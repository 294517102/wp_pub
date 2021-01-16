

package com.tjsj.fwk.mvc.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.m_util.json.FastjsonPropertyPreFilter;
import com.tjsj.m_util.spring.SpringUtil;
 

/**
 * 所有action的拦截器，主要是设置base与basepath
 */  
public class Outputterceptor extends  HandlerInterceptorAdapter {
    
    protected Logger logger = Logger.getLogger(this.getClass());
    
    public static boolean IS_WINDOWS  = false;
    

    private DefSet defSet = (DefSet) SpringUtil.getBean("defSet");
    
    
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
		HttpSession session =	request.getSession(true);	
		
		String terminal = request.getParameter("terminal");
		
		if(StringUtils.isNotBlank(terminal)){
			session.setAttribute("terminal", terminal);
		}
       
		
        return true; 
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session =	request.getSession(true);	
		if(modelAndView!=null){
			Map<String,Object> model = modelAndView.getModel();
			//这里对变量处理
			//处理分页
			String ap = request.getParameter("ajaxPage");
			if(StringUtils.isNotBlank(ap)){
				logger.info("ajaxPage : "+ap);
				if(model.get("pageHtml")!=null){
					String pageHtml =(String) model.get("pageHtml");
					pageHtml =  pageHtml.replace("<A	href='", "<A	href='javascript:"+ap+"(\"");
					pageHtml =  pageHtml.replace("'>", "\")'>");
				
					model.put("pageHtml", pageHtml);
					logger.info("pageHtml : "+model.get("pageHtml"));
				}
			}
			
			if(StringUtils.isNotBlank(request.getParameter("mode"))
						&&request.getParameter("mode").equals("ajax")){
				FastjsonPropertyPreFilter  jf =  new FastjsonPropertyPreFilter();
				String jsonFormat = (String) model.get("_jf");
				if(StringUtils.isNotBlank(jsonFormat)){
					String[] jsonFormats = jsonFormat.split("\\|");
					for (int i =0;i<jsonFormats.length;i++) {
						String s = jsonFormats[i];
						jf.setExpandProperty(s);
					}
				}
				Map<String,Object> m = Maps.newHashMap();
				String data = new String("{");
				for (Map.Entry<String,Object> entry : model.entrySet()) { 
					//ajax模式下，清除环境变量
					if(     entry.getKey().equalsIgnoreCase("_jf")||
							entry.getKey().equalsIgnoreCase("rp")||
							entry.getKey().equalsIgnoreCase("bp")||
							entry.getKey().equalsIgnoreCase("pageParameter")){
						continue;
					}
					
					if(entry.getValue()!=null
							&& !(entry.getValue() instanceof org.springframework.validation.BeanPropertyBindingResult)
							&& !entry.getKey().equalsIgnoreCase("config")){	
						data+=",\""+entry.getKey()+"\":"+JSONObject.toJSONString(entry.getValue(),jf,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
					}
				}
				data = data.replaceFirst(",", "");
				
				data += "}";
				model.put("data", data); 
				
				modelAndView.setViewName("ajax_out");
				
			}
		
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}