package com.tjsj.wp.mvc.controller.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmCommentTbl;
import com.tjsj.wp.orm.entity.CmSmallContentTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;

@Controller
public class SmallContentMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;
	
	/**
	 * 查询当前网站的小模块内容
	 * @author 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("obtain_small_content_list")
	public ModelAndView obtain_small_content_list(HttpServletRequest request,HttpServletResponse response,Integer pageIndex,String url,PageParameter page){
		ModelAndView mv = createBgView("content_manager/small_content/small_content_list", request, response);
	try{
		/*根据当前登录用户获取吗留言信息*/
		PagedList<CmSmallContentTbl> content = CmSmallContentTbl.find.where().eq("isDelete", -1).and().or().isNull("webSet").or().eq("webSet", getWebSet(request)).orderBy("insertTime desc").
				setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
		pagePrint(request, mv, content);
		
	}catch(Exception e){
		e.printStackTrace();
	}
		return mv;
	}
	
	/**
	 * 查询模块处理列表
	 * @param request
	 * @param response
	 * @param CmSmallContentTbl
	 * @param page
	 * @param url
	 * @return
	 */
	@RequestMapping("obtain_small_content_list_like")
	public ModelAndView obtain_small_content_list_like(HttpServletRequest request, 
										  HttpServletResponse response,
										  CmSmallContentTbl content,
										  PageParameter page,
										  String url){
		ModelAndView mv = null;
		if(StringUtils.isNotBlank(url)){
			mv = createBgView(url, request, response);
		}else{
			mv = createBgView("content_manager/small_content/small_content_list", request, response);
		}
		try {
			PagedList<BaseEntity> pageList = content.getLikeOrExpressionList().eq("webSet", getWebSet(request)).eq("isDelete", -1).setFirstRow(page.getFirstRow())
											.setMaxRows(page.getMaxRows()).orderBy("insert_time desc").findPagedList();
			pagePrint(request, mv, pageList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param CmSmallContentTbl 模块处理
	 */
	@RequestMapping("updata_small_content")
	public void updata_comment(HttpServletRequest request,HttpServletResponse response,CmSmallContentTbl  content){
		ResultJson rj=new ResultJson();
		try{
			content.update();
			rj.setResult(true);
			rj.setResultMsg("操作成功");
			outJson(response, rj);
			return ;
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("处理异常");
			outJson(response, rj);
			return ;
		}
	}
	/**
	 * 模块的保存和编辑
	 * @param request
	 * @param response
	 * @param content 模块实体
	 * @param pic_id 图片id
	 */
	@RequestMapping("admin/small_content/add_small_content")
	public void add_small_content(HttpServletRequest request,HttpServletResponse response,CmSmallContentTbl content,String pic_id){
		ResultJson rj=new ResultJson();
		try{
			if(StringUtils.isNotBlank(pic_id)){
				content.setAccessory(SmAccessoryTbl.find.byId(Integer.valueOf(pic_id)));
			}
			content.setWebSet(getWebSet(request));
			content.setCreator(this.getLoginUser());
			if(content.getId()==0){//如果id=0,就表示该对象为新增
				content.save();
			}else{
				content.update();
			}
			rj.setResult(true);
			rj.setResultMsg("操作成功");
			outJson(response, rj);
			return ;
		}catch(Exception e){
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg("操作异常");
			outJson(response, rj);
			return ;
		}
	}
	@RequestMapping("obtain_small_content_by_id")
	public ModelAndView obtain_small_content_by_id(HttpServletRequest request,HttpServletResponse response,Integer id,String url){
		ModelAndView mv=null;
		
		if(StringUtils.isNotBlank(url)){
			mv=createBgView(url, request, response);
		}else{
			mv=createBgView("content_manager/small_content/small_content_editor.html", request, response);
		}
		if(id!=null&&id!=0){
			CmSmallContentTbl content=CmSmallContentTbl.find.byId(id);
			mv.addObject("data", content);
		}
		return mv;
	}
}
