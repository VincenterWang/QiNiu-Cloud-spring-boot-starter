package com.vincent.tools.qiniu.cloud.spring.boot.starter.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: Vincent
 * <p> DateTime: 2023/10/17 21:04
 * <p> Description: 配置属性类
 * <p> TODO:
 **/
@Component
@ConfigurationProperties(prefix = "qinniu.cloud")
public class QiNiuCloudProperties {
	
	/**
	 * 查询文件夹中文件最大数量
	 */
	private int limit;
	
	/**
	 * 目标文件夹
	 */
	private String prefix;
	/**
	 * 访问密钥
	 */
	private String accessKey;
	/**
	 * 安全密钥
	 */
	private String secretKey;
	/**
	 * 命名空间
	 */
	private String bucket;
	
	/**
	 * 带协议的域名
	 */
	private String domain;
	
	public QiNiuCloudProperties() {
	}
	
	public QiNiuCloudProperties(String accessKey, String secretKey, String bucket, String domain) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.bucket = bucket;
		this.domain = domain;
	}
	
	public String getAccessKey() {
		return accessKey;
	}
	
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public String getBucket() {
		return bucket;
	}
	
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
