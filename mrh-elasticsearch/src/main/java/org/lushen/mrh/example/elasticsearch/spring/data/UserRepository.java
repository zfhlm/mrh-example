package org.lushen.mrh.example.elasticsearch.spring.data;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 查询接口，根据方法名称动态生成查询条件，可以嵌套组合，更多规则查看官方文档 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html
 * 
 * @author hlm
 */
public interface UserRepository extends ElasticsearchRepository<UserDocument, Long> {

	public List<UserDocument> findByName(String name);

	public List<UserDocument> findByAddress(String address);

}
