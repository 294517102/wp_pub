package com.tjsj.wp.mvc.config;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tjsj.wp.App;

@Configuration
public class TomcatConfig {


	@Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        
        //設置doc根目録
        String filePath = TomcatConfig.class.getResource("/").getFile();
        tomcat.setDocumentRoot(new File(filePath));
        tomcat.setBaseDirectory(new File(filePath+"tomcat/"));
        //默认设置60分钟
        //tomcat.setSessionTimeout(3600);
        
        return tomcat;
    }
}
