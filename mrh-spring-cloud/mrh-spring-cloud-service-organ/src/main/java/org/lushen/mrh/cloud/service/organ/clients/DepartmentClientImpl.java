package org.lushen.mrh.cloud.service.organ.clients;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.reference.clients.DepartmentClient;
import org.lushen.mrh.cloud.reference.clients.department.Department;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentClientImpl implements DepartmentClient {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public Department get(long id) {

		Department department = new Department();
		department.setId(id);
		department.setName(UUID.randomUUID().toString());
		department.setOrgId(System.currentTimeMillis()%2 + 1);

		log.info(department);

		return department;
	}

}
