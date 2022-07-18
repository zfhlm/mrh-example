package org.lushen.mrh.example.springdoc;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class TestReq {
	
	@NotNull
	@Schema(name="主键ID")
	private Integer id;

	@NotBlank
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
