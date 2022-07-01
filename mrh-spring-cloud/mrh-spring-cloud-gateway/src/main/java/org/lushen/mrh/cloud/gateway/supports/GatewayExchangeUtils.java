package org.lushen.mrh.cloud.gateway.supports;

import java.util.List;

/**
 * 工具类
 * 
 * @author hlm
 */
public class GatewayExchangeUtils {

	private static final String EXCHANGE_PREFIX = GatewayExchangeUtils.class.getName() + ".";

	/**
	 * api exchange attribute {@link org.lushen.mrh.cloud.reference.gateway.GatewayApi}
	 */
	public static final String EXCHANGE_CONTEXT_GATEWAY_API = EXCHANGE_PREFIX + "exchange_context_gateway_api";

	/**
	 * print request line exchange attribute {true, false}
	 */
	public static final String EXCHANGE_PRINT_REQUEST_LINE_ENABLED = EXCHANGE_PREFIX + "exchange_print_request_line_enabled";

	/**
	 * print request body exchange attribute {true, false}
	 */
	public static final String EXCHANGE_PRINT_REQUEST_JSON_BODY_ENABLED = EXCHANGE_PREFIX + "exchange_print_request_json_body_enabled";

	/**
	 * print response body exchange attribute {true, false}
	 */
	public static final String EXCHANGE_PRINT_RESPONSE_JSON_BODY_ENABLED = EXCHANGE_PREFIX + "exchange_print_response_json_body_enabled";

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
