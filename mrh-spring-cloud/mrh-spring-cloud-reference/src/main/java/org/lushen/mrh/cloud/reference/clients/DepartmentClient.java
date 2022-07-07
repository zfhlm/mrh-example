package org.lushen.mrh.cloud.reference.clients;

import org.lushen.mrh.cloud.reference.clients.department.Department;
import org.lushen.mrh.cloud.reference.fallback.DepartmentClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 部门服务
 * 
 * @author hlm
 */
@FeignClient(name="${feign.clients.department}", contextId="department-client", fallbackFactory=DepartmentClientFallbackFactory.class)
public interface DepartmentClient {

	/**
	 * 根据 ID 获取部门信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path="/api/department/{id}")
	public Department get(@PathVariable(name="id", required=true) long id);

}
