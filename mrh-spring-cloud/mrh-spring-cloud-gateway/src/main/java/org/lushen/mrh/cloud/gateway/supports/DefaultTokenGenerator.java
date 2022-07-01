package org.lushen.mrh.cloud.gateway.supports;

import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_NAME_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_SOURCE_HEADER;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lushen.mrh.cloud.gateway.supports.GatewayTokenException.GatewayTokenExpiredException;
import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT 令牌生成器
 * 
 * @author hlm
 */
public class DefaultTokenGenerator implements GatewayTokenGenerator {

	@NotNull
	private SignatureAlgorithm signature = SignatureAlgorithm.HS512;	//加密方式

	@NotBlank
	private String secret;												//加密秘钥

	@NotNull
	private Duration lifetime;											//生存时长

	@Override
	public String create(GatewayTokenContext context) throws GatewayTokenException {
		try {
			// 令牌信息
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put(JWT_DELIVER_ID_HEADER, context.id());
			claims.put(JWT_DELIVER_NAME_HEADER, context.decodedName());
			claims.put(JWT_DELIVER_ROLE_ID_HEADER, context.roleId());
			claims.put(JWT_DELIVER_SOURCE_HEADER, context.source());
			// 创建令牌
			JwtBuilder builder = Jwts.builder();
			builder.setClaims(claims);
			builder.setSubject(GatewayDeliverHeaders.JWT_TOKEN_HEADER);
			builder.setExpiration(new Date(System.currentTimeMillis()+this.lifetime.toMillis()));
			builder.signWith(this.signature, this.secret);
			return builder.compact();
		} catch(Exception e) {
			throw new GatewayTokenException(e.getMessage(), e);
		}
	}

	@Override
	public GatewayTokenContext parse(String token) throws GatewayTokenException {
		try {
			// 解析令牌
			JwtParser jwtParser = Jwts.parser().setSigningKey(this.secret);
			Claims claims = jwtParser.parseClaimsJws(token).getBody();
			// 令牌信息
			Object id = claims.get(JWT_DELIVER_ID_HEADER);
			Object name = claims.get(JWT_DELIVER_NAME_HEADER);
			Object roleId = claims.get(JWT_DELIVER_ROLE_ID_HEADER);
			Object source = claims.get(JWT_DELIVER_SOURCE_HEADER);
			return new GatewayTokenContext(id, name, roleId, source);
		} catch(ExpiredJwtException e) {
			throw new GatewayTokenExpiredException(e.getMessage(), e);
		} catch(Exception e) {
			throw new GatewayTokenException(e.getMessage(), e);
		}
	}

	public SignatureAlgorithm getSignature() {
		return signature;
	}

	public void setSignature(SignatureAlgorithm signature) {
		this.signature = signature;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Duration getLifetime() {
		return lifetime;
	}

	public void setLifetime(Duration lifetime) {
		this.lifetime = lifetime;
	}

}
