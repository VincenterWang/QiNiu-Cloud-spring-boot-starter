package com.vincent.tools.qiniu.cloud.spring.boot.starter.configure;

import com.vincent.tools.qiniu.cloud.spring.boot.starter.core.QiNiuCloudHandler;
import com.vincent.tools.qiniu.cloud.spring.boot.starter.core.QiNiuCloudProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Author: Vincent
 * <p> DateTime: 2023/10/17 21:03
 * <p> Description:
 * <p> TODO:
 **/
@AutoConfiguration
@ConditionalOnClass(com.qiniu.storage.Configuration.class)
@Import(QiNiuCloudProperties.class)
public class QiNiuCloudConfiguration {
	
	@Bean
	public QiNiuCloudHandler qiNiuCloudHandler(QiNiuCloudProperties properties){
		return new QiNiuCloudHandler(properties);
	}
}
