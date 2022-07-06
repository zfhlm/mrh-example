package org.lushen.mrh.cloud.api.simple.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lushen.mrh.cloud.reference.clients.DepartmentClient;
import org.lushen.mrh.cloud.reference.clients.OrganClient;
import org.lushen.mrh.cloud.reference.supports.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class TestController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private OrganClient organClient;
	@Autowired
	private DepartmentClient departmentClient;

	@GetMapping(path="/v1/test/list")
	public ViewResult list() {

		Map<String, Object> map = new HashMap<>();
		map.put("organs", Arrays.asList(organClient.get(1), organClient.get(2)));
		map.put("departments", Arrays.asList(departmentClient.get(1), departmentClient.get(2)));

		log.info(map);

		return ViewResult.create(map);
	}

}
