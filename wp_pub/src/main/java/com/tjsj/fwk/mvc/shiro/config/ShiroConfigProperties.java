package com.tjsj.fwk.mvc.shiro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for Shiro.
 *
 * @author gongdzh
 */
@Component      
@ConfigurationProperties(prefix="shiro.url")
public class ShiroConfigProperties {
  
	private String loginUrl ;
	
	private String successUrl;
	
	private String unauthorizedUrl;
	
	private String logoutUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	@Override
	public String toString() {
		return "ShiroProperties [loginUrl=" + loginUrl + ", successUrl=" + successUrl + ", unauthorizedUrl="
				+ unauthorizedUrl + ", logoutUrl=" + logoutUrl + "]";
	}
    
	
	
}
