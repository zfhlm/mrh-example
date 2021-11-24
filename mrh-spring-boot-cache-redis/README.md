
# mrh-spring-boot-cache-redis

    配置默认缓存组件：

        org.springframework.cache.annotation.CachingConfigurer#keyGenerator()

        org.springframework.cache.annotation.CachingConfigurer#errorHandler()

        org.springframework.cache.annotation.CachingConfigurer#cacheResolver()

        org.springframework.cache.annotation.CachingConfigurer#cacheManager()

    缓存击穿问题处理：

        @Cachable(sync=true) 开启 sync 只允许一个请求执行逻辑，底层使用 synchronized 实现，默认锁整个 RedisCache 实例

        如果需要进行扩展，例如分布式环境、以及更优更细粒度的锁 CountDownLatch 等，重写两处：

            org.springframework.data.redis.cache.RedisCache#get(Object, Callable<T>)

            org.springframework.data.redis.cache.RedisCacheManager.createRedisCache(String, RedisCacheConfiguration)

    缓存批量清除问题：

        创建 org.springframework.data.redis.cache.RedisCacheWriter 同时定义 org.springframework.data.redis.cache.BatchStrategy

        集群环境不支持 keys 或 scan 命令，可以选择禁用 @Cachable(allEntries=true)，或者重写 org.springframework.data.redis.cache.BatchStrategy

    缓存数据安全问题：

        // 示例代码：

        RedisCacheCryptoProvider provider = new RedisCacheCryptoProvider.RedisCacheDes3CryptoProvider("test", "123456");

        RedisCacheCryptoSerializer<Object> serializer = new RedisCacheCryptoSerializer<>(new GenericJackson2JsonRedisSerializer(), provider);

        RedisCacheConfiguration configuration = RedisCacheConfiguration.serializeValuesWith(SerializationPair.fromSerializer(serializer));
