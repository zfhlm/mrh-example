package org.lushen.mrh.example.cache.redis.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 序列化内容加解密
 * 
 * @author hlm
 * @param <T>
 */
public class RedisCacheCryptoSerializer<T> implements RedisSerializer<T> {

	private RedisCacheCryptoProvider cryptoProvider;

	private RedisSerializer<T> serializer;

	public RedisCacheCryptoSerializer(RedisSerializer<T> serializer, RedisCacheCryptoProvider cryptoProvider) {
		super();
		this.cryptoProvider = cryptoProvider;
		this.serializer = serializer;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		try {
			return this.cryptoProvider.encrypt(this.serializer.serialize(t));
		} catch (SerializationException e) {
			throw e;
		} catch (Exception e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		try {
			return this.serializer.deserialize(this.cryptoProvider.decrypt(bytes));
		} catch (SerializationException e) {
			throw e;
		} catch (Exception e) {
			throw new SerializationException(e.getMessage(), e);
		}
	}

}
