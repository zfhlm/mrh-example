package org.lushen.mrh.boot.seata.at.rest;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * TCC 接口
 * 
 * @author hlm
 */
@LocalTCC
public interface TestTccAction {

	// useTCCFence=true 处理空回滚、事务悬挂
	// 注意处理  commitTcc、cancelTcc 幂等问题

	@TwoPhaseBusinessAction(name = "prepareTcc", commitMethod = "commitTcc", rollbackMethod = "cancelTcc", useTCCFence=true)
	public String prepareTcc(@BusinessActionContextParameter(paramName="parameter") TestTccParameter parameter);

	public void commitTcc(BusinessActionContext context);

	public void cancelTcc(BusinessActionContext context);

}
