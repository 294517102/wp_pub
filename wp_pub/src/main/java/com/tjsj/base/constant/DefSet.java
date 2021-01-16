package com.tjsj.base.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.wp.orm.entity.*;


@Component
@Scope("singleton")
@ConfigurationProperties
public  class  DefSet
{

	
	private  String domain;
	
	private  String editDir; 
	
	private String editUser;
	
	//实体类
	private  Map<String,Object> classType;
	//字典值
	private List<CmCodeTbl>  codes;
	//黑名单
	private Map<String,ArrayList<String>> requestIntercepts_1;
	//白名单
	private Map<String,ArrayList<String>> requestIntercepts_2;
	//动态加载
	private Map<String,Map<Class<?>,String>> dynamicMethod = Maps.newHashMap();
	
	
	public DefSet() {
		
		init();
	}

	public void init(){
		//初始化实体			
		 try {
			 classType = Maps.newConcurrentMap();
			 
			 List<Class<?>> entitis = ClassUtil.getClasses("com.tjsj.wp.orm.entity");
			 for (Class<?> classz :entitis) { 
				 if(!classz.getName().endsWith("$1")){
					 String key = StringUtils.substringAfterLast(classz.getName(),".");
					 classType.put(key, classz);
				 }		             
		       }  
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Map<String, Object> getClassType() {
		return classType;
	}

	public void setClassType(Map<String, Object> classType) {
		this.classType = classType;
	}

	public List<CmCodeTbl> getCodes() {
		return codes;
	}

	public void setCodes(List<CmCodeTbl> codes) {
		this.codes = codes;
	}
	 
	public String getCodeDesc(String tablename,String colname,Integer value){
		for (CmCodeTbl code : codes) {
			if(code.getTablename().equalsIgnoreCase(tablename)&&
					code.getColname().equalsIgnoreCase(colname)&&
					code.getValue()==value){
				return code.getDescname();
			}
		}
		return null;
	}

	public Map<String, ArrayList<String>> getRequestIntercepts_1() {
		return requestIntercepts_1;
	}

	public void setRequestIntercepts_1(Map<String, ArrayList<String>> requestIntercepts_1) {
		this.requestIntercepts_1 = requestIntercepts_1;
	}

	public Map<String, ArrayList<String>> getRequestIntercepts_2() {
		return requestIntercepts_2;
	}

	public void setRequestIntercepts_2(Map<String, ArrayList<String>> requestIntercepts_2) {
		this.requestIntercepts_2 = requestIntercepts_2;
	}

	public Map<Class<?>, String> getDynamicMethod(String iid) {
		if(StringUtils.isBlank(iid)){
			return null;
		}
		return dynamicMethod.get(iid);
	}

	public void regesterDynamicMethod(String iid,Class<?> classz,String method) {
		if(StringUtils.isBlank(iid)||
				classz==null||
				StringUtils.isBlank(method)){
			return;
		}
		Map<Class<?>,String> m = Maps.newHashMap();
		m.put(classz, method);
		dynamicMethod.put(iid, m);
	}

	public String getEditDir() {
		return editDir;
	}

	public void setEditDir(String editDir) {
		this.editDir = editDir;
	}

	public String getEditUser() {
		return editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}

	
}
