package com.tjsj.wp.mvc.controller.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnPicWebTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

@Controller
public class PicWebMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;

	@RequestMapping("/admin/savepicweb")
	public ModelAndView savepicweb(HttpServletRequest request,HttpServletResponse response,BnPicWebTbl picweb,Integer pic_id){
		ModelAndView mv=createBgView("", request, response);
		try {
			SmUserTbl u=SmUserTbl.find.byId(getLoginUser().getId());
			if(picweb!=null && picweb.getId()!=0){
				BnPicWebTbl o=BnPicWebTbl.find.byId( picweb.getId());
				ClassUtil.copyObjectByPropertyValues(o, picweb);
				if(pic_id!=null &&pic_id!=0){
					SmAccessoryTbl p=SmAccessoryTbl.find.byId(pic_id);
					if(p!=null){
						o.setPic(p);
					}
				}
				o.update();
			}else{
				if(pic_id==null ||pic_id==0){
					throw new com.tjsj.m_util.exception.ParameterException("请上传图片");
				}
				SmAccessoryTbl p=SmAccessoryTbl.find.byId(pic_id);
				if(p==null){
					throw new com.tjsj.m_util.exception.ParameterException("请上传图片");
				}
				picweb.setState(1);
				picweb.setIsDelete(-1);
				picweb.setPic(p);	
				picweb.setUser(u);
				picweb.save();
				String relativeRootPath = request.getRequestURL().toString().replace(request.getRequestURI(), "")+"/";
//			String url=relativeRootPath+"/general_service_sgYgS4FBRLyq1X5bkjC.htm?id="+picweb.getId();
				String url=relativeRootPath+"/sctjsj_ui_design_"+picweb.getId()+".htm";
				picweb.setUrl(url);
				picweb.update();
			}
			addResult(mv, true, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, "操作失败"+e.getMessage());
			}
		return mv;
	}
	@RequestMapping("/sctjsj_ui_design")
	public ModelAndView sctjsj_ui_design(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv=createBgView("tools/web_page/index", request, response);
		try {
			if(id==null ||id==0){
				throw new com.tjsj.m_util.exception.ParameterException("网页不存在");
			}
			BnPicWebTbl web_pic=BnPicWebTbl.find.byId(id);
			mv.addObject("web_pic",web_pic);
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, "操作失败"+e.getMessage());
		}
		return mv;
	}
	

}
