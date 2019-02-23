package com.emarbox.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DruidConfigAuthProperties {
	
	private String driverClassName;

	private String jdbcUrl;

	private String username;

	private String password;

	private Integer minIdle;

	private Integer maxActive;

	private Integer maxWait;

	private Long timeBetweenEvictionRunsMillis;

	private Long minEvictableIdleTimeMillis;

	private String validationQuery;

	private Boolean testWhileIdle;

	private Boolean testOnBorrow;

	private Boolean testOnReturn;

	private Boolean poolPreparedStatements;

	private Integer maxPoolPreparedStatementPerConnectionSize;

	private String filters;
	
}
