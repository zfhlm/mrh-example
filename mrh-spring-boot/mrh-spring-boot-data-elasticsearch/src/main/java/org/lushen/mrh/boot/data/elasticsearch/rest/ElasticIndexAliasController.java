package org.lushen.mrh.boot.data.elasticsearch.rest;

import static org.lushen.mrh.boot.data.elasticsearch.rest.ElasticIndexController.INDEX_NAME;

import java.io.IOException;
import java.util.function.Supplier;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 索引别名，可以对单个或多个索引设置同一别名，通常用于索引重建时无缝切换：<br>
 * 
 * <li>第一步，配置新索引</li>
 * <li>第二步，选择时间段数据进行rebuild</li>
 * <li>第三步，校验数据是否准确</li>
 * <li>第四步，切换索引别名到新索引</li>
 * <li>第五步，补全时间段进行rebuild</li>
 * <li>第六步，校验数据</li>
 * <li>第五步，删除旧索引</li>
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/index/alias")
public class ElasticIndexAliasController {

	public static final String ALIAS_NAME = "my-sample-user-index-alias";

	@Autowired
	private RestHighLevelClient highLevelClient;

	/**
	 * 添加索引别名
	 */
	@GetMapping(path="/add")
	public String add() throws IOException {
		IndicesAliasesRequest request = new IndicesAliasesRequest();
		request.addAliasAction(((Supplier<AliasActions>)() -> {
			AliasActions aliasAction = new AliasActions(AliasActions.Type.ADD);
			aliasAction.index(INDEX_NAME);
			aliasAction.alias(ALIAS_NAME);
			return aliasAction;
		}).get());
		AcknowledgedResponse response = highLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 移动索引别名
	 */
	@GetMapping(path="/move")
	public String move() throws IOException {
		IndicesAliasesRequest request = new IndicesAliasesRequest();
		request.addAliasAction(((Supplier<AliasActions>)() -> {
			AliasActions aliasAction = new AliasActions(AliasActions.Type.REMOVE);
			aliasAction.index(INDEX_NAME);
			aliasAction.alias(ALIAS_NAME);
			return aliasAction;
		}).get());
		request.addAliasAction(((Supplier<AliasActions>)() -> {
			AliasActions aliasAction = new AliasActions(AliasActions.Type.ADD);
			aliasAction.index(INDEX_NAME);
			aliasAction.alias(ALIAS_NAME);
			return aliasAction;
		}).get());
		AcknowledgedResponse response = highLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

	/**
	 * 删除索引别名
	 */
	@GetMapping(path="/delete")
	public String delete() throws IOException {
		IndicesAliasesRequest request = new IndicesAliasesRequest();
		request.addAliasAction(((Supplier<AliasActions>)() -> {
			AliasActions aliasAction = new AliasActions(AliasActions.Type.REMOVE);
			aliasAction.index(INDEX_NAME);
			aliasAction.alias(ALIAS_NAME);
			return aliasAction;
		}).get());
		AcknowledgedResponse response = highLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
		return String.valueOf(response.isAcknowledged());
	}

}
