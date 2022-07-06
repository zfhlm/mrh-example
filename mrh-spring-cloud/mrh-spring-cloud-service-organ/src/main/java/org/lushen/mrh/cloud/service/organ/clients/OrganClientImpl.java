package org.lushen.mrh.cloud.service.organ.clients;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.reference.clients.OrganClient;
import org.lushen.mrh.cloud.reference.clients.organ.Organ;
import org.lushen.mrh.cloud.reference.supports.ServiceBusinessException;
import org.lushen.mrh.cloud.reference.supports.ServiceStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganClientImpl implements OrganClient {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public Organ get(long id) {

		Organ organ = new Organ();
		organ.setId(id);
		organ.setName(UUID.randomUUID().toString());

		log.info(organ);

		return organ;
	}

	@Override
	public void add(Organ organ) {
		log.info(organ);
		throw new ServiceBusinessException(ServiceStatus.EXTEND_SERVER_ERROR, new RuntimeException("测试"));
	}

}
