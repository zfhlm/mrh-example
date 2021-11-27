package org.lushen.mrh.example.elasticsearch.rest.client;

import java.io.IOException;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.DeleteComponentTemplateRequest;
import org.elasticsearch.client.indices.GetComponentTemplatesRequest;
import org.elasticsearch.client.indices.GetComponentTemplatesResponse;
import org.elasticsearch.client.indices.PutComponentTemplateRequest;
import org.elasticsearch.cluster.metadata.ComponentTemplate;
import org.elasticsearch.cluster.metadata.Template;
import org.elasticsearch.common.compress.CompressedXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组件模板
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/template/component")
public class ElasticComponentTemplateController {

	static final String COMPONENT_TEMPLATE_NAME = "my-sample-component-template";

	@Autowired
	private RestHighLevelClient highLevelClient;

	/**
	 * 创建组件模板
	 */
	@GetMapping(path="/create")
	public String create() throws IOException {

		String mappingJson = ""
				+ "{"
				+ "  \"properties\": {"
				+ "    \"message\": {"
				+ "      \"type\": \"text\""
				+ "    }"
				+ "  }"
				+ "}";
		Template template = new Template(null, new CompressedXContent(mappingJson), null); 

		// 发起请求
		PutComponentTemplateRequest request = new PutComponentTemplateRequest().name(COMPONENT_TEMPLATE_NAME);
		request.componentTemplate(new ComponentTemplate(template, null, null));
		AcknowledgedResponse response = highLevelClient.cluster().putComponentTemplate(request, RequestOptions.DEFAULT);

		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 查询组件模板
	 */
	@GetMapping(path="/query")
	public String query() throws IOException {
		GetComponentTemplatesRequest request = new GetComponentTemplatesRequest(COMPONENT_TEMPLATE_NAME);
		GetComponentTemplatesResponse response = highLevelClient.cluster().getComponentTemplate(request, RequestOptions.DEFAULT);
		return String.valueOf(response.getComponentTemplates().keySet());
	}

	/**
	 * 删除组件模板
	 */
	@GetMapping(path="/delete")
	public String delete() throws IOException {
		DeleteComponentTemplateRequest request = new DeleteComponentTemplateRequest(COMPONENT_TEMPLATE_NAME);
		AcknowledgedResponse response = highLevelClient.cluster().deleteComponentTemplate(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

}
