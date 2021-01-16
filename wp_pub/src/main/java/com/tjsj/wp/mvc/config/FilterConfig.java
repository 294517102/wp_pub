package com.tjsj.wp.mvc.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@Configuration
public class FilterConfig {

	@Bean
    public FilterRegistrationBean  urlRewriteFilter(){  
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  

        filterRegistrationBean.setFilter(new UrlRewriteFilter());  
        filterRegistrationBean.setName("urlRewriteFilter");
        filterRegistrationBean.setEnabled(true);  
        filterRegistrationBean.addUrlPatterns("*.htm");  
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addInitParameter("confPath", "config/urlrewrite.xml");

        return filterRegistrationBean;  
    }     
}
