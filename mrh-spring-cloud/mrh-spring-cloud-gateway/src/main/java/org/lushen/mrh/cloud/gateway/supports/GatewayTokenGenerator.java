package org.lushen.mrh.cloud.gateway.supports;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 网关令牌生成器
 * 
 * @author hlm
 */
public interface GatewayTokenGenerator {

	/**
	 * 生成令牌
	 * 
	 * @param context
	 * @return
	 * @throws GatewayTokenException
	 */
	public String create(GatewayTokenObject object) throws GatewayTokenException;

	/**
	 * 解析令牌
	 * 
	 * @param token
	 * @return
	 * @throws GatewayTokenException
	 */
	public GatewayTokenObject parse(String token) throws GatewayTokenException;

	/**
	 * 令牌处理异常
	 * 
	 * @author hlm
	 */
	public static class GatewayTokenException extends Exception {

		private static final long serialVersionUID = 3156013436270961981L;

		public GatewayTokenException(String message, Throwable cause) {
			super(message, cause);
		}

		public GatewayTokenException(String message) {
			super(message);
		}

		public GatewayTokenException(Throwable cause) {
			super(cause);
		}

	}

	/**
	 * 令牌超时异常
	 * 
	 * @author hlm
	 */
	public static class GatewayTokenExpiredException extends GatewayTokenException {

		private static final long serialVersionUID = 4185473174414439671L;

		public GatewayTokenExpiredException(Throwable cause) {
			super(cause);
		}

		public GatewayTokenExpiredException(String message, Throwable cause) {
			super(message, cause);
		}

		public GatewayTokenExpiredException(String message) {
			super(message);
		}

	}

	/**
	 * JWT 令牌生成器
	 * 
	 * @author hlm
	 */
	public static class JsonWebTokenGenerator implements GatewayTokenGenerator {

		private static final String JWT_MESSAGE = "Jwt-Message";

		private static final ObjectMapper MAPPER = new ObjectMapper();

		static {
			MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			MAPPER.setSerializationInclusion(Include.NON_NULL);
			MAPPER.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
			MAPPER.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
			MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			MAPPER.registerModule(new JavaTimeModule());
		}

		private SignatureAlgorithm signature = SignatureAlgorithm.HS512;	//加密方式

		private String secret;												//加密秘钥

		@Override
		public String create(GatewayTokenObject object) throws GatewayTokenException {
			try {
				// 令牌信息
				Map<String, Object> claims = new HashMap<String, Object>();
				claims.put(JWT_MESSAGE, MAPPER.writeValueAsString(object));
				// 创建令牌
				JwtBuilder builder = Jwts.builder();
				builder.setClaims(claims);
				builder.setSubject(GatewayDeliverHeaders.JWT_TOKEN_HEADER);
				builder.setExpiration(new Date(System.currentTimeMillis() + object.getExpired().toMillis()));
				builder.signWith(this.signature, this.secret);
				return builder.compact();
			} catch(Exception e) {
				throw new GatewayTokenException(e.getMessage(), e);
			}
		}

		@Override
		public GatewayTokenObject parse(String token) throws GatewayTokenException {
			try {
				// 解析令牌
				JwtParser jwtParser = Jwts.parser().setSigningKey(this.secret);
				Claims claims = jwtParser.parseClaimsJws(token).getBody();
				// 令牌信息
				return MAPPER.readValue(claims.get(JWT_MESSAGE, String.class), GatewayTokenObject.class);
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

	}

}
