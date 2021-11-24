package org.lushen.mrh.example.cache.redis.config;

import java.time.Duration;

import org.lushen.mrh.example.cache.redis.config.CacheErrorHandler.NotSupportCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.BatchStrategy;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * cache 扩展自动配置
 * 
 * @author helm
 */
@Configuration
public class CacheAutoConfiguration {

	/**
	 * 注册默认缓存key生成器、缓存错误处理器
	 */
	@Bean
	public CachingConfigurer cachingConfigurer() {
		return new CachingConfigurer() {
			@Override
			public KeyGenerator keyGenerator() {
				return new org.lushen.mrh.example.cache.redis.config.CacheKeyGenerator();
			}
			@Override
			public CacheErrorHandler errorHandler() {
				return new org.lushen.mrh.example.cache.redis.config.CacheErrorHandler();
			}
			@Override
			public CacheResolver cacheResolver() {
				return null;
			}
			@Override
			public CacheManager cacheManager() {
				return null;
			}
		};
	}

	/**
	 * 注册缓存管理器
	 */
	@Bean
	public RedisCacheManager redisCacheManager(@Autowired RedisConnectionFactory connectionFactory) {
		RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory, new BatchStrategy() {
			@Override
			public long cleanCache(RedisConnection connection, String name, byte[] pattern) {
				throw new NotSupportCommandException(String.format("Not support redis command :: keys %s", new String(pattern)));
			}
		});
		RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration
				.defaultCacheConfig()
				.computePrefixWith(CacheKeyPrefix.simple())
				.entryTtl(Duration.ofSeconds(300L))
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		return new org.lushen.mrh.example.cache.redis.config.RedisCacheManager(cacheWriter, defaultCacheConfiguration);
	}

}
