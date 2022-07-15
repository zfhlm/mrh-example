package org.lushen.mrh.cloud.service.zookeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import reactor.core.publisher.Mono;

/**
 * 权重轮询策略
 * 
 * // 假设节点及其权重  A=2  B=1  C=4  D=2  权重轮询顺序： A-B-C-D-A-C-D-C-C  A-B-C-D-A-C-D-C-C
 * // 轮询比值从 1 开始，每次轮询比值加 1，若当次轮询比值大于权重，则跳过该节点，直到所有节点轮询完毕，从新开始下一轮
 * 
 * @author hlm
 */
public class WeightRoundRobinLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	private static final Log log = LogFactory.getLog(WeightRoundRobinLoadBalancer.class);

	private final String serviceId;

	private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

	private final AtomicInteger currWeight = new AtomicInteger(1);

	private final AtomicInteger offset = new AtomicInteger(0);

	public WeightRoundRobinLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
		this.serviceId = serviceId;
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
				.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next().map(serviceInstances -> processInstanceResponse(supplier, serviceInstances));
	}

	private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, List<ServiceInstance> serviceInstances) {
		Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
		if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
			((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
		}
		return serviceInstanceResponse;
	}

	private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {

		if (instances.isEmpty()) {
			if (log.isWarnEnabled()) {
				log.warn("No servers available for service: " + serviceId);
			}
			return new EmptyResponse();
		}

		// 只有一个节点，直接返回
		if(instances.size() == 1) {
			return new DefaultResponse(instances.get(0));
		}

		// 排序
		List<ServiceInstance> sortedInstances = new ArrayList<ServiceInstance>(instances);
		Collections.sort(sortedInstances, Comparator.comparing(ServiceInstance::getInstanceId));

		synchronized (this) {

			while(true) {

				// 根据偏移量，获取筛选节点列表
				List<ServiceInstance> subInstances = sortedInstances.subList(offset.get(), sortedInstances.size());

				for(ServiceInstance instance : subInstances) {

					// 偏移量+1，超出则重置
					if(offset.incrementAndGet() == sortedInstances.size()) {
						offset.set(0);
					}

					// 大于等于权重比值，返回该节点
					if(getWeight(instance) >= currWeight.get()) {
						return new DefaultResponse(instance);
					}
					
					// 如果筛选列表为全量，重置权重比值，继续下一次筛选；否则权重比值+1，继续下一次筛选
					if(subInstances.size() == sortedInstances.size()) {
						currWeight.set(1);
					} else {
						currWeight.incrementAndGet();
					}

				}

			}

		}

	}

	private int getWeight(ServiceInstance instance) {
		try {
			return Optional.ofNullable(instance.getMetadata()).map(e -> e.get("weight")).map(Integer::parseInt).filter(e -> e>0).orElse(1);
		} catch (Exception e2) {
			return 1;
		}
	}

}
