

package com.tjsj.fwk.mvc.listener;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.wp.orm.entity.CmCodeTbl;


/**
 * 启动监听
 */
 
@WebListener
public class StartUpListener implements ServletContextListener{

	/*
	 * log4j日志记录 
	 */
	protected final Logger logger = LoggerFactory.getLogger(StartUpListener.class);   

	/**
	 *  
	 *  监听项目启动，进行初始化
	 *  @param sce ServletContextEvent对象
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("StartUpListener init ... ");
	
		Const.PROJECT_PATH = sce.getServletContext().getRealPath(File.separator);
		Const.CONTEXT =  WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		
		Const.BASE = sce.getServletContext().getContextPath();
		
		Const.PROJECT_PATH = sce.getServletContext().getRealPath("/");
		

	}

	/**
	 * 监听项目终止，进行销毁
	 * @param sce  ServletContextEvent对象
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.debug("=====");
	}

}