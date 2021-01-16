package com.tjsj.wp.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaje.ebean.EbeanServer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tjsj.base.constant.DefSet;
import com.tjsj.m_util.spring.SpringUtil;
import com.tjsj.wp.orm.entity.CmCodeTbl;
import com.tjsj.wp.orm.entity.SmRequestInterceptTbl;

/*
 * 加载系统缓存数据
 */
public class DataLoadService {

	private static Logger logger = LoggerFactory.getLogger(DataLoadService.class);
	
	private static  EbeanServer ebeanServer;
	private static DefSet defSet;

	public static synchronized  void loadCodeData(){
		init();
		logger.info("load CmCodeTbl data from db.");
		List<CmCodeTbl>  codes = ebeanServer.find(CmCodeTbl.class).findList();
    	defSet.setCodes(codes);
    	logger.info(codes.size()+" CmCodeTbl records has loaded.");
	}
	
	public static synchronized  void loadRequestInterceptData(){
		init();
		logger.info("load SmRequestInterceptTbl data from db.");
		List<SmRequestInterceptTbl> RequestIntercept = ebeanServer.find(SmRequestInterceptTbl.class).where()
														.eq("state", 1).findList();
		Map<String,ArrayList<String>> requestIntercepts_1 = Maps.newHashMap(); 
		Map<String,ArrayList<String>> requestIntercepts_2 = Maps.newHashMap(); 
		for (SmRequestInterceptTbl ri : RequestIntercept) {
			if(ri.getType()==1){
				ArrayList<String> intercepts = requestIntercepts_1.get(ri.getWebSet().getDomain());
				if(intercepts==null){
					intercepts = Lists.newArrayList();
					requestIntercepts_1.put(ri.getWebSet().getDomain(), intercepts);
				}
				intercepts.add(ri.getUrl());
			}else if(ri.getType()==2){
				ArrayList<String> intercepts = requestIntercepts_2.get(ri.getWebSet().getDomain());
				if(intercepts==null){
					intercepts = Lists.newArrayList();
					requestIntercepts_2.put(ri.getWebSet().getDomain(), intercepts);
				}
				intercepts.add(ri.getUrl());
			}
		}

		defSet.setRequestIntercepts_1(requestIntercepts_1);
		defSet.setRequestIntercepts_2(requestIntercepts_2);
		logger.info(RequestIntercept.size()+" SmRequestInterceptTbl records has loaded.");
	}
	
	private static void init(){
		if(ebeanServer==null){
			ebeanServer = (EbeanServer) SpringUtil.getBean("ebeanFactoryBean");
		}
		if(defSet==null){
			defSet =  (DefSet) SpringUtil.getBean("defSet");
		}
	}
}
