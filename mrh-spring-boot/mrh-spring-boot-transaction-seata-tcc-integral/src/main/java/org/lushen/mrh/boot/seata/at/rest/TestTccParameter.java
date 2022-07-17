package org.lushen.mrh.boot.seata.at.rest;

import java.util.concurrent.ThreadLocalRandom;

public class TestTccParameter {

	private int id = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestTccParameter [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
