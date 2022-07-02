package org.lushen.mrh.cloud.gateway.supports;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import reactor.core.publisher.Mono;

/**
 * 网关登录令牌存储接口
 * 
 * @author hlm
 */
public interface GatewayTokenRepository {

	/**
	 * 更新登录令牌
	 * 
	 * @param token
	 * @param object
	 * @return
	 */
	public Mono<Boolean> update(String token, GatewayTokenObject object);

	/**
	 * 校验登录令牌
	 * 
	 * @param token
	 * @param object
	 * @return
	 */
	public Mono<Boolean> validate(String token, GatewayTokenObject object);

	/**
	 * 无实现 登录令牌存储
	 * 
	 * @author hlm
	 */
	public static class UnrealizedTokenRepository implements GatewayTokenRepository {

		@Override
		public Mono<Boolean> update(String token, GatewayTokenObject context) {
			return Mono.just(Boolean.TRUE);
		}

		@Override
		public Mono<Boolean> validate(String token, GatewayTokenObject context) {
			return Mono.just(Boolean.TRUE);
		}

	}

	/**
	 * 登录令牌 reactive redis 存储实现
	 * 
	 * @author hlm
	 */
	public static class RedisTokenRepository implements GatewayTokenRepository {

		private String keyPrefix = RedisTokenRepository.class.getSimpleName();

		private ReactiveStringRedisTemplate redisTemplate;

		public RedisTokenRepository(ReactiveStringRedisTemplate redisTemplate) {
			super();
			this.redisTemplate = redisTemplate;
		}

		@Override
		public Mono<Boolean> update(String token, GatewayTokenObject object) {
			return redisTemplate.opsForValue().set(key(object), token, object.getExpired());
		}

		@Override
		public Mono<Boolean> validate(String token, GatewayTokenObject object) {
			return redisTemplate.opsForValue().get(key(object))
					.flatMap(value -> Mono.just(StringUtils.equals(token, value)))
					.switchIfEmpty(Mono.just(Boolean.FALSE));
		}

		private String key(GatewayTokenObject object) {
			return new StringBuilder(this.keyPrefix).append(".").append(object.getId()).append(".").append(object.getSource()).toString();
		}

		public String getKeyPrefix() {
			return keyPrefix;
		}

		public void setKeyPrefix(String keyPrefix) {
			this.keyPrefix = keyPrefix;
		}

	}

}
