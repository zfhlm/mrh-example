package org.lushen.mrh.cloud.reference.supports.log4j2;

import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ID_HEADER;
import static org.lushen.mrh.cloud.reference.gateway.GatewayDeliverHeaders.JWT_DELIVER_ROLE_ID_HEADER;

import java.util.function.Function;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * log4j2 输出用户相关信息
 * 
 * @author hlm
 */
@Plugin(name = "Log4j2UserPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"user", "USER"})
@PerformanceSensitive("allocation")
public class Log4j2UserPatternConverter extends LogEventPatternConverter {

	private Log4j2UserPatternConverter() {
		super("User", "user");
	}
	public static Log4j2UserPatternConverter newInstance(final String[] options) {
		return new Log4j2UserPatternConverter();
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {

		// 当前请求上下文
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

		// 获取请求头
		Function<String, String> getHeaderValue = (name -> {
			if(attributes instanceof ServletRequestAttributes) {
				return ((ServletRequestAttributes)attributes).getRequest().getHeader(name);
			}
			else if(attributes instanceof WebRequest) {
				return ((WebRequest)attributes).getHeader(name);
			} else {
				return null;
			}
		});
		String id = getHeaderValue.apply(JWT_DELIVER_ID_HEADER);
		String roleId = getHeaderValue.apply(JWT_DELIVER_ROLE_ID_HEADER);

		// 追加到日志输出
		if(id != null && roleId != null) {
			toAppendTo.append(id).append("-").append(roleId);
		}

	}

}
