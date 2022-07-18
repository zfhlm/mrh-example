package org.lushen.mrh.boot.seata.at.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;

/**
 * seata 配置
 * 
 * @author hlm
 */
@Configuration
public class SeataConfiguration {

	@Bean
	public DbStateMachineConfig dbStateMachineConfig(DataSource dataSource) {
		DbStateMachineConfig config = new DbStateMachineConfig();
		config.setDataSource(dataSource);
		config.setResources(new String[] {"classpath:statelang/*.json"});
		return config;
	}

	@Bean
	public ProcessCtrlStateMachineEngine processCtrlStateMachineEngine(DbStateMachineConfig config) {
		ProcessCtrlStateMachineEngine engine = new ProcessCtrlStateMachineEngine();
		engine.setStateMachineConfig(config);
		return engine;
	}

	@Bean
	public StateMachineEngineHolder stateMachineEngineHolder(StateMachineEngine engine) {
		StateMachineEngineHolder engineHolder = new StateMachineEngineHolder();
		engineHolder.setStateMachineEngine(engine);
		return engineHolder;
	}

}
