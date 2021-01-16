package com.tjsj.fwk.mvc.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.m_util.http.NetworkUtil;
import com.tjsj.m_util.spring.SpringUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.orm.entity.SmRequestInterceptTbl;


/**
 * 拦截危险ip
 * @author 李波
 *
 */  

public class RequestInterceptor extends HandlerInterceptorAdapter  {
	
	public DefSet  defSet = (DefSet) SpringUtil.getBean("defSet");
	
	protected final Logger LOG = Logger.getLogger(RequestInterceptor.class);

		@Override
		public boolean preHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		//获取用户请求的IP

		if(request.getServletPath().indexOf("/error/")>=0){
			return true;
		}
		String requestIp =NetworkUtil.getRemoteAddress(request);

        //去掉http头，屏蔽端口号
        String instanceDomain = null;
        
        if(StringUtils.isNotBlank(defSet.getDomain())){
        	instanceDomain = defSet.getDomain();
        }else{
        	instanceDomain =  request.getServerName() +(request.getServerPort()==80?"":":"+request.getServerPort())+ Const.BASE;
	        instanceDomain = instanceDomain.replace("/", "_");
	        if(instanceDomain.indexOf(":") > 0){
	        	instanceDomain = instanceDomain.replace(":", "_");
	        }
        }

        //处理白名单
        Map<String,ArrayList<String>>   requestIntercepts_2 = defSet.getRequestIntercepts_2();
        ArrayList<String> ips = requestIntercepts_2.get(instanceDomain);
        if(ips!=null&&ips.size()>0){
        	boolean is_exist = false;
        	for (String ip : ips) {
        		if(requestIp.equalsIgnoreCase(ip.trim())){
        			is_exist = true;
        			break;
        		}
			}
        	if(!is_exist){
        		request.getRequestDispatcher("/error/refused.htm?ip="+requestIp).forward(
                        request, response);
        		return false;
        	}
        }
        
        //处理黑名单
        Map<String,ArrayList<String>>   requestIntercepts_1 = defSet.getRequestIntercepts_1();

        								ips = requestIntercepts_1.get(instanceDomain);
        							
        if(ips!=null&&ips.size()>0){
        	for (String ip : ips) {

        		if(requestIp.equalsIgnoreCase(ip.trim())){
        			request.getRequestDispatcher("/error/refused.htm?ip="+requestIp).forward(
                            request, response);
            		return false;
        		}
			}
        }
		//如果访问的ip和数据库不匹配正常运行
        return super.preHandle(request, response, handler); 
     }
	
 
}