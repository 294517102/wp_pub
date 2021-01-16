package com.tjsj.wp.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.common.BeanList;
import com.google.common.collect.Maps;
import com.tjsj.m_util.comm.CommUtil;
import com.tjsj.m_util.json.FastjsonPropertyPreFilter;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.orm.entity.CmCodeTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

import cn.hutool.core.util.StrUtil;

import com.tjsj.wp.orm.entity.IfWechatTbl;
import com.tjsj.wp.orm.entity.SmModelTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

import weChatUtil.weiXinUserUtil;

  
public class WpBaseController extends BaseController {

	private static Logger logger = Logger.getLogger(WpBaseController.class);
	
	@Autowired
	protected EbeanServer ebeanServer;
	
	public ModelAndView createBgView(String viewName,HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(viewName)){
			ModelAndView mv = new ModelAndView("ajax_out");
			return mv;
		}
		viewName = getWrapperView(viewName,"/bg/zh_CN",request);
		
		ModelAndView mv = new ModelAndView(viewName);
		//公共变量
		mv.addObject("base", Const.BASE);

		setObjectMv( mv, request);
		
		return mv;
	}
	
	public ModelAndView createView(String viewName,HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isBlank(viewName)){
			ModelAndView mv = new ModelAndView("ajax_out");
			return mv;
		}
		Object language = request.getSession().getAttribute(Const.SYSTEM_LANGUAGE);
		Object domain = request.getSession(true).getAttribute(Const.SESSION_DOMAIN);
		String	rPath = "templates/"+getWebSet(request).getDomain()+"/" + (language==null?"zh_CN":language.toString());
	
		String wViewName = getWrapperView(viewName,rPath,request);
		ModelAndView mv = new ModelAndView(wViewName);	
		String rp = StringUtils.removeEnd(wViewName, viewName);
		mv.addObject("rp",rp);
		mv.addObject("bp", getWebSet(request).getProtocol() + "://" + request.getServerName()
		+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + Const.BASE + "/" + rp);
		//公共变量
		setObjectMv( mv, request);
		return mv;
	}

	public void setObjectMv(ModelAndView mv,HttpServletRequest request){
		//这里增加公共变量的代码
	}
	
	public Session getSubjectSession(){
		Subject userSub = SecurityUtils.getSubject();
		return userSub.getSession();
	}
	
	
	public Object  getSessionValue(String key){
		Session session = getSubjectSession();
		return session.getAttribute(key);
	}
	
	public void setSessionValue(String key,Object value){
		Session session = getSubjectSession();
		session.setAttribute(key, value);
	}
	
	public SmUserTbl getLoginUser(){
		
		if(this.getSubjectSession().getAttribute(Const.SESSION_USER)==null){
			String userName = (String) SecurityUtils.getSubject().getPrincipal();
			SmUserTbl user = SmUserTbl.find.where().eq("username", userName).findUnique();	
			this.getSubjectSession().setAttribute(Const.SESSION_USER,user);
		}
		
		return (SmUserTbl) this.getSubjectSession().getAttribute(Const.SESSION_USER);
	}
	
	public boolean isLogin(){
		
		if( SecurityUtils.getSubject().getPrincipal() != null){
			return true;
			}
			return false;
		}
	
	public void pagePrint(HttpServletRequest request,ModelAndView mv,PagedList pageList){
		mv.addObject("data", pageList.getList().size()>0?pageList.getList().get(0):null);
		mv.addObject("resultList", pageList.getList());
		mv.addObject("pageIndex", pageList.getPageIndex()+1); 
		mv.addObject("pageSize", pageList.getPageSize());
		mv.addObject("pageCount", pageList.getTotalPageCount());
		mv.addObject("rowCount",pageList.getTotalCount());
		mv.addObject("pageHtml", CommUtil.printPagesIndexConditionByHtml(request, pageList.getPageIndex(), pageList.getTotalPageCount()));
	}
	
	public void pageAjaxPrint(HttpServletRequest request,ModelAndView mv,PagedList pageList,String url,String params){
		mv.addObject("data", pageList.getList().size()>0?pageList.getList().get(0):null);
		mv.addObject("resultList", pageList.getList());
		mv.addObject("pageIndex", pageList.getPageIndex()+1); 
		mv.addObject("pageSize", pageList.getPageSize());
		mv.addObject("pageCount", pageList.getTotalPageCount());
		mv.addObject("rowCount",pageList.getTotalCount());
		mv.addObject("pageHtml", CommUtil.showPageAjaxHtml(url, params, pageList.getPageIndex()+1, pageList.getTotalPageCount()));
	}
	
	public void pagePrint(HttpServletRequest request,ModelAndView mv,String rName,PagedList pageList){
		mv.addObject(rName, pageList.getList().size()>0?pageList.getList().get(0):null);
		mv.addObject("resultList_"+rName, pageList.getList());
		mv.addObject("pageIndex_"+rName, pageList.getPageIndex()+1); 
		mv.addObject("pageSize_"+rName, pageList.getPageSize());
		mv.addObject("pageCount_"+rName, pageList.getTotalPageCount());
		mv.addObject("rowCount_"+rName,pageList.getTotalCount());
		mv.addObject("pageHtml_"+rName, CommUtil.printPagesIndexConditionByHtml(request, pageList.getPageIndex(), pageList.getTotalPageCount()));
	}
	
	public void addResult(ModelAndView mv,boolean result,String msg){
		mv.addObject(Const.RESULT, result);
		if(StringUtils.isNotBlank(msg)){
			mv.addObject(Const.MSG, msg);
		}
	}  
	
	public Map<String,SmModelTbl> getParentModel(SmModelTbl model){
		
		if(model.getParent()==null){	
			Map<String,SmModelTbl> mMap= Maps.newHashMap();
			mMap.put(String.valueOf(model.getId()), model);
			return mMap;
		}
		Map<String,SmModelTbl> tMap = getParentModel(model.getParent());
		tMap.put(String.valueOf(model.getId()), model); 
		return tMap;
	}
	
	public SmWebSetTbl getWebSet(HttpServletRequest request){
		 Object  object = request.getSession().getAttribute(Const.SESSION_WEB_SET);
		if(object instanceof BeanList){ 
			@SuppressWarnings("rawtypes")
			BeanList bl = (BeanList) request.getSession().getAttribute(Const.SESSION_WEB_SET);
			if(bl.size() > 0){
				return (SmWebSetTbl) bl.get(0);
			}
		}
		return (SmWebSetTbl) request.getSession().getAttribute(Const.SESSION_WEB_SET);
	}
	/**
	 * 获取cmcode值 ，所有名字以数据库名为准
	 * @param tablename 表名
	 * @param colname 列名
	 * @param value 值
	 * @return
	 */
	public String getCodeName(String tablename,String colname,int value){
		String cname="";
		List<CmCodeTbl> code=CmCodeTbl.find.where().eq("tablename", tablename).eq("colname", colname).eq("value", value).findList();
		if(code.size()>0){
			cname= code.get(0).getDescname();
			return cname;
		}
		return cname;		
	}
	
	/**
	 * 获取微信令牌
	 * @param request
	 * @return
	 */
	public String getLogingSuccessToken(HttpServletRequest request){
		if(request.getSession().getAttribute(Const.SESSION_WC_SUCCESS_TOKEN)==null){
			if(!StringUtil.isBlank(this.getLoginUser().getWebSet())){
				SmWebSetTbl webset=this.getLoginUser().getWebSet();
				if(!StringUtil.isBlank(webset.getWeChat())){
					IfWechatTbl weChat=webset.getWeChat();
					if(!StringUtil.isBlank(weChat)){
						if(!StringUtil.isBlank(weChat.getAppid())&&!StringUtil.isBlank(weChat.getAppSecret())){
							String successToken=weiXinUserUtil.getAccess_token(weChat.getAppid(), weChat.getAppSecret());
							request.getSession().setAttribute(Const.SESSION_WC_SUCCESS_TOKEN, successToken);
						}
					}
				}
			}
		}
		return  (String) request.getSession().getAttribute(Const.SESSION_WC_SUCCESS_TOKEN);
	}
	protected void outJson(HttpServletResponse response, Object entity, String jsonFormat) {
		if (entity != null && (entity instanceof ResultJson)) {
			ResultJson rj = (ResultJson) entity;
		}
		FastjsonPropertyPreFilter jf = new FastjsonPropertyPreFilter();
		if (StrUtil.isNotBlank(jsonFormat)) {
			String[] jsonFormats = jsonFormat.split("\\|");
			for (int i = 0; i < jsonFormats.length; i++) {
				String s = jsonFormats[i];
				jf.addAvailableProperty(s);
			}
		}

		outJson(response, JSONObject.toJSONString(entity, jf, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat));
	}
	protected void outJson(HttpServletResponse response, String jsonDataStr) {
		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(jsonDataStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			this.logger.error("excepto", e);
		}
	}

	protected void outJson(HttpServletResponse response, Object object) {
		if (object != null && (object instanceof ResultJson)) {
			ResultJson rj = (ResultJson) object;
		}
		outJson(response, object, null);
	}

}
