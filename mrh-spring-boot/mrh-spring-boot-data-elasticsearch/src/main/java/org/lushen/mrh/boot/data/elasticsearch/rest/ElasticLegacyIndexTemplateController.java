package org.lushen.mrh.boot.data.elasticsearch.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexTemplatesRequest;
import org.elasticsearch.client.indices.GetIndexTemplatesResponse;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 索引模板，已经不建议使用，官方推荐组件索引模板
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/template/legacy")
@Deprecated
public class ElasticLegacyIndexTemplateController {

	static final String INDEX_TEMPLATE_NAME = "my-sample-index-template";

	@Autowired
	private RestHighLevelClient highLevelClient;

	/**
	 * 创建索引模板
	 */
	@GetMapping(path="/create")
	@Deprecated
	public String create() throws IOException {

		// 读取配置
		Resource resource = new DefaultResourceLoader().getResource("classpath:es/"+INDEX_TEMPLATE_NAME+".json");
		InputStream stream = resource.getInputStream();
		String json = IOUtils.toString(stream, Charset.defaultCharset());
		stream.close();
		System.out.println(json);

		// 创建索引模板
		PutIndexTemplateRequest request = new PutIndexTemplateRequest(INDEX_TEMPLATE_NAME);
		request.source(json, XContentType.JSON);
		AcknowledgedResponse response = highLevelClient.indices().putTemplate(request, RequestOptions.DEFAULT);

		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 查询索引模板
	 */
	@GetMapping(path="/query")
	@Deprecated
	public String query() throws IOException {

		// 查询索引模板
		GetIndexTemplatesRequest request = new GetIndexTemplatesRequest(INDEX_TEMPLATE_NAME);
		GetIndexTemplatesResponse response = highLevelClient.indices().getIndexTemplate(request, RequestOptions.DEFAULT);

		return String.valueOf(response.getIndexTemplates().size());
	}

	/**
	 * 删除索引模板
	 */
	@GetMapping(path="/delete")
	@Deprecated
	public String delete() throws Exception {

		// 删除索引模板
		DeleteIndexTemplateRequest request = new DeleteIndexTemplateRequest(INDEX_TEMPLATE_NAME);
		AcknowledgedResponse response = highLevelClient.indices().deleteTemplate(request, RequestOptions.DEFAULT);

		return String.valueOf(response.isAcknowledged());
	}

}
