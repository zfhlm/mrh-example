package org.lushen.mrh.boot.id.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import com.baidu.fsg.uid.worker.dao.WorkerNodeDAO;
import com.baidu.fsg.uid.worker.entity.WorkerNodeEntity;

/**
 * baidu uid 配置
 * 
 * @author hlm
 */
@Configuration
public class BaiduUidConfiguration {

	// 适配  ID 生成器
	@Bean
	public IdGenerator<Long> idGenerator(CachedUidGenerator generator) {
		return () -> generator.getUID();
	}

	// 自定义持久化，不使用自带的 mybatis 实现
	@Bean
	public WorkerNodeDAO workerNodeDAO(JdbcTemplate jdbcTemplate) {
		return new WorkerNodeDAO() {
			@Override
			public WorkerNodeEntity getWorkerNodeByHostPort(String host, String port) {
				String sql = "SELECT ID, HOST_NAME, PORT, TYPE, LAUNCH_DATE, MODIFIED, CREATED FROM WORKER_NODE WHERE HOST_NAME=? AND PORT=?";
				WorkerNodeEntity e = jdbcTemplate.query(sql, ps -> {
					ps.setObject(1, host);
					ps.setObject(2, port);
				}, rs -> {
					WorkerNodeEntity entity = new WorkerNodeEntity();
					entity.setId(rs.getLong("ID"));
					entity.setHostName(rs.getString("HOST_NAME"));
					entity.setPort(rs.getString("PORT"));
					entity.setType(rs.getInt("TYPE"));
					entity.setLaunchDateDate(rs.getDate("LAUNCH_DATE"));
					entity.setCreated(rs.getDate("CREATED"));
					entity.setModified(rs.getDate("MODIFIED"));
					return entity;
				});
				return e;
			}
			@Override
			public void addWorkerNode(WorkerNodeEntity entity) {
				String sql = "INSERT INTO WORKER_NODE (HOST_NAME, PORT, TYPE, LAUNCH_DATE, MODIFIED, CREATED) VALUES (?, ?, ?, ?, NOW(), NOW())";
				jdbcTemplate.update(sql, ps -> {
					ps.setObject(1, entity.getHostName());
					ps.setObject(2, entity.getPort());
					ps.setObject(3, entity.getType());
					ps.setObject(4, entity.getLaunchDate());
				});
			}
		};
	}

	@Bean
	public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
		return new DisposableWorkerIdAssigner();
	}

	@Bean
	public CachedUidGenerator cachedUidGenerator(DisposableWorkerIdAssigner workerIdAssigner) {
		CachedUidGenerator uidGenerator = new CachedUidGenerator();
		uidGenerator.setWorkerIdAssigner(workerIdAssigner);
		uidGenerator.setTimeBits(29);
		uidGenerator.setWorkerBits(21);
		uidGenerator.setSeqBits(13);
		uidGenerator.setEpochStr("2022-01-01");
		return uidGenerator;
	}

}
