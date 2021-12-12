package org.lushen.mrh.example.seata.two;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "mrh-seata-service-two", path = "/", contextId = "service.two")
public interface TwoService {

	@GetMapping(path="two/service/test")
	public void test();

}
