package org.lushen.mrh.cloud.gateway.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.gateway.supports.GatewayApiRepository;
import org.lushen.mrh.cloud.gateway.supports.GatewayRoleRepository;
import org.lushen.mrh.cloud.reference.gateway.GatewayApi;
import org.lushen.mrh.cloud.reference.gateway.GatewayPermissionEvent;
import org.lushen.mrh.cloud.reference.gateway.GatewayRole;
import org.lushen.mrh.cloud.reference.gateway.GatewayStartedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * 网关 bus 事件发布与监听
 * 
 * @author hlm
 */
@Component
public class GatewayEventPublisherListener implements ApplicationListener<ApplicationEvent>, GatewayApiRepository, GatewayRoleRepository {

	private final Log log = LogFactory.getLog(getClass());

	private final AtomicReference<PermissionCache> cacheHolder = new AtomicReference<>(new PermissionCache());

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private BusProperties busProperties;
	@Autowired
	private Destination.Factory destinationFactory;

	@Override
	public synchronized void onApplicationEvent(ApplicationEvent event) {

		// 网关启动完成，发布网关已启动事件
		if (event instanceof ApplicationReadyEvent) {
			GatewayStartedEvent gatewayStartedEvent = new GatewayStartedEvent(this, busProperties.getId(), destinationFactory.getDestination(null));
			applicationContext.publishEvent(gatewayStartedEvent);
			log.info("Publish event " + gatewayStartedEvent);
		}

		// 监听到网关权限信息事件，更新权限信息
		if(event instanceof GatewayPermissionEvent) {

			log.info("Handle event " + event);

			GatewayPermissionEvent permissionEvent = (GatewayPermissionEvent)event;

			if(cacheHolder.get().getVersion() >= permissionEvent.getVersion()) {
				return;
			}

			log.info(String.format("refresh permissions, version number from %s to %s", cacheHolder.get().getVersion(), permissionEvent.getVersion()));

			MultiKeyMap<String, GatewayApi> apiCache = new MultiKeyMap<>();
			Optional.ofNullable(permissionEvent.getApis()).orElse(Collections.emptyList()).forEach(api -> {
				apiCache.put(api.getMethod().name(), api.getPath(), api);
			});

			Map<Long, GatewayRole> roleCache = new HashMap<>();
			Optional.ofNullable(permissionEvent.getRoles()).orElse(Collections.emptyList()).forEach(role -> {
				roleCache.put(role.getId(), role);
			});

			cacheHolder.set(new PermissionCache(apiCache, roleCache, permissionEvent.getVersion()));
		}

	}

	@Override
	public GatewayApi get(HttpMethod method, String path) {
		return cacheHolder.get().getApiCache().get(method.name(), path);
	}

	@Override
	public GatewayRole get(long id) {
		return cacheHolder.get().getRoleCache().get(id);
	}

	/**
	 * 缓存信息对象
	 * 
	 * @author hlm
	 */
	private class PermissionCache {

		private MultiKeyMap<String, GatewayApi> apiCache;

		private Map<Long, GatewayRole> roleCache;

		private long version;

		public PermissionCache() {
			this(new MultiKeyMap<>(), new HashMap<>(), 0);
		}

		public PermissionCache(MultiKeyMap<String, GatewayApi> apiCache, Map<Long, GatewayRole> roleCache, long version) {
			super();
			this.apiCache = apiCache;
			this.roleCache = roleCache;
			this.version = version;
		}

		public MultiKeyMap<String, GatewayApi> getApiCache() {
			return apiCache;
		}

		public Map<Long, GatewayRole> getRoleCache() {
			return roleCache;
		}

		public long getVersion() {
			return version;
		}

	}

}
