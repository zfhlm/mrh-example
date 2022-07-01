package org.lushen.mrh.cloud.gateway.supports;

import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverContext;

/**
 * 默认实现，不做任何验证
 * 
 * @author hlm
 */
public class DefaultTokenRepository implements GatewayTokenRepository {

	@Override
	public void update(String token, GatewayDeliverContext context) {
		
	}

	@Override
	public boolean validate(String token, GatewayDeliverContext context) {
		return true;
	}

}
