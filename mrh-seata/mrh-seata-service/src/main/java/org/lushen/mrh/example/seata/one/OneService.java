package org.lushen.mrh.example.seata.one;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "mrh-seata-service-one", path = "/", contextId = "service.one")
public interface OneService {

	@GetMapping(path="one/service/test")
	public void test();

}
