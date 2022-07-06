package org.lushen.mrh.cloud.api.admin.web;

import javax.servlet.http.HttpServletResponse;

import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders;
import org.lushen.mrh.cloud.reference.supports.ViewResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限测试接口
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="api/permission")
public class PermissionController {

	@PostMapping(path="login")
	public ViewResult login(HttpServletResponse response) {
		response.setHeader(GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER, String.valueOf(1));
		response.setHeader(GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER, "test");
		response.setHeader(GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER, String.valueOf(1));
		response.setHeader(GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER, String.valueOf(2));
		response.setHeader(GatewayDeliverHeaders.JWT_DELIVER_EXPIRED_HEADER, "2h");
		return ViewResult.create();
	}

	@GetMapping(path="update")
	public ViewResult update() {
		return ViewResult.create();
	}

}
