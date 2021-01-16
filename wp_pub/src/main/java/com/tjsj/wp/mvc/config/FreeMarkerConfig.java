package com.tjsj.wp.mvc.config;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.beans.factory.config.PropertiesFactoryBean;

@Configuration
public class FreeMarkerConfig {
	@Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    protected org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver viewResolver;

    
    @PostConstruct
    public void setFreeMarkerConfig(){
    	FreemarkerStaticModels freemarkerStaticModels = FreemarkerStaticModels.getInstance();
 
    	freemarkerStaticModels.setStaticModels(getFreemarkerStaticModelsProps());
    	viewResolver.setAttributesMap((Map)freemarkerStaticModels);
    	//可以在这里设置其他属性
    	
    }
    

    public Properties getFreemarkerStaticModelsProps(){

    	Resource  file =new ClassPathResource("config/freemarkerstatic.properties"); 
    	Properties props =null;
		try {
			props = PropertiesLoaderUtils.loadProperties(file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return props;
    }
}
