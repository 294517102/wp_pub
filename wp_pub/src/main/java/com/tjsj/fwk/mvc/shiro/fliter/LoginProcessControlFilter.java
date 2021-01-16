package com.tjsj.fwk.mvc.shiro.fliter;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.codec.MD5;
import com.tjsj.m_util.comm.CommUtil;
import com.tjsj.wp.mvc.controller.portal.LoginMaintenanceController;
import com.tjsj.wp.orm.entity.SmUserTbl;

//@Component
public class LoginProcessControlFilter extends AccessControlFilter {

	private static Logger logger = LoggerFactory.getLogger(LoginProcessControlFilter.class);
	
	//@Autowired
	private DefSet defSet;
	
	private final String TOKEN_REGEX = "^[0-9]+-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// TODO Auto-generated method stub
		
		 Subject subject = getSubject(request, response);
		 String[] roles = (String[])mappedValue; 
		 if(roles!=null){
			for(String role : roles) {  
	            if(getSubject(request, response).hasRole(role)) {  
	                return true;  
	            }  
	        }  
		 }
		//用户未登录
  		HttpServletRequest    req =  (HttpServletRequest) request;
  		HttpServletResponse   resp =  (HttpServletResponse) response;
		String devId = req.getHeader("DEV_ID");
		String token = req.getHeader("TOKEN");
		//只针对手机app用户生效
		if(StringUtils.isBlank(devId)||StringUtils.isBlank(token)){
			 return true;  
		} 
		//用户未认证
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
					if(token.equalsIgnoreCase("login")){
						return true;
					}
					//token未default或格式错误情况下，直接返回，降低数据库检索压力
					if(token.equalsIgnoreCase("default")||!token.matches(TOKEN_REGEX)){
						
						//返回认证失败，跟底部一致
						ResultJson rj = new ResultJson();
						rj.setResult(false);
						rj.setResultMsg("认证失败");
						rj.setStatus("AUTH-FAILURE");
						
						resp.setContentType("application/json");
						resp.setHeader("Cache-Control", "no-cache");
						resp.setCharacterEncoding("UTF-8");
						resp.setHeader("AUTH", "false");
						PrintWriter out = response.getWriter();
						out.print(JSONObject.toJSONString(rj));
						out.flush();
						return false;
					}
					logger.info("一键登录");
					//调用一键登录
					String domain = (String) req.getSession().getAttribute(Const.SESSION_DOMAIN);
					if(domain==null){
						defSet = (DefSet) Const.CONTEXT.getBean("defSet");
					  if(StringUtils.isNotBlank(defSet.getDomain())){
						  domain = defSet.getDomain();
				        }else{
				        	 domain = request.getServerName() +(request.getServerPort()==80?"":":"+request.getServerPort())+ Const.BASE;
						     domain = domain.replace("/", "_");
							if(domain.indexOf(":") > 0){
								domain = domain.replace(":", "_");
							}
				        }
					    
					System.out.println(" domain - "+domain);
					SmUserTbl user = LoginMaintenanceController.auto_login_2(req, resp,devId, token,domain);
					
					if(user!=null){
						resp.setHeader("TOKEN", user.getToken());
					}else{
	
						ResultJson rj = new ResultJson();
						rj.setResult(false);
						rj.setResultMsg("认证失败");
						rj.setStatus("AUTH-FAILURE");
						
						resp.setContentType("application/json");
						resp.setHeader("Cache-Control", "no-cache");
						resp.setCharacterEncoding("UTF-8");
						resp.setHeader("AUTH", "false");
						PrintWriter out = response.getWriter();
						out.print(JSONObject.toJSONString(rj));
						out.flush();
						return false;
					}
				}
		//用户已认证
		  }else{
			  //若已登录，校验sign
			  Map pMap = CommUtil.getParameterMap((HttpServletRequest)request); 
			  String sign = null;
			  if(pMap.size() > 0){
				  Iterator<Map.Entry<String, Object>> it = pMap.entrySet().iterator();
				  Map enMap = Maps.newTreeMap();  
				  while (it.hasNext()) {
					 Map.Entry<String, Object> entry = it.next();
					 if(entry.getKey().equalsIgnoreCase("sign")){
						 sign  = (String) entry.getValue();
						 continue;
					 }
					 enMap.put(entry.getKey(), entry.getValue());
				  }
				  
				  //增加key
				  Session session = SecurityUtils.getSubject().getSession();
				  SmUserTbl user = null;
				  if(session.getAttribute(Const.SESSION_USER)==null){
						String userName = (String) SecurityUtils.getSubject().getPrincipal();
						user = SmUserTbl.find.where().eq("username", userName).findUnique();							
					}else{
						user = (SmUserTbl) session.getAttribute(Const.SESSION_USER);
					}
				  
				  if(StringUtils.isBlank(user.getToken())||StringUtils.isBlank(sign)){
					   ResultJson rj = new ResultJson();
						rj.setResult(false);
						rj.setResultMsg("校验失败");
						rj.setStatus("VERIFY-FAILURE");
						rj.setInfo("sign为空或密钥无效");
						
						resp.setContentType("application/json");
						resp.setHeader("Cache-Control", "no-cache");
						resp.setCharacterEncoding("UTF-8");
						resp.setHeader("AUTH", "false");
						PrintWriter out = response.getWriter();
						out.print(JSONObject.toJSONString(rj));
						out.flush();
						return false;
				  }
				  
				  enMap.put("key_",StringUtils.substring(user.getToken(), user.getToken().length()-8));
				  
				  it = enMap.entrySet().iterator();
				  String sText = new String();
				  while (it.hasNext()) {
						 Map.Entry<String, Object> entry = it.next();
						 sText += "&"+entry.getKey()+"="+entry.getValue();
					  }
				  sText = sText.replaceFirst("&", "");
				  String lSign = MD5.md5(sText);
				  if(!sign.equals(lSign)){
					  ResultJson rj = new ResultJson();
						rj.setResult(false);
						rj.setResultMsg("校验失败");
						rj.setStatus("VERIFY-FAILURE");
						rj.setInfo("sign错误");
						
						resp.setContentType("application/json");
						resp.setHeader("Cache-Control", "no-cache");
						resp.setCharacterEncoding("UTF-8");
						resp.setHeader("AUTH", "false");
						PrintWriter out = response.getWriter();
						out.print(JSONObject.toJSONString(rj));
						out.flush();
						return false;
				  }
			  }else{
				  ResultJson rj = new ResultJson();
					rj.setResult(false);
					rj.setResultMsg("校验失败");
					rj.setStatus("VERIFY-FAILURE");
					rj.setInfo("sign不能为空");
					
					resp.setContentType("application/json");
					resp.setHeader("Cache-Control", "no-cache");
					resp.setCharacterEncoding("UTF-8");
					resp.setHeader("AUTH", "false");
					PrintWriter out = response.getWriter();
					out.print(JSONObject.toJSONString(rj));
					out.flush();
					return false;
			  }
		  }
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		return false;
	}

}
