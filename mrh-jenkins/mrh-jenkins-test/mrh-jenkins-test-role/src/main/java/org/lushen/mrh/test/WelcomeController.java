package org.lushen.mrh.test;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 欢迎页
 * 
 * @author hlm
 */
@RestController
@RequestMapping
public class WelcomeController {

	/**
	 * 欢迎页
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping(path="/")
	public String index() throws IOException {
		return "success";
	}

}
