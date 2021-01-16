package com.tjsj.wp.mvc.config;

import java.util.List;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.tjsj.fwk.mvc.interceptors.RequestInterceptor;
import com.tjsj.fwk.mvc.interceptors.ObjectInterceptor;
import com.tjsj.fwk.mvc.interceptors.Outputterceptor;
import com.tjsj.fwk.mvc.shiro.annotation.SessionUserArgumentResolver;




@Configuration
public class WebMvcConfigurer  extends WebMvcConfigurerAdapter  {

	
	 @Override
	  public void configurePathMatch(PathMatchConfigurer configurer) {
	
	    configurer.setUseSuffixPatternMatch(true)
	          	  .setUseTrailingSlashMatch(false);
	  }

	
	 
	 @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
		 registry.addInterceptor(new ObjectInterceptor())
		 		 	.addPathPatterns("/**")
		 		 	.excludePathPatterns("/error/**");
		 registry.addInterceptor(new Outputterceptor())
 		 		 	.addPathPatterns("/**")
 		 		 	.excludePathPatterns("/error/**");
		 registry.addInterceptor(new RequestInterceptor())
	 		 		.addPathPatterns("/**")
	 		 		.excludePathPatterns("/error/**");
        super.addInterceptors(registry);
    }

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		
		registry.addViewController("/").setViewName("index.htm");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	
		super.addViewControllers(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub

		registry.addResourceHandler("/webapp/**").addResourceLocations("classpath:/webapp/");
		
		registry.addResourceHandler("/**").addResourceLocations("classpath:/webapp/");

		super.addResourceHandlers(registry);
	}
	 
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SessionUserArgumentResolver());
    }
	
	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				// TODO Auto-generated method stub
				  container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.htm"));
	              container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/404.htm"));
	              container.addErrorPages(new ErrorPage(java.lang.Throwable.class,"/error/404.htm"));
			}
	    };
	}

}
