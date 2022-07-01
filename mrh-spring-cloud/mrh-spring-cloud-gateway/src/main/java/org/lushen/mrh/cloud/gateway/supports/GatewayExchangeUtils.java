package org.lushen.mrh.cloud.gateway.supports;

import java.util.List;

import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;

/**
 * 工具类
 * 
 * @author hlm
 */
public class GatewayExchangeUtils {

	public static final int PRINT_CIRCUIT_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 300;

	public static final int PRINT_CIRCUIT_BASE_ON_API_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 200;

	public static final int PRINT_REQUEST_LINE_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 100;

	public static final int PRINT_REQUEST_JSON_BODY_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 99;

	public static final int PRINT_RESPONSE_JSON_BODY_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 99;

	public static final int MODIFY_LOGIN_RESPONSE_BODY_FILTER_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 50;

	// =====================================================================================================================

	private static final String EXCHANGE_PREFIX = GatewayExchangeUtils.class.getName() + ".";

	public static final String EXCHANGE_CONTEXT_GATEWAY_API = EXCHANGE_PREFIX + "exchange_context_gateway_api";

	public static final String EXCHANGE_PRINT_REQUEST_LINE_ENABLED = EXCHANGE_PREFIX + "exchange_print_request_line_enabled";

	public static final String EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED = EXCHANGE_PREFIX + "exchange_print_request_json_body_enabled";

	public static final String EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED = EXCHANGE_PREFIX + "exchange_print_response_json_body_enabled";

	// =====================================================================================================================

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

}
