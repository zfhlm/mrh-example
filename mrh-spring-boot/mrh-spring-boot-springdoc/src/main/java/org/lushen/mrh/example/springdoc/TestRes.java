package org.lushen.mrh.example.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;

public class TestRes {

	@Schema(name="主键ID")
	private Integer id;

	@Schema(name="姓名")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
