package org.lushen.mrh.cloud.reference.fallback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.reference.clients.DepartmentClient;
import org.lushen.mrh.cloud.reference.clients.department.Department;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 部门服务 fallback
 * 
 * @author hlm
 */
public class DepartmentClientFallbackFactory implements FallbackFactory<DepartmentClient> {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public DepartmentClient create(Throwable cause) {
		log.error(cause, cause);
		return new DepartmentClient() {
			@Override
			public Department get(long id) {
				log.info("fallback get");
				return new Department();
			}
		};
	}

}
