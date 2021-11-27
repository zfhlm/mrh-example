package org.lushen.mrh.example.elasticsearch.welcome;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 * 
 * @author hlm
 */
@RestController
@RequestMapping
public class WelcomeController {

	@GetMapping(path="/")
	public String index() throws IOException {
		return "welcome";
	}

}
