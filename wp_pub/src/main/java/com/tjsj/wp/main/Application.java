package com.tjsj.wp.main;



import org.avaje.agentloader.AgentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


import com.tjsj.base.constant.DefSet;

import com.tjsj.m_util.spring.SpringUtil;
import com.tjsj.wp.tools.DataLoadService;


/**
 * 框架入口
 * @author gongdzh
 *
 */ 

@SpringBootApplication
@ComponentScan("com.tjsj")
@ServletComponentScan("com.tjsj.fwk.mvc")
@EnableConfigurationProperties
public class Application 
{
	protected static  DefSet  defSet ;

	private static  Logger  logger = LoggerFactory.getLogger(Application.class);
	
    public static void main( String[] args )
    {
    	Application.someApplicationBootupMethod();
    	    	
    	SpringApplication springApplication = new SpringApplication(Application.class);    	
    	
    	//这里可以增加监听       
    	ConfigurableApplicationContext cac = springApplication.run(args);
    	cac.registerShutdownHook();
   
    	//初始化
    	init();
    	regesterServices();
    }
    public static  void  someApplicationBootupMethod() {
    	  // Load the agent into the running JVM process
    	
    	  if (!AgentLoader.loadAgentFromClasspath("ebean-agent","debug=1;packages=com.tjsj.wp.orm.entity")) {
    		  logger.error("ebean-agent not found in classpath - not dynamically loaded");    		 
    	  }
    	  
    }
    
    
    public static void init(){
    	
    	DataLoadService.loadCodeData();
    	   
    	DataLoadService.loadRequestInterceptData();
    	

    }
    
    public static void regesterServices(){
    	if(defSet==null){
    		defSet = (DefSet) SpringUtil.getBean("defSet");
    	}
    	defSet.regesterDynamicMethod("XkdSxOJJLI0lwRjYs67", DataLoadService.class, "loadRequestInterceptData");
    }

}
