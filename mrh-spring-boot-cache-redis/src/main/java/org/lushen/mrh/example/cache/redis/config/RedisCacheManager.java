package org.lushen.mrh.example.cache.redis.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * 缓存管理器，重写缓存创建方法，开启@Cacheable(sync=true)时使用更细粒度锁<br><br>
 * 
 * 可接受情况下不引入非分布式锁，应用更加健壮，n 次 缓存击穿 db 请求，降低到最多应用实例个数 db 请求
 * 
 * @author hlm
 */
public class RedisCacheManager extends org.springframework.data.redis.cache.RedisCacheManager {

	private final RedisCacheWriter cacheWriter;

	private final RedisCacheConfiguration defaultCacheConfig;

	public RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
		this(cacheWriter, defaultCacheConfiguration, Collections.emptyMap());
	}

	public RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
		super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
		this.cacheWriter = cacheWriter;
		this.defaultCacheConfig = defaultCacheConfiguration;
	}

	@Override
	protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
		return new RedisCountDownLatchCache(name, this.cacheWriter, cacheConfig != null ? cacheConfig : this.defaultCacheConfig);
	}

	private class RedisCountDownLatchCache extends RedisCache {

		private final ConcurrentHashMap<Object, CountDownLatch> countDownLatchs = new ConcurrentHashMap<Object, CountDownLatch>();

		private RedisCountDownLatchCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
			super(name, cacheWriter, cacheConfig);
		}

		@SuppressWarnings("unchecked")
		public <T> T get(Object key, Callable<T> valueLoader) {

			ValueWrapper result = get(key);

			if (result != null) {
				return (T) result.get();
			}

			// 创建引用对象
			AtomicReference<Consumer<CountDownLatch>> reference = new AtomicReference<>(latch -> {
				try {
					latch.await();
				} catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			});
			AtomicBoolean isFirst = new AtomicBoolean(false);

			// 第一个key线程不阻塞，其他key线程阻塞等待
			CountDownLatch latch = this.countDownLatchs.computeIfAbsent(key, s -> {
				reference.set(l -> {});
				isFirst.set(true);
				return new CountDownLatch(1);
			});
			reference.get().accept(latch);

			try {

				// 其他key线程，先查询缓存
				if( ! isFirst.get() && (result = get(key)) != null) {
					return (T) result.get();
				}

				// 无缓存数据，执行回调，并刷新缓存
				// 不包装运行时异常，防止识别不到业务异常
				T value;
				try {
					value = valueLoader.call();
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new ValueRetrievalException(key, valueLoader, e);
				}
				put(key, value);

				return value;

			} finally {

				// 第一个key线程执行完毕，允许其他key线程并发执行
				if(isFirst.get()) {
					latch.countDown();
					this.countDownLatchs.remove(key);
				}

			}

		}

	}

}
