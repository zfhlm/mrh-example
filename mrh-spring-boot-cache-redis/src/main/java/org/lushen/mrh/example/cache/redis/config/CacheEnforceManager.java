package org.lushen.mrh.example.cache.redis.config;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 缓存强制 miss 上下文管理器
 * 
 * @author hlm
 */
public final class CacheEnforceManager {

	private static final CacheEnforceManager INSTANCE = new CacheEnforceManager();

	private static final ThreadLocal<Queue<Context>> CONTEXTS = new ThreadLocal<Queue<Context>>() {
		@Override
		protected Queue<Context> initialValue() {
			return new LinkedList<Context>();
		}
	};

	/**
	 * 获取 manager 实例
	 * 
	 * @return
	 */
	public static final CacheEnforceManager getInstance() {
		return INSTANCE;
	}

	private CacheEnforceManager() {}

	/**
	 * 是否强制 miss 缓存
	 * 
	 * @return
	 */
	public boolean isMiss() {
		Context context = CONTEXTS.get().peek();
		return context != null && context.isMiss();
	}

	/**
	 * 是否传递 miss 缓存
	 * 
	 * @return
	 */
	public boolean isTransfer() {
		Context context = CONTEXTS.get().peek();
		return context != null && context.isTransfer();
	}

	/**
	 * 配置当前上下文
	 * 
	 * @param miss
	 * @param transfer
	 * @return
	 */
	public String configure(boolean miss, boolean transfer) {
		String id = UUID.randomUUID().toString();
		CONTEXTS.get().offer(new Context(id, miss, transfer));
		return id;
	}

	/**
	 * 释放当前上下文
	 * 
	 * @param id
	 */
	public void release(String id) {
		Context context = CONTEXTS.get().peek();
		if(context == null || ! StringUtils.equals(id, context.getId())) {
			throw new IllegalArgumentException("Wrong context id :: " + id);
		}
		CONTEXTS.get().poll();
	}

	/**
	 * 上下文对象
	 * 
	 * @author hlm
	 */
	private class Context {

		private final String id;

		private final boolean miss;

		private final boolean transfer;

		private Context(String id, boolean miss, boolean transfer) {
			super();
			this.id = id;
			this.miss = miss;
			this.transfer = transfer;
		}

		public String getId() {
			return id;
		}

		public boolean isMiss() {
			return miss;
		}

		public boolean isTransfer() {
			return transfer;
		}

	}

}
