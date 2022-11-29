package org.lushen.mrh.cloud.service.prometheus.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Prometheus http_sd_configs 接口，动态获取 metrics 端点
 * 
 * @author hlm
 */
@RestController
public class PrometheusController {

    @Autowired
    private DiscoveryClient client;

    /**
     * 通过 /metrics 暴露指标数据的服务
     */
    @GetMapping(path="/prometheus/metrics/configs")
    public List<PrometheusConfig> metrics() {

        List<PrometheusConfig> configs = new ArrayList<PrometheusConfig>();

        // Node
        {   
            List<String> targets = Arrays.asList("192.168.119.144:9100", "192.168.119.145:9100");
            Map<String, String> labels = new HashMap<String, String>();
            labels.put("cluster", "mooin-cluster");
            labels.put("application", "centos7");
            configs.add(new PrometheusConfig(targets, labels));
        }

        // Nginx
        {
            List<String> targets = Arrays.asList("192.168.119.145:9913");
            Map<String, String> labels = new HashMap<String, String>();
            labels.put("cluster", "mooin-cluster");
            labels.put("application", "nginx");
            configs.add(new PrometheusConfig(targets, labels));
        }

        // MySQL
        {
            List<String> targets = Arrays.asList("192.168.119.145:9104");
            Map<String, String> labels = new HashMap<String, String>();
            labels.put("cluster", "mooin-cluster");
            labels.put("application", "nginx");
            configs.add(new PrometheusConfig(targets, labels));
        }

        // Redis
        {
            List<String> targets = Arrays.asList("192.168.119.145:9121");
            Map<String, String> labels = new HashMap<String, String>();
            labels.put("cluster", "mooin-cluster");
            labels.put("application", "redis");
            configs.add(new PrometheusConfig(targets, labels));
        }

        return configs;
    }

    /**
     * 通过 /actuator/prometheus 暴露指标数据的服务
     */
    @GetMapping(path="/prometheus/boot/configs")
    public List<PrometheusConfig> boot() {

        List<PrometheusConfig> configs = new ArrayList<PrometheusConfig>();

        // SpringBoot
        {
            List<String> targets = Arrays.asList("192.168.119.145:8888", "192.168.119.145:8889");
            Map<String, String> labels = new HashMap<String, String>();
            labels.put("cluster", "mooin-cluster");
            labels.put("application", "java");
            labels.put("service", "mrh-spring-boot");
            configs.add(new PrometheusConfig(targets, labels));
        }

        return configs;
    }

    /**
     * 通过 /actuator/prometheus 暴露指标数据的服务
     */
    @GetMapping(path="/prometheus/cloud/configs")
    public List<PrometheusConfig> cloud() {

        // 从注册中心读取服务配置
        List<String> serviceIds = client.getServices();
        List<List<ServiceInstance>> instancesGroup = serviceIds.stream().map(serviceId -> client.getInstances(serviceId)).collect(Collectors.toList());

        // 转换服务配置
        List<PrometheusConfig> configs = instancesGroup.stream().map(instances -> {
            List<String> targets = new ArrayList<String>();
            Map<String, String> labels = new HashMap<String, String>();
            labels.put("cluster", "mooin-cluster");
            labels.put("application", "java");
            labels.put("service", instances.stream().findFirst().get().getServiceId());
            instances.forEach(instance -> {
                targets.add(instance.getHost()+":"+instance.getPort());
            });
            return new PrometheusConfig(targets, labels);
        }).collect(Collectors.toList());

        return configs;
    }

}
