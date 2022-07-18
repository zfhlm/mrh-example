package org.lushen.mrh.boot.transaction.rabbitmq.config;

import java.io.Serializable;

/**
 * 消息参数
 * 
 * @author hlm
 */
public class AmqpParameter implements Serializable {

	private static final long serialVersionUID = -5737962105389578180L;

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
		builder.append("AmqpParameter [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
