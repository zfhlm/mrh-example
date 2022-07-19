package org.lushen.mrh.boot.id.generator.config;

/**
 * leaf 接口配置
 * 
 * @author hlm
 */
public class LeafServerProperties {
	
	private String snowflakeUrl;
	
	private String segmentUrl;

	public String getSnowflakeUrl() {
		return snowflakeUrl;
	}

	public void setSnowflakeUrl(String snowflakeUrl) {
		this.snowflakeUrl = snowflakeUrl;
	}

	public String getSegmentUrl() {
		return segmentUrl;
	}

	public void setSegmentUrl(String segmentUrl) {
		this.segmentUrl = segmentUrl;
	}

}
