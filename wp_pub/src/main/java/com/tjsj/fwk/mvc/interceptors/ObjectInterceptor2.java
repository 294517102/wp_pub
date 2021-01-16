

package com.tjsj.fwk.mvc.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.m_util.http.DeviceUtils;
import com.tjsj.m_util.spring.SpringUtil;
import com.tjsj.wp.mvc.view.label.At;
import com.tjsj.wp.mvc.view.label.Cl;
import com.tjsj.wp.mvc.view.label.Cm;
import com.tjsj.wp.mvc.view.label.Fl;
import com.tjsj.wp.mvc.view.label.Sc;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;


/**
 * 所有action的拦截器，主要是设置base与basepath
 */  
public class ObjectInterceptor2 extends  HandlerInterceptorAdapter {
    
    protected Logger logger = Logger.getLogger(this.getClass());

    public static boolean IS_WINDOWS  = false;
    
    
    public DefSet  defSet = (DefSet) SpringUtil.getBean("defSet");
    
    
    static {
    	if (System.getProperty("os.name").toLowerCase().indexOf("windows" )>0) {
    		IS_WINDOWS = true;
    	}
    }

	/**
	 * 所有action的拦截,主要拦截base与basepath
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse 对象
	 * @param handler 处理器
	 * @throws Exception 异常处理
	 * @return 处理后返回true
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
        logger.info("domain : "+instanceDomain); 
        
       try{ 
  
	        HttpSession session  = request.getSession(true);
	       
	        //设置language
	        String language = request.getParameter("language");
	        Object oLanguage =  session.getAttribute(Const.SYSTEM_LANGUAGE);
	
			if(StringUtils.isNotBlank(language)){
				session.setAttribute(Const.SYSTEM_LANGUAGE, language);
			}else{
				if(session.getAttribute(Const.SYSTEM_LANGUAGE)==null){
					language = "zh_CN";
					session.setAttribute(Const.SYSTEM_LANGUAGE, "zh_CN");
				}
				
			}
		
	    	//站点 管理
	        Object languages = session.getAttribute(Const.SYSTEM_LANGUAGE);
	    
	        Object domain = session.getAttribute(Const.SESSION_DOMAIN);
	        
	        //终端信息初始化
			String terminal = request.getParameter("terminal");
			
			if(domain==null){
				session.setAttribute(Const.SESSION_DOMAIN, instanceDomain);			
				SmWebSetTbl webSet=SmWebSetTbl.find.where().eq("domain", instanceDomain).eq("language", languages).findUnique();
				session.setAttribute(Const.SESSION_WEB_SET,webSet);
				terminal=changeTerminal(webSet,request);
			}else{
				//说明用户做了域名切换，销毁session,阻止不同域名间信息共享
				if(!domain.equals(instanceDomain)){
					session.invalidate();
					session  = request.getSession(true);
					//重新加载
					session.setAttribute(Const.SESSION_DOMAIN, instanceDomain);	
					SmWebSetTbl webSet=SmWebSetTbl.find.where().eq("domain", instanceDomain).eq("language", languages).findUnique();
					session.setAttribute(Const.SESSION_WEB_SET,webSet);
					terminal=changeTerminal(webSet,request);
				}else{
					//如果切换语言版本，重新setSession数据
					if(StringUtils.isNotBlank(language)&&oLanguage!=null&&
							!language.equals(oLanguage)){
						SmWebSetTbl webSet=SmWebSetTbl.find.where().eq("domain", instanceDomain).eq("language", languages).findUnique();
						session.setAttribute(Const.SESSION_WEB_SET,webSet);
						terminal=changeTerminal(webSet,request);
					}				
				}
			}
			
	      
			if(StringUtils.isNotBlank(terminal)){
				request.getSession(true).setAttribute("terminal", terminal);
			}
			System.err.println("拦截器："+request.getSession().getAttribute("terminal"));
       }catch(Exception e){
    	   logger.error("Exception throws:",e);
       }
        return true; 
	}
	/**
	 * 判断网站的类型；如果是单独pc或者响应式并且终端是非pc；就强制将终端
	 * @param webSet
	 * @param request
	 * @return
	 */
	private String changeTerminal(SmWebSetTbl webSet,HttpServletRequest request){
		String terminal = request.getParameter("terminal");
		if(StringUtils.isBlank(terminal) && webSet.getType()!=null&& webSet.getType()==1&&DeviceUtils.getDeviceType(request)!=1){
			System.out.println("强制设置pc ");
			terminal="pc";
		}
		return terminal;
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session =	request.getSession(false);	

		if(modelAndView!=null
				&&StringUtils.isNoneBlank(modelAndView.getViewName())
				&&!StringUtils.contains(modelAndView.getViewName(), "error/")){
			Map<String,Object> model = modelAndView.getModel();
			//用户信息处理
			if(model.get("user")!=null){
				session.setAttribute(Const.SESSION_USER,model.get("user"));
			}else{
				if(session.getAttribute(Const.SESSION_USER)!=null){
					model.put("user", session.getAttribute(Const.SESSION_USER));
				}
			}
			//站点信息处理	
			if(model.get(Const.WEB_SET)!=null){				
				session.setAttribute(Const.SESSION_WEB_SET,model.get(Const.WEB_SET));	
			}else{
				if(session.getAttribute(Const.SESSION_WEB_SET)!=null){
					model.put(Const.WEB_SET, session.getAttribute(Const.SESSION_WEB_SET));
				}
			}
			
			SmWebSetTbl webset=(SmWebSetTbl) session.getAttribute(Const.SESSION_WEB_SET);
//			网站关闭拦截
			if(StringUtils.contains( request.getRequestURL(), "/error/close.htm")&&webset!=null&&webset.getState()==1){
//				进入首页
				 response.sendRedirect(request.getContextPath()+"/index.htm");	
			}else if(webset!=null&&webset.getState()!=1&&!StringUtils.contains( request.getRequestURL(), "/admin")&&!StringUtils.contains( request.getRequestURL(), "/general_service")&&!StringUtils.contains( request.getRequestURL(), "/error/close.htm")){
				response.sendRedirect(request.getContextPath()+"/error/close.htm");	
			}
			
			//增加标签
			model.put("ds", defSet);
			model.put("at", new At(webset));
			model.put("cl", new Cl(webset));
			model.put("fl", new Fl(webset));
			model.put("sc", new Sc(webset));
			model.put("req", request);
			model.put("cm", new Cm(Const.BASE,webset));
			
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}