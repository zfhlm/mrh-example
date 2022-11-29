package org.lushen.mrh.cloud.service.prometheus.rest;

import java.util.List;
import java.util.Map;

/**
 * Prometheus scrape_configs job 配置
 * 
 * @author hlm
 */
public class PrometheusConfig {

    private List<String> targets;

    private Map<String, String> labels;

    public PrometheusConfig(List<String> targets, Map<String, String> labels) {
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
