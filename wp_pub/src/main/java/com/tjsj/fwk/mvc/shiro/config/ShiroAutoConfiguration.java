package com.tjsj.fwk.mvc.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.tjsj.fwk.mvc.shiro.ShiroRealm;
import com.tjsj.fwk.mvc.shiro.annotation.EnableShiroWebSupport;
import com.tjsj.fwk.mvc.shiro.fliter.LoginProcessControlFilter;
import com.tjsj.fwk.mvc.shiro.fliter.MyLogoutFilter;


/**
 * shiro 配置类
 * 
 * @author gongdzh
 *
 */
@Configuration
@EnableShiroWebSupport
@Import(ShiroConfiguration.class)
@EnableConfigurationProperties({ ShiroConfigProperties.class })
public class ShiroAutoConfiguration {

	@Autowired(required = false)
	private ShiroConfigProperties shiroConfigProperties;

	/**
	 * HashedCredentialsMatcher，这个类是为了对密码进行编码的， 防止密码在数据库里明码保存，当然在登陆认证的时候，
	 * 这个类也负责对form里输入的密码进行编码。
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	/**
	 * ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，
	 * 负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
	 */
	@Bean(name = "shiroRealm")
	@DependsOn("lifecycleBeanPostProcessor")
	public ShiroRealm shiroRealm() {
		ShiroRealm realm = new ShiroRealm();
		// realm.setCredentialsMatcher(hashedCredentialsMatcher());
		return realm;
	}

	/**
	 * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
	 * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
	 */
	@Bean(name = "ehCacheManager")
	@DependsOn("lifecycleBeanPostProcessor")
	public EhCacheManager ehCacheManager() {
		return new EhCacheManager();
	}

	@Bean(name="sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //默认设置60分钟
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        return sessionManager;
    }
	
	/**
	 * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
	 */
	@Bean(name = "securityManager")
	@DependsOn(value = { "shiroRealm", "ehCacheManager","sessionManager" })
	public DefaultWebSecurityManager securityManager(	AuthorizingRealm shiroRealm, 
														EhCacheManager ehCacheManager,
														SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm);
		securityManager.setCacheManager(ehCacheManager);
		//securityManager.setSessionManager(sessionManager);
	
		return securityManager;
	}

	/**
	 * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
	 * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
	 */
	@Bean(name = "shiroFilter")
	@DependsOn(value = { "securityManager" })
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

		// 用户退出过滤器
		MyLogoutFilter logoutFilter = new MyLogoutFilter();
		logoutFilter.setRedirectUrl(shiroConfigProperties.getLoginUrl());

		LoginProcessControlFilter loginProcessFilter = new LoginProcessControlFilter();
		
		filters.put("logout", logoutFilter);
		filters.put("loginProcess", loginProcessFilter);

		shiroFilterFactoryBean.setFilters(filters);

		Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
		//非授权访问
		filterChainDefinitionManager.put("/admin/obtain_login.htm", "anon");
		filterChainDefinitionManager.put("/admin/login.htm", "anon");
		//授权登录
		filterChainDefinitionManager.put("/admin/logout.htm", "logout");
		filterChainDefinitionManager.put("/admin/**", "authc");
		filterChainDefinitionManager.put("/admin", "authc");
		
		filterChainDefinitionManager.put("/user/**", "loginProcess");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
		shiroFilterFactoryBean.setLoginUrl(shiroConfigProperties.getLoginUrl());
		shiroFilterFactoryBean.setSuccessUrl(shiroConfigProperties.getSuccessUrl());
		shiroFilterFactoryBean.setUnauthorizedUrl(shiroConfigProperties.getUnauthorizedUrl());

		return shiroFilterFactoryBean;
	}

	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}

	/**
	 * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
	 * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
	 */
	@Bean
	@DependsOn(value = { "securityManager" })
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
		aASA.setSecurityManager(securityManager);
		return aASA;
	}
	
}
