package org.lushen.mrh.example.cache.redis.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * 加解密配置
 * 
 * @author hlm
 */
public class RedisCacheCryptoProperties {

	@NotNull
	private CryptoType type;

	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]+")
	@Length(max=24)
	private String key;

	@Length(max=6)
	@Pattern(regexp="^[a-zA-Z0-9]+")
	private String iv;

	public CryptoType getType() {
		return type;
	}

	public void setType(CryptoType type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	/**
	 * 加密方式
	 * 
	 * @author hlm
	 */
	public static enum CryptoType {

		AES, DES, DES3;

	}

}
