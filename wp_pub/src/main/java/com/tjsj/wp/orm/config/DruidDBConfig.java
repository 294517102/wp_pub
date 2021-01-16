package com.tjsj.wp.orm.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;


@Configuration
@ConfigurationProperties(prefix="spring.datasource")
@PropertySource("config/db-config.properties")
public class DruidDBConfig {
	
	private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);  
     
    private String url;  
      
    private String username;  
      
    private String password;  
      
    private String driverClassName;  
      
    private int initialSize;  
      
    private int minIdle;  
      
    private int maxActive;  
       
    private int maxWait;  
      
    private int timeBetweenEvictionRunsMillis;  
      
    private int minEvictableIdleTimeMillis;  
      
    private String validationQuery;  
      
    private boolean testWhileIdle;  
      
    private boolean testOnBorrow;  
       
    private boolean testOnReturn;  
      
    private boolean poolPreparedStatements;  
       
    private int maxPoolPreparedStatementPerConnectionSize;  
       
    private String filters;  
       
    private String connectionProperties;  
    
    private String durid_allow;
    
    private String durid_deny;
    
    private String durid_username;
    
    private String durid_password;
    
    private String durid_resetEnable;
    
    
    @Bean     //声明其为Bean实例  
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource  
    public DataSource dataSource(){  
        DruidDataSource dataSource = new DruidDataSource();  
        
        dataSource.setUrl(url);  
        dataSource.setUsername(username);  
        dataSource.setPassword(password);  
        dataSource.setDriverClassName(driverClassName);  
          
        //configuration  
        dataSource.setInitialSize(initialSize);  
        dataSource.setMinIdle(minIdle);  
        dataSource.setMaxActive(maxActive);  
        dataSource.setMaxWait(maxWait);  
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        dataSource.setValidationQuery(validationQuery);  
        dataSource.setTestWhileIdle(testWhileIdle);  
        dataSource.setTestOnBorrow(testOnBorrow);  
        dataSource.setTestOnReturn(testOnReturn);  
        dataSource.setPoolPreparedStatements(poolPreparedStatements);  
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {  
            dataSource.setFilters(filters);  
        } catch (SQLException e) {  
            logger.error("druid configuration initialization filter", e);  
        }  
        dataSource.setConnectionProperties(connectionProperties);  
     
        return dataSource;  
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
     
        // IP白名单
        if(StringUtils.isNoneBlank(durid_allow))servletRegistrationBean.addInitParameter("allow", durid_allow);
        // IP黑名单(共同存在时，deny优先于allow)
        if(StringUtils.isNoneBlank(durid_deny))servletRegistrationBean.addInitParameter("deny", durid_deny);
        //控制台管理用户
        if(StringUtils.isNoneBlank(durid_username))servletRegistrationBean.addInitParameter("loginUsername", durid_username);
        if(StringUtils.isNoneBlank(durid_password))servletRegistrationBean.addInitParameter("loginPassword", durid_password);
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        if(StringUtils.isNoneBlank(durid_resetEnable))servletRegistrationBean.addInitParameter("resetEnable", durid_resetEnable);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getDurid_allow() {
		return durid_allow;
	}

	public void setDurid_allow(String durid_allow) {
		this.durid_allow = durid_allow;
	}

	public String getDurid_deny() {
		return durid_deny;
	}

	public void setDurid_deny(String durid_deny) {
		this.durid_deny = durid_deny;
	}

	public String getDurid_username() {
		return durid_username;
	}

	public void setDurid_username(String durid_username) {
		this.durid_username = durid_username;
	}

	public String getDurid_password() {
		return durid_password;
	}

	public void setDurid_password(String durid_password) {
		this.durid_password = durid_password;
	}

	public String getDurid_resetEnable() {
		return durid_resetEnable;
	}

	public void setDurid_resetEnable(String durid_resetEnable) {
		this.durid_resetEnable = durid_resetEnable;
	} 
    
    
}
