package org.lushen.mrh.example.elasticsearch.spring.data;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API 接口，简单示例，更多规则查看官方文档 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html
 * 
 * @author hlm
 */
@RestController
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	private UserRepository repository;

	/**
	 * 查询文档
	 */
	@GetMapping(path="query")
	public String query() {
		return String.valueOf(repository.findById(1L).orElse(null));
	}

	/**
	 * 保存文档
	 */
	@GetMapping(path="save")
	public String save() {
		UserDocument entity = new UserDocument();
		entity.setId(2L);
		entity.setName("lisi");
		entity.setAddress("北京");
		entity.setTimestamp(LocalDate.now());
		return String.valueOf(repository.save(entity));
	}

	/**
	 * 根据名称查询文档
	 */
	@GetMapping(path="name/query")
	public String queryByName() {
		return String.valueOf(repository.findByName("lisi"));
	}

	/**
	 * 根据名称查询文档
	 */
	@GetMapping(path="address/query")
	public String queryByAddress() {
		return String.valueOf(repository.findByAddress("京"));
	}

}
