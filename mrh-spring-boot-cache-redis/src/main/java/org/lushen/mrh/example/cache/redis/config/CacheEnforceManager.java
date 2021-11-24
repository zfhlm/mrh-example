package org.lushen.mrh.example.cache.redis.config;

import java.util.UUID;
import java.util.function.Supplier;

import org.apache.commons.collections4.map.LinkedMap;

/**
 * 缓存强制 miss 上下文
 * 
 * @author hlm
 */
public final class CacheEnforceManager {

	private static final ThreadLocal<LinkedMap<String, Boolean>> CONTEXTS = new ThreadLocal<LinkedMap<String, Boolean>>() {
		@Override
		protected LinkedMap<String, Boolean> initialValue() {
			return new LinkedMap<String, Boolean>(2);
		}
	};

	/**
	 * 是否强制 miss 缓存
	 * 
	 * @return
	 */
	public static final boolean isEnforceMiss() {
		LinkedMap<String, Boolean> context = CONTEXTS.get();
		if(context == null || context.isEmpty()) {
			return false;
		} else {
			Boolean value = context.getValue(context.size()-1);
			return value != null && value.booleanValue();
		}
	}

	/**
	 * 指定强制 miss 缓存标识
	 * 
	 * @param miss 是否强制miss
	 * @return
	 */
	public static final String enforce(boolean miss) {
		String id = UUID.randomUUID().toString();
		CONTEXTS.get().put(id, miss);
		return id;
	}

	/**
	 * 释放强制 miss 缓存标识
	 * 
	 * @param id
	 */
	public static final void release(String id) {
		CONTEXTS.get().remove(id);
	}

	/**
	 * 指定强制 miss 缓存标识，执行 supplier
	 * 
	 * @param miss
	 * @param supplier
	 * @return
	 */
	public static final <T> T enforce(boolean miss, Supplier<T> supplier) {
		String id = enforce(miss);
		try {
			return supplier.get();
		} finally {
			release(id);
		}
	}

}
