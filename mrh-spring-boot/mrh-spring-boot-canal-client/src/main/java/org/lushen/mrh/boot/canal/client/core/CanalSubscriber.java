package org.lushen.mrh.boot.canal.client.core;

import com.alibaba.otter.canal.protocol.Message;

/**
 * canal 消息订阅接口
 *
 * @author hlm
 */
public interface CanalSubscriber {

	/**
	 * canal 消息处理
	 *
	 * @param message
	 * @throws Exception
	 */
	public void subscribe(Message message) throws Exception;

}