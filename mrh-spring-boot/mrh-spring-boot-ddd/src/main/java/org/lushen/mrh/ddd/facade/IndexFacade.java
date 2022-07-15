package org.lushen.mrh.ddd.facade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 根路径界面
 * 
 * @author hlm
 */
@RestController
@RequestMapping
public class IndexFacade {

	@GetMapping(path="/")
	public String welcome() {
		return "welcome";
	}

}
