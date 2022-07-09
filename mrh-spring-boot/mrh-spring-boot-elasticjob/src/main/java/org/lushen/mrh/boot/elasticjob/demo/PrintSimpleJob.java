package org.lushen.mrh.boot.elasticjob.demo;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintSimpleJob implements SimpleJob {

	@Autowired
	private TestService testService;
	
	@Override
	public void execute(ShardingContext shardingContext) {
		
		System.out.println(shardingContext.getJobName());
		System.out.println(shardingContext.getJobParameter());
		System.out.println(shardingContext.getShardingItem());
		System.out.println(shardingContext.getShardingParameter());
		System.out.println(shardingContext.getShardingTotalCount());
		System.out.println(shardingContext.getTaskId());
		
		testService.test();
		
	}

}
