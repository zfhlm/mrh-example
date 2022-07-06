package org.lushen.mrh.cloud.reference.fallback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.reference.clients.OrganClient;
import org.lushen.mrh.cloud.reference.clients.organ.Organ;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 机构服务 fallback
 * 
 * @author hlm
 */
public class OrganClientFallbackFactory implements FallbackFactory<OrganClient> {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public OrganClient create(Throwable cause) {
		log.error(cause, cause);
		return new OrganClient() {
			@Override
			public Organ get(long id) {
				log.info("fallback get");
				return new Organ();
			}
			@Override
			public void add(Organ organ) {
				log.info("fallback add");
			}
		};
	}

}
