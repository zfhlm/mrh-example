package org.lushen.mrh.example.cache.redis.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存强制 miss 注解
 * 
 * @author hlm
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CacheEnforceMiss {

	/**
	 * 是否强制 miss 缓存
	 * 
	 * @return
	 */
	boolean value() default true;

	/**
	 * 是否传递强制 miss 缓存状态，需要额外扩展处理，例如基于 dubbo filter context、spring cloud feign interceptor header 进行传递
	 * 
	 * @return
	 */
	boolean transfer() default true;

}
