package com.tjsj.wp.mvc.controller.comm;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.m_util.comm.CommUtil;
import com.tjsj.m_util.spring.SpringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.SmUserTbl;



/**
 * @author gongdzh
 *
 */
@Controller
public class NormalOperationController extends WpBaseController {

	
	@Autowired
	private DefSet defSet;
	@Autowired
	private EbeanServer ebeanServer;
	
	public NormalOperationController() {
		// TODO Auto-generated constructor stub
	}

    
	/**
	 * 
	 * @param request
	 * @param response
	 * @param url
	 * @param isFlush
	 * @return
	 * @author gongdzh 
	 * 2016年9月7日 上午10:20:53
	 */
	@RequestMapping("/admin/to_target")
	public ModelAndView to_bg_target(HttpServletRequest request,
										HttpServletResponse response,
										String url,Integer isFlush,
										String key){
		ModelAndView mv=createBgView(url, request, response);
		if(isFlush!=null&&isFlush==1){
			SmUserTbl user =SmUserTbl.find.byId(this.getLoginUser().getId());
			mv.addObject("user", user);
		}
		if(StringUtils.isNotBlank(key)){
			mv.addObject(key, SpringUtil.getBean(key));
		}
		
		return mv;
	}
	
	@RequestMapping("/to_target")
	public ModelAndView to_target(HttpServletRequest request,HttpServletResponse response,String url,Integer isFlush){
		ModelAndView mv=createView(url, request, response);
		
		return mv;
	}
	
	
	/*
	 * 针对内容管理模块使用
	 */
	@RequestMapping("/go")
	public  ModelAndView go(HttpServletRequest request,
							HttpServletResponse response){
		Map pMap = CommUtil.getAllParameterMap(request);
		String url= request.getParameter("u");
		/*System.out.println(request.getParameter("k"));
		String key= request.getParameter("k");
		String t = key.substring(0, 1);*/
		
		if(StringUtils.isBlank(url)){
			url = request.getParameter("url");
		}
		
		ModelAndView mv=createView(url, request, response);
		
		for (Object e: pMap.entrySet()) {
			Entry<String, String> entry = (Entry<String, String>) e;			
			if(StringUtils.isNotBlank(entry.getValue())){
				mv.addObject(entry.getKey(), entry.getValue());
			}
		}
		
		return mv;
	}
	
	@RequestMapping("/switch_language")
	public ModelAndView switch_language(HttpServletRequest request,HttpServletResponse response,String url,String lang){
		if(StringUtils.isNotBlank(lang)){
			request.getSession().setAttribute(Const.SYSTEM_LANGUAGE, lang);
		}
		return to_target(request, response,url,null);
	}
	 
}
