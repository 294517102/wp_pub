package com.tjsj.wp.mvc.controller.cms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmArticleTemplateTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
@Controller
public class TemplateMaintenanceController extends WpBaseController {
	/**
	 * @author andrew_silence
	 * 跳转到文章模板列表页面
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping("to_article_template_list")
	public ModelAndView toArticleTemplateList(HttpServletRequest request,HttpServletResponse response,PageParameter page,String name){
		ModelAndView  mv=createBgView("content_manager/template/temp_list", request, response);
		try{
			ExpressionList<CmArticleTemplateTbl> exp=CmArticleTemplateTbl.find.where().eq("isDelete", -1).eq("webSet", getWebSet(request)).eq("state", 1);
			if(StringUtils.isNotBlank(name)){
				exp.like("name","%"+name+"%");
			}
			PagedList<CmArticleTemplateTbl> resultList=exp.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, resultList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 新增模板
	 * @param request
	 * @param response
	 * @param url
	 * @param name
	 */
	 @RequestMapping("admin/add_article_tmeplate")
	 public void add_article_tmeplate(HttpServletRequest request,HttpServletResponse response,String url,String name,String from_tbl,String p_id,String p_key,String p_url){
		 ResultJson rj=new ResultJson();
		 try{
			 if(StringUtils.isBlank(url)&&StringUtils.isBlank(name)){
				 rj.setResult(false);
				 rj.setResultMsg("参数错误");
				 outJson(response, rj);
				 return ;
			 }
			 CmArticleTemplateTbl at=new CmArticleTemplateTbl();
			 at.setName(name);
			 at.setUrl(url);
			 at.setUser(this.getLoginUser());
			 at.setWebSet(getWebSet(request));
			 at.setState(1);
			 at.setIsDelete(-1);
			 
//			 组装模板链接
			 JSONObject urlObj=new JSONObject();
			 if(StringUtils.isNotBlank(from_tbl)){
				 urlObj.put("from_tbl", from_tbl); 
			 }
			 if(StringUtils.isNotBlank(p_id)){
				 urlObj.put("id:",p_id); 
			 }
			 if(StringUtils.isNotBlank(p_key)){
				 urlObj.put("keyword",p_key); 
			 }
			 if(StringUtils.isNotBlank(p_url)){
				 urlObj.put("t_url",p_url); 
			 }
			 
			 at.setParameter(urlObj.toString());
			 at.save();
			//记录日志
				CmSysLogTbl log=new CmSysLogTbl();
				log.setWebset(getWebSet(request));
				log.setoPerator(getLoginUser());
				log.setType(2);
				log.setTableName("cm_article_template_tbl");
				log.setRemark("新增文章模板"+at.getId());
				log.save();
			 rj.setResult(true);
			 rj.setResultMsg("保存成功");
			 outJson(response, rj);
			 return ;
		 }catch(Exception e){
			 e.printStackTrace();
			 rj.setResult(false);
			 rj.setResultMsg("保存异常");
			 outJson(response, rj);
			 return ;
		 }
	 }
	 /**
	  * 删除模板
	  * @param request
	  * @param response
	  * @param id
	  */
	 @RequestMapping("delete_article_template")
	 public void delete_article_template(HttpServletRequest request,HttpServletResponse response,Integer id){
		 ResultJson rj=new ResultJson();
		 try{
			 if(id==null){
				 rj.setResult(false);
				 rj.setResultMsg("参数错误");
				 outJson(response, rj);
				 return ;
			 }
			 CmArticleTemplateTbl at=CmArticleTemplateTbl.find.byId(id);
			 at.setIsDelete(-1);
			 at.update();
			//记录日志
				CmSysLogTbl log=new CmSysLogTbl();
				log.setoPerator(getLoginUser());
				log.setType(1);
				log.setTableName("cm_article_template_tbl");
				log.setRemark("删除文章模板"+at.getId());
				log.save();
			 rj.setResult(true);
			 rj.setResultMsg("删除成功");
			 outJson(response, rj);
			 return ;
		 }catch(Exception e){
			 e.printStackTrace();
			 rj.setResult(false);
			 rj.setResultMsg("删除异常");
			 outJson(response, rj);
			 return ;
		 }
	 }
	 /**
		 * 根据ID批量删除
		 * @author zp
		 * @param request
		 * @param response
		 * @param spCodesTemp
		 */
		@RequestMapping("delete_article_template_all")
		@ResponseBody
		public ModelAndView deleteAll(HttpServletRequest request,HttpServletResponse response,String spCodesTemp){
			System.out.println("进入方法并获取到参数"+spCodesTemp);
			ModelAndView mv = createView("", request, response);
			CmArticleTemplateTbl at=null;
			
			if(spCodesTemp==null){
				addResult(mv, false, "没有选中的模板!");
				return mv;
			}
			//接收参数","分割数据
			String [] spCodesTemps =spCodesTemp.split(",");
			//遍历spCodesTemps
			ebeanServer.beginTransaction();
			for (int i = 0; i < spCodesTemps.length; i++) {
					//String[]转int[]
					int id = Integer.parseInt(spCodesTemps[i]);
					try {
						 at = CmArticleTemplateTbl.find.byId(id);
						//判断为空
						if(at == null){
							addResult(mv, false, "删除失败,没有该模板!");
							return mv;
							}
							//调用持久层
							at.setIsDelete(-1);
							at.update();
							//记录日志
							CmSysLogTbl log=new CmSysLogTbl();
							log.setoPerator(getLoginUser());
							log.setType(1);
							log.setTableName("cm_article_template_tbl");
							log.setRemark("删除文章模板"+at.getId());
							log.save();
							addResult(mv, true, "删除成功!");	
					} catch (Exception e) {
						addResult(mv, false, "删除失败!");
						return mv;
					}	
				}
			ebeanServer.commitTransaction();
			return mv;
		}
}
