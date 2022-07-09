package org.lushen.mrh.boot.data.elasticsearch.rest;

import static org.lushen.mrh.boot.data.elasticsearch.rest.ElasticIndexAliasController.ALIAS_NAME;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文档操作
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/document")
public class ElasticDocumentController {

	@Autowired
	private RestHighLevelClient highLevelClient;

	/**
	 * 添加文档
	 */
	@GetMapping(path="/save")
	public String save() throws IOException {

		Map<String, Object> source = new HashMap<String, Object>();
		source.put("@timestamp", "2021-01-30");
		source.put("name", "zhangsan");
		source.put("address", "广州");

		IndexRequest request = new IndexRequest(ALIAS_NAME);
		request.id("1");
		request.source(source);
		IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);

		return String.valueOf(response.getVersion());
	}

	/**
	 * 查询文档
	 */
	@GetMapping(path="/query")
	public String query() throws IOException {

		GetRequest request = new GetRequest(ALIAS_NAME);
		request.id("1");
		GetResponse response = highLevelClient.get(request, RequestOptions.DEFAULT);

		return String.valueOf(response.getSource());
	}

	/**
	 * 查询文档属性字段
	 */
	@GetMapping(path="/source/query")
	public String querySource() throws IOException {

		GetSourceRequest request = new GetSourceRequest(ALIAS_NAME, "1");
		GetSourceResponse response = highLevelClient.getSource(request, RequestOptions.DEFAULT);

		return String.valueOf(response.getSource());
	}

	/**
	 * 查询文档是否存在
	 */
	@GetMapping(path="/exist")
	public String exist() throws IOException {

		GetSourceRequest request = new GetSourceRequest(ALIAS_NAME, "1");
		boolean response = highLevelClient.existsSource(request, RequestOptions.DEFAULT);

		return String.valueOf(response);
	}

	/**
	 * 删除文档
	 */
	@GetMapping(path="/delete")
	public String delete() throws IOException {

		DeleteRequest request = new DeleteRequest(ALIAS_NAME, "1");
		DeleteResponse response = highLevelClient.delete(request, RequestOptions.DEFAULT);

		return String.valueOf(response.getResult() == Result.DELETED);
	}

	/**
	 * 其他动态查询，不再演示
	 */
	// ......

}
