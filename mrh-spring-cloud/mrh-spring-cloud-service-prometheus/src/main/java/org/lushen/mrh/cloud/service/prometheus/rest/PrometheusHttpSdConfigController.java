package org.lushen.mrh.cloud.service.prometheus.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * prometheus http_sd_configs 抓取接口
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="")
public class PrometheusHttpSdConfigController {

	@Autowired
	private DiscoveryClient client;

	@GetMapping(path="prometheus/config/sd")
	public List<HttpSdConfig> prometheus() {

		// 获取各个服务及其集群信息
		List<String> serviceIds = client.getServices();
		List<List<ServiceInstance>> instancesGroup = serviceIds.stream().map(serviceId -> client.getInstances(serviceId)).collect(Collectors.toList());

		// 转换为 HttpSdConfig 列表
		List<HttpSdConfig> sdConfigs = instancesGroup.stream().map(instances -> {
			List<String> targets = new ArrayList<String>();
			Map<String, String> labels = new HashMap<String, String>();
			instances.forEach(instance -> {
				targets.add(instance.getHost()+":"+instance.getPort());
				labels.putAll(instance.getMetadata());
			});
			return new HttpSdConfig(targets, labels);
		}).collect(Collectors.toList());

		// 返回 prometheus 需要的 JSON
		return sdConfigs;
	}

	public static class HttpSdConfig {

		private List<String> targets;

		private Map<String, String> labels;

		public HttpSdConfig(List<String> targets, Map<String, String> labels) {
			super();
			this.targets = targets;
			this.labels = labels;
		}

		public List<String> getTargets() {
			return targets;
		}

		public Map<String, String> getLabels() {
			return labels;
		}

	}

}
