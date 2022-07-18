package org.lushen.mrh.boot.seata.at.svc;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 积分服务
 * 
 * @author hlm
 */
@Service("integralService")
public class IntegralService {

	private RestTemplate template = new RestTemplate();

	/**
	 * 增加积分，调用 http  接口
	 * 
	 * @param quantity
	 * @return
	 */
	public Integer add(Integer quantity) {

		System.out.println("add integral :: quantity = " + quantity);

		ResponseEntity<String> res = template.getForEntity("http://localhost:8888/add", String.class);
		Integer id = Integer.parseInt(res.getBody());

		System.out.println("add integral :: id = " + id);

		return id;
	}

	/**
	 * 撤销积分，调用 http  接口
	 * 
	 * @param id
	 */
	public void del(Integer id) {

		System.out.println("del integral :: id = " + id);

		ResponseEntity<String> res = template.getForEntity("http://localhost:8888/del/"+id, String.class);

		System.out.println("del integral :: res = " + res);

	}

}
