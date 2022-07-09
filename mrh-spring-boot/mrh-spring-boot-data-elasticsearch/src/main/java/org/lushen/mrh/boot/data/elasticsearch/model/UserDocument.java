package org.lushen.mrh.boot.data.elasticsearch.model;

import static org.lushen.mrh.boot.data.elasticsearch.rest.ElasticIndexAliasController.ALIAS_NAME;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

/**
 * 文档映射
 * 
 * @author hlm
 */
// 指定 index 名称，不自动创建 index，不写入当前类型到 elasticsearch
@Document(indexName=ALIAS_NAME, createIndex=false, writeTypeHint=WriteTypeHint.FALSE)
public class UserDocument {

	// 如果主键不保存到 _source 必须加 @ReadOnlyProperty 注解
	@Id
	@ReadOnlyProperty
	private Long id;

	@Field(name="name", type=FieldType.Keyword)
	private String name;

	@Field(name="address", type=FieldType.Text)
	private String address;

	@Field(name="@timestamp", type=FieldType.Date, format=DateFormat.year_month_day)
	private LocalDate timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append("]");
		return builder.toString();
	}

}
