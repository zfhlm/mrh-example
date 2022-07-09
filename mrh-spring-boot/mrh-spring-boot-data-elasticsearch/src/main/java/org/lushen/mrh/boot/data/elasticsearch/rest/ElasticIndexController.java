package org.lushen.mrh.boot.data.elasticsearch.rest;

import java.io.IOException;
import java.util.Arrays;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.CloseIndexResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 索引
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/index")
public class ElasticIndexController {

	static final String INDEX_NAME = "my-sample-user-index-v1";

	@Autowired
	private RestHighLevelClient highLevelClient;

	/**
	 * 创建索引
	 */
	@GetMapping(path="/create")
	public String create() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
		CreateIndexResponse response = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
		return response.index();
	}

	/**
	 * 查询索引
	 */
	@GetMapping(path="/query")
	public String query() throws IOException {
		GetIndexRequest request = new GetIndexRequest(INDEX_NAME);
		GetIndexResponse response = highLevelClient.indices().get(request, RequestOptions.DEFAULT);
		return Arrays.toString(response.getIndices());
	}

	/**
	 * 开启索引
	 */
	@GetMapping(path="/open")
	public String open() throws IOException {
		OpenIndexRequest request = new OpenIndexRequest(INDEX_NAME);
		OpenIndexResponse response = highLevelClient.indices().open(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 关闭索引
	 */
	@GetMapping(path="/close")
	public String close() throws IOException {
		CloseIndexRequest request = new CloseIndexRequest(INDEX_NAME);
		CloseIndexResponse response = highLevelClient.indices().close(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 删除索引
	 */
	@GetMapping(path="/delete")
	public String delete() throws IOException {
		DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
		AcknowledgedResponse response = highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

}
