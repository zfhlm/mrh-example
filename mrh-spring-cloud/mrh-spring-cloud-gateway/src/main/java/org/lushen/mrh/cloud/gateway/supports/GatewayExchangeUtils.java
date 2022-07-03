package org.lushen.mrh.cloud.gateway.supports;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationContext;

/**
 * 工具类
 * 
 * @author hlm
 */
public class GatewayExchangeUtils {

	// 过滤器 order 数值
	public static class Orders {

		public static final int PRINT_CIRCUIT_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 300;

		public static final int PRINT_CIRCUIT_BASE_ON_API_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 200;

		public static final int PRINT_REQUEST_LINE_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 100;

		public static final int PRINT_REQUEST_JSON_BODY_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 99;

		public static final int PRINT_RESPONSE_JSON_BODY_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 99;

		public static final int MODIFY_LOGIN_RESPONSE_BODY_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 50;

	}

	// 过滤器 exchange attribute name
	public static class Exchanges {

		private static final String EXCHANGE_PREFIX = GatewayExchangeUtils.class.getName() + ".";

		public static final String EXCHANGE_CONTEXT_GATEWAY_API = EXCHANGE_PREFIX + "exchange_context_gateway_api";

		public static final String EXCHANGE_PRINT_REQUEST_LINE_ENABLED = EXCHANGE_PREFIX + "exchange_print_request_line_enabled";

		public static final String EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED = EXCHANGE_PREFIX + "exchange_print_request_json_body_enabled";

		public static final String EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED = EXCHANGE_PREFIX + "exchange_print_response_json_body_enabled";

	}

	// 日志追踪 sleuth bean
	public static class Sleuth {

		private static final AtomicReference<Tracer> TRACER_HOLDER = new AtomicReference<Tracer>();

		private static final AtomicReference<CurrentTraceContext> CURRENT_TRACE_CONTEXT_HOLDER = new AtomicReference<CurrentTraceContext>();

		public static final void initialize(ApplicationContext applicationContext) {
			TRACER_HOLDER.set(applicationContext.getBean(Tracer.class));
			CURRENT_TRACE_CONTEXT_HOLDER.set(applicationContext.getBean(CurrentTraceContext.class));
		}

		public static final Tracer tracer() {
			return TRACER_HOLDER.get();
		}

		public static final CurrentTraceContext currentTraceContext() {
			return CURRENT_TRACE_CONTEXT_HOLDER.get();
		}

	}

	/**
	 * 按顺序将所有 byte[] 合并为一个 byte[]
	 * 
	 * @param buffers
	 * @return
	 */
	public static final byte[] mergeByteArrays(List<byte[]> buffers) {
		if(buffers == null || buffers.size() == 0) {
			return new byte[0];
		}
		if(buffers.size() == 1) {
			return buffers.get(0);
		}
		byte[] content = new byte[buffers.stream().mapToInt(e -> e.length).sum()];
		int offset = 0;
		for(byte[] array : buffers) {
			for(byte ele : array) {
				content[offset++] = ele;
			}
		}
		return content;
	}

	/**
	 * URL encode
	 * 
	 * @param arg
	 * @return
	 */
	public static final String urlEncode(String arg) {
		if(arg == null) {
			return null;
		}
		try {
			return URLEncoder.encode(arg, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * URL decode
	 * 
	 * @param arg
	 * @return
	 */
	public static final String urlDecode(String arg) {
		if(arg == null) {
			return null;
		}
		try {
			return URLDecoder.decode(arg, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
