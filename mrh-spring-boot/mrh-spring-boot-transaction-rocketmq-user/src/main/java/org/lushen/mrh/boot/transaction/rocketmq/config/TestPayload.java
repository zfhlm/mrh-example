package org.lushen.mrh.boot.transaction.rocketmq.config;

import java.io.Serializable;

/**
 * 事务消息实体
 * 
 * @author hlm
 */
public class TestPayload implements Serializable {

	private static final long serialVersionUID = -7688963733232183244L;

	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestParameter [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
