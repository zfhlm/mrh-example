package org.lushen.mrh.cloud.reference.clients;

import org.lushen.mrh.cloud.reference.clients.organ.Organ;
import org.lushen.mrh.cloud.reference.fallback.OrganClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 机构服务
 * 
 * @author hlm
 */
@FeignClient(name="${feign.clients.organ}", contextId="organClient", fallbackFactory=OrganClientFallbackFactory.class)
public interface OrganClient {

	/**
	 * 根据 ID 获取机构信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path="/api/organ/{id}")
	public Organ get(@PathVariable(name="id", required=true) long id);

	/**
	 * 添加机构信息
	 * 
	 * @param organ
	 */
	@PostMapping(path="/api/organ")
	public void add(@RequestBody Organ organ);

}
