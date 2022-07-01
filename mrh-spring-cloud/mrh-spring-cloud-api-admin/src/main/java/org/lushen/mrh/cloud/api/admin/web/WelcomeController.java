package org.lushen.mrh.cloud.api.admin.web;

import javax.servlet.http.HttpServletResponse;

import org.lushen.mrh.cloud.reference.supports.ViewResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 * 
 * @author hlm
 */
@RestController
@RequestMapping("api/welcome")
public class WelcomeController {

	@RequestMapping(path="")
	public ViewResult index(HttpServletResponse response) {
		return ViewResult.create(0, "success");
	}

}
