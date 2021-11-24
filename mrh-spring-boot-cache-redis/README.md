
# mrh-spring-boot-cache-redis

    缓存击穿问题：

        使用 @Cachable(sync=true) 防止缓存击穿

        更细锁粒度缓存管理器 org.lushen.mrh.example.cache.redis.config.RedisCacheManager

    缓存批量清除问题：

        使用 @Cachable(allEntries=true) 底层实现使用到 keys 或 scan 命令，可能遇到集群不支持的情况，或者支持但会导致redis阻塞

        需要重写 org.springframework.data.redis.cache.RedisCacheWriter 中的 org.springframework.data.redis.cache.BatchStrategy

        建议禁用 @Cachable(allEntries=true)

    缓存序列化问题：

        // 自定义 org.springframework.data.redis.serializer.RedisSerializer 可以扩展例如 protobuf、加解密、压缩等

        // 加解密示例代码：

        RedisCacheCryptoProvider provider = new RedisCacheCryptoProvider.RedisCacheDes3CryptoProvider("test", "123456");

        RedisCacheCryptoSerializer<Object> serializer = new RedisCacheCryptoSerializer<Object>(new GenericJackson2JsonRedisSerializer(), provider);

        RedisCacheConfiguration configuration = RedisCacheConfiguration.serializeValuesWith(SerializationPair.fromSerializer(serializer));

    缓存强制 miss 问题：

        使用注解 @CacheEnforceMiss 强制 miss 缓存

        微服务可以通过上下文进行传递，例如 dubbo filter context，feign http header，然后根据上下文配置 CacheEnforceMissManager
