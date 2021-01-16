package com.tjsj.wp.mvc.controller.cms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmArticleTbl;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.CmCommentTbl;
import com.tjsj.wp.orm.entity.CmHomeSlideTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmRoleTbl;

@Controller
public class UseCmsMaintenanceController extends WpBaseController {
	

	private static Logger logger = LoggerFactory.getLogger(UseCmsMaintenanceController.class);
	/**
	 * 添加站点信息
	 * @author 李波 
	 * @param requst
	 * @param response
	 * @return true or false
	 */
	@RequestMapping("/admin/system/update_webset")
	public ModelAndView ApplySetAdds(HttpServletRequest request,SmWebSetTbl webset, HttpServletResponse response){
		//声明rj接收持久层返回数据
		HttpSession session  = request.getSession();
		ModelAndView mv=createView("", request, response);
		logger.info(webset.toString());
		try {
			if(webset.getId()==0){
				addResult(mv, false, "参数错误");
				return mv;
			}
			//根据Id查询原有的数据
			SmWebSetTbl dWebSet=SmWebSetTbl.find.byId(webset.getId());
			ClassUtil.copyObjectByPropertyValues(dWebSet, webset);
			//更新数据
			dWebSet.update();
			session.setAttribute(Const.SESSION_WEB_SET, webset);
		} catch (Exception e) {
			addResult(mv, false, "保存失败");
			return mv;
		}
		addResult(mv, true, "保存成功");
		return mv;
	}
	
	/**
	 * @author 张栩强
	 * @param request
	 * @param homeslide
	 * @param response
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/admin/system/add_slide_set")
	@ResponseBody
	public ModelAndView SlideSetAdds(HttpServletRequest request,HttpServletResponse response,String homeSlide,Integer pauseTime,Integer remarkState,String picIds) {
		ModelAndView mv=createView("", request, response);
		System.err.println("homeSlides:"+homeSlide.toString());
		try {
			
			String[] pics = picIds.split(",");
			System.err.println(pics+"---------------------");
			int i= 0;
			List<CmHomeSlideTbl> homeSlides=JSONArray.parseArray(homeSlide,CmHomeSlideTbl.class);
			for(CmHomeSlideTbl h:homeSlides){
				String picId = pics[i];i++;
				System.out.println(picId);
				
				h.setAccessory(SmAccessoryTbl.find.byId(Integer.parseInt(picId)));
				System.out.println(h.toString());
				
				//添加所属应用
				h.setWebSet(this.getLoginUser().getWebSet());
				//添加所属用户
				h.setCreator(getLoginUser());
				//添加轮播图播放时间
				h.setPauseTime(pauseTime);
				//添加文字说明状态
				h.setRemarkState(remarkState);
				
				System.out.println(h.getId()+"-----------------------------------");
				//判断轮播图是否存在，存在则更新，不存在则保存
				if(CmHomeSlideTbl.find.where().eq("id",h.getId()).findUnique()!=null){
					h.update();
				}else{
					h.save();
				}
				
				//更新session数据刷新图片
				SmWebSetTbl webset=SmWebSetTbl.find.byId(this.getLoginUser().getWebSet().getId());
				HttpSession session = request.getSession();
				session.setAttribute(Const.SESSION_WEB_SET, webset);
			}
			addResult(mv, true, "保存成功!");
			return mv;
		} catch (Exception e1) {
			e1.printStackTrace();
			addResult(mv, false, "保存失败!");
			logger.info("参数设置有误");
			return mv;
		}
	}
	/**
	 * @author 张栩强
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/admin/system/deleteSlide")
	public ModelAndView deleteSlide(HttpServletRequest request,HttpServletResponse response,int id){
		ModelAndView mv=createView("", request, response);
		try {
			CmHomeSlideTbl slide=CmHomeSlideTbl.find.byId(id);
			slide.delete();
			//更新session数据刷新图片
			SmWebSetTbl webset=SmWebSetTbl.find.byId(this.getLoginUser().getWebSet().getId());
			HttpSession session = request.getSession();
			session.setAttribute(Const.SESSION_WEB_SET, webset);
			addResult(mv, true, "删除成功!");
		
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, "删除失败!");
		}
		return mv;
	}
	
	


}
