package org.lushen.mrh.boot.data.elasticsearch.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.DeleteComposableIndexTemplateRequest;
import org.elasticsearch.client.indices.GetComposableIndexTemplateRequest;
import org.elasticsearch.client.indices.GetComposableIndexTemplatesResponse;
import org.elasticsearch.client.indices.PutComposableIndexTemplateRequest;
import org.elasticsearch.cluster.metadata.ComposableIndexTemplate;
import org.elasticsearch.cluster.metadata.Template;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组件索引模板
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/template/component/index")
public class ElasticComponentIndexTemplateController {

	static final String COMPONENT_INDEX_TEMPLATE_NAME = "my-sample-component-index-template";

	@Autowired
	private RestHighLevelClient highLevelClient;

	/**
	 * 创建模板
	 */
	@GetMapping(path="/create")
	public String create() throws IOException {

		List<String> indexPatterns = Arrays.asList("my-sample-component-*");
		Settings settings = Settings.builder() 
				.put("index.number_of_shards", 1)
				.put("index.number_of_replicas", 0)
				.build();
		String mappingJson = "" 
				+ "{"
				+ "	\"_source\": {"
				+ "		\"enabled\": true"
				+ "	},"
				+ "	\"properties\": {"
				+ "		\"host_name\": {"
				+ "			\"type\": \"keyword\""
				+ "		},"
				+ "		\"created_at\": {"
				+ "			\"type\": \"date\","
				+ "			\"format\": \"EEE MMM dd HH:mm:ss Z yyyy\""
				+ "		}"
				+ "   }"
				+ "}";
		Template template = new Template(settings, new CompressedXContent(mappingJson), null);

		PutComposableIndexTemplateRequest request = new PutComposableIndexTemplateRequest().name(COMPONENT_INDEX_TEMPLATE_NAME);
		request.indexTemplate(new ComposableIndexTemplate(indexPatterns, template, null, 100L, 1L, null));
		AcknowledgedResponse response = highLevelClient.indices().putIndexTemplate(request, RequestOptions.DEFAULT);

		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 查询模板
	 */
	@GetMapping(path="/query")
	public String query() throws IOException {
		GetComposableIndexTemplateRequest request = new GetComposableIndexTemplateRequest(COMPONENT_INDEX_TEMPLATE_NAME); 
		GetComposableIndexTemplatesResponse response = highLevelClient.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
		return String.valueOf(response.getIndexTemplates().keySet());
	}

	/**
	 * 删除模板
	 */
	@GetMapping(path="/delete")
	public String delete() throws IOException {
		DeleteComposableIndexTemplateRequest request = new DeleteComposableIndexTemplateRequest(COMPONENT_INDEX_TEMPLATE_NAME);
		AcknowledgedResponse response = highLevelClient.indices().deleteIndexTemplate(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

}
