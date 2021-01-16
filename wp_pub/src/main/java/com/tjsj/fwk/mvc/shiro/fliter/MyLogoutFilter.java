package com.tjsj.fwk.mvc.shiro.fliter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tjsj.base.entity.ResultJson;

//@Component
public class MyLogoutFilter extends LogoutFilter {

	 private static final Logger log = LoggerFactory.getLogger(MyLogoutFilter.class);
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Subject subject = getSubject(request, response);
		HttpServletRequest    req =  (HttpServletRequest) request;
  		HttpServletResponse   resp =  (HttpServletResponse) response;
		String devId = req.getHeader("DEV_ID");
		String token = req.getHeader("TOKEN");
		
        //try/catch added for SHIRO-298:
        try {
        	 subject.logout();

    		if(StringUtils.isNotBlank(devId)&&StringUtils.isNotBlank(token)){
    			ResultJson rj = new ResultJson();
    			rj.setResult(true);
    			rj.setResultMsg("退出成功");
    			resp.setContentType("application/json");
				resp.setHeader("Cache-Control", "no-cache");
				resp.setCharacterEncoding("UTF-8");
    			PrintWriter out = response.getWriter();
				out.print(JSONObject.toJSONString(rj));
				out.flush();
				return false;
    		}else{
    			return super.preHandle(request, response);
    		}
    		
           
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
		return true;
	}

}
