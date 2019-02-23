package com.emarbox.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.emarbox.mapper.auth"}, sqlSessionTemplateRef  = "authSqlSessionTemplate")
public class DataSourceAuthConfig {
	
    @Autowired
    private DruidConfigAuthProperties configAuthProperties;

    @Bean(name = "authDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource testDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(configAuthProperties.getDriverClassName());
        druidDataSource.setUrl(configAuthProperties.getJdbcUrl());
        druidDataSource.setUsername(configAuthProperties.getUsername());
        druidDataSource.setPassword(configAuthProperties.getPassword());
        druidDataSource.setInitialSize(configAuthProperties.getMinIdle());
        druidDataSource.setMinIdle(configAuthProperties.getMinIdle());
        druidDataSource.setMaxActive(configAuthProperties.getMaxActive());
        druidDataSource.setMaxWait(configAuthProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(configAuthProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(configAuthProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(configAuthProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(configAuthProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(configAuthProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(configAuthProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(configAuthProperties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(configAuthProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setFilters(configAuthProperties.getFilters());
        return druidDataSource;
    }

    @Bean(name = "authSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("authDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[] {interceptor});
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/auth/*.xml"));
		bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		bean.getObject().getConfiguration().setCallSettersOnNulls(true);
        return bean.getObject();
    }

    @Bean(name = "authTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("authDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "authSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("authSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    

	@Bean(name = "namedParameterAuthJdbcTemplate")
	public NamedParameterJdbcTemplate namedParameterAuthJdbcTemplate(@Qualifier("authDataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean(name = "readJdbcTemplate")
	public JdbcTemplate secondaryJdbcTemplate(@Qualifier("authDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
}
