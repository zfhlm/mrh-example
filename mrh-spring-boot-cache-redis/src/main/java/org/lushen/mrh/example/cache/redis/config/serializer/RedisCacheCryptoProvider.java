package org.lushen.mrh.example.cache.redis.config.serializer;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/**
 * 加解密接口，注意别使用 base64 增加长度
 * 
 * @author helm
 */
public interface RedisCacheCryptoProvider {

	/**
	 * 加密
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] bytes) throws Exception;

	/**
	 * 解密
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] bytes) throws Exception;

	/**
	 * 3DES 双向加解密工具
	 * 
	 * @author helm
	 */
	public static class RedisCacheDes3CryptoProvider implements RedisCacheCryptoProvider {

		private static final String KEY_ALGORITHM = "desede";

		private static final String DEFAULT_CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";

		private byte[] key;

		private byte[] iv;

		public RedisCacheDes3CryptoProvider(String key, String iv) {
			super();
			this.key = StringUtils.rightPad(key, 24, '0').getBytes();
			this.iv = StringUtils.rightPad(iv, 8, '0').getBytes();
		}

		@Override
		public byte[] encrypt(byte[] bytes) throws Exception {

			if(bytes == null) {
				return null;
			}

			DESedeKeySpec spec = new DESedeKeySpec(this.key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			Key deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			IvParameterSpec ips = new IvParameterSpec(this.iv);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);

			return cipher.doFinal(bytes);
		}

		@Override
		public byte[] decrypt(byte[] bytes) throws Exception {

			if(bytes == null) {
				return null;
			}

			DESedeKeySpec spec = new DESedeKeySpec(this.key);
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			Key deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			IvParameterSpec ips = new IvParameterSpec(this.iv);
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			return cipher.doFinal(bytes);
		}

	}

	/**
	 * des 加解密工具
	 * 
	 * @author helm
	 */
	public static class RedisCacheDesCryptoProvider implements RedisCacheCryptoProvider {

		private final static String DES = "DES";

		private byte[] key;

		public RedisCacheDesCryptoProvider(String key) {
			super();
			this.key = StringUtils.rightPad(key, 24, '0').getBytes();
		}

		@Override
		public byte[] encrypt(byte[] bytes) throws Exception {

			if(bytes == null) {
				return null;
			}

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(new DESKeySpec(this.key));

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());

			return cipher.doFinal(bytes);
		}

		@Override
		public byte[] decrypt(byte[] bytes) throws Exception {

			if(bytes == null) {
				return null;
			}

			DESKeySpec dks = new DESKeySpec(this.key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());

			return cipher.doFinal(bytes);
		}

	}

	/**
	 * AES双向加密工具类
	 * 
	 * @author helm
	 */
	public static class RedisCacheAesCryptoProvider implements RedisCacheCryptoProvider {

		private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

		private static final String SECURE_RANDOM = "SHA1PRNG";

		private static final String KEY_ALGORITHM = "AES";

		private byte[] key;

		public RedisCacheAesCryptoProvider(String key) {
			super();
			this.key = key.getBytes();
		}

		@Override
		public byte[] encrypt(byte[] bytes) throws Exception {

			if(bytes == null) {
				return null;
			}

			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
			secureRandom.setSeed(this.key);
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			return cipher.doFinal(bytes);
		}

		@Override
		public byte[] decrypt(byte[] bytes) throws Exception {

			if(bytes == null) {
				return null;
			}

			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
			secureRandom.setSeed(this.key);
			keyGenerator.init(128, secureRandom);
			SecretKey secretKey = keyGenerator.generateKey();
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);

			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			return cipher.doFinal(bytes);
		}

	}

}
