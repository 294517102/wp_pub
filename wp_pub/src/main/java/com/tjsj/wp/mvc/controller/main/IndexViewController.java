package com.tjsj.wp.mvc.controller.main;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.m_util.http.DeviceUtils;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnOrderTbl;
import com.tjsj.wp.orm.entity.CmArticleExtTbl;
import com.tjsj.wp.orm.entity.CmArticleTbl;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.CmCommentTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

@Controller
public class IndexViewController extends WpBaseController {

	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,String language){
		if(request.getParameter("terminal")==null) {
		DeviceUtils.getDeviceUrl(request, getWebSet(request).getType());
		}
		ModelAndView mv = createView("index", request, response);
		
		if( request.getSession(true).getAttribute("terminal")==null && getWebSet(request).getType()!=null&&getWebSet(request).getType()==1&&DeviceUtils.getDeviceType(request)!=1){
			request.getSession().setAttribute("terminal", "pc");
		}
		return mv;
	}
	
	/**
	 * 根据栏目id查询栏目下的文章
	 * @author 张栩强
	 * @param request
	 * @param response
	 * @param url
	 * @param id
	 * @return
	 */
	@RequestMapping("columnDetails")
	public ModelAndView obtain_column_details(HttpServletRequest request,HttpServletResponse response,String url,Integer id){
		if(url==null){
			url="";
		}
		ModelAndView mv = createView(url, request, response);
		
		try {
			CmColumnTbl column=CmColumnTbl.find.byId(id);
			mv.addObject("column",column);
			addResult(mv, true, "查询成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 跳转文章详情页面，比如新闻详情，产品详情
	 * @param request
	 * @param response
	 * @param id 文章Id
	 * @return 详情页面
	 */
	@RequestMapping("/details")
	public ModelAndView obtain_news_details(HttpServletRequest request,HttpServletResponse response,String url,Integer id){
	
		ModelAndView mv=createView(url, request, response);
		CmArticleTbl article=CmArticleTbl.find.where().eq("isDelete",-1).and().eq("id",id).findUnique();
		mv.addObject("article",article);
		return mv;
	}
	@RequestMapping("admin/details")
	public ModelAndView obtain_admin_details(HttpServletRequest request,HttpServletResponse response,String url,Integer id){
		ModelAndView mv=createView(url, request, response);
		CmArticleTbl article=CmArticleTbl.find.byId(id);
	
		mv.addObject("article",article);
		return mv ;
	}
	/**
	 * 分页查询数据信息
	 * @param request
	 * @param response
	 * @param url 要跳转页面的Url
	 * @param keyword 栏目的关键字
	 * @param pageIndex 当前页
	 * @return 返回到页面
	 */
	@RequestMapping("obtain_page_list")
	public ModelAndView obtain_page_list(HttpServletRequest request,HttpServletResponse response,String title,String url,String keyword,Integer pageIndex,Integer size,String fileColumn,String sort){
		ModelAndView mv=createView(url, request, response);
		HttpSession session  = request.getSession();
		try {
			SmWebSetTbl webSet = (SmWebSetTbl) session.getAttribute(Const.SESSION_WEB_SET);
		
			CmColumnTbl column =null;
			if(pageIndex==null) pageIndex = 0;
			if(size==null) size= Const.PAGE_SIZE;
			if(keyword!=null){
				column =CmColumnTbl.find.where().eq("keyword", keyword).eq("web_set_id", webSet.getId()).eq("is_delete", 0).findUnique();
			}
			if(column!=null){
				if(StringUtils.isNoneBlank(fileColumn)&&StringUtils.isNotBlank(sort)){
					/*PagedList<CmArticleTbl> article=CmArticleTbl.find.where().eq("column_id", column.getId()).
							eq("is_delete", 0).order(fileColumn+" "+sort).findPagedList(pageIndex>0?pageIndex-1:0,size);
					pagePrint(request, mv, article);*/
				}else{
					/*PagedList<CmArticleTbl> article=CmArticleTbl.find.where().eq("column_id", column.getId()).
							eq("is_delete", 0).order("insert_time desc").findPagedList(pageIndex>0?pageIndex-1:0,size);
					pagePrint(request, mv, article);*/
				}
				CmArticleTbl first_article = CmArticleTbl.find.where().eq("column_id", column.getId()).findList().get(0);
				mv.addObject("first_article", first_article);
			}else{
				/*PagedList<CmArticleTbl> article= CmArticleTbl.find.where().eq("webSet", this.getWebSet(request)).like("title","%"+title+"%").findPagedList(pageIndex>0?pageIndex-1:0,size);
				pagePrint(request, mv, article);*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/detailss")
	public ModelAndView newsdetails(HttpServletRequest request,HttpServletResponse response,Integer id,String url){
		ModelAndView mv=createView(url, request, response);
		List<CmArticleExtTbl> articleContext=CmArticleExtTbl.find.where().eq("article_id", id).findList();
		mv.addObject("articleContext",articleContext);
		return mv;
		
	}
	@RequestMapping("column_article_list_by_id")
	public ModelAndView columnArticleListById(HttpServletRequest request,HttpServletResponse response,String id,String url){
		ModelAndView mv=createView(url, request, response);
		if(StringUtils.isNotBlank(id)){
			CmArticleTbl article=CmArticleTbl.find.byId(Integer.valueOf(id));
			List<CmArticleTbl> articles=CmArticleTbl.find.where().eq("column", article.getColumn()).findList();
			mv.addObject("articles", articles);
			mv.addObject("data", article);
		}
		return mv;
	}
	
	@RequestMapping("/queryByCondition")
	public ModelAndView queryByCondition(HttpServletRequest request, HttpServletResponse response,String id,String url){
		
		ModelAndView mv = createView(url, request, response);
		
		List<CmArticleTbl> articles=null;
		
		if(StringUtils.isNotBlank(id)&& (!id.equals("308"))){
			
			CmArticleTbl article=CmArticleTbl.find.byId(Integer.valueOf(id));
			
			articles=CmArticleTbl.find.where().eq("source", article.getTitle()).eq("is_delete", 0).findList();
			
			for (CmArticleTbl a : articles) {
				a.setUrl(a.getThumbnail().getUrl());
			}
			
		}else{

			articles=CmArticleTbl.find.where().eq("column_id", id).findList();
			
			for (CmArticleTbl a : articles) {
				a.setUrl(a.getThumbnail().getUrl());
			}
		}
		mv.addObject("data",articles);
		return mv;
	}
	
    @RequestMapping("connectionDenied")
    public ModelAndView toDengerIp(HttpServletRequest request,HttpServletResponse response,String ip){
   	 ModelAndView mv=createBgView("connectionDenied", request, response);
   	 mv.addObject("ip", ip);
   	 return mv;
    }
    
    
    @RequestMapping("obtain_like_article_by_title")
    public ModelAndView obtain_like_article_by_title(HttpServletRequest request,HttpServletResponse response,String url,String title,String time,String keyword){
    	ModelAndView mv=createView("", request, response);;
    	if(StringUtils.isNotBlank(url)){
    		mv=createView(url, request, response);
    	}
    	
    	List<CmColumnTbl> clist=new ArrayList<>();
		CmColumnTbl columns=CmColumnTbl.find.where().eq("webSet", getWebSet(request)).eq("keyword", keyword).findUnique();
//    	ExpressionList<CmArticleTbl> ptExpress = CmArticleTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1).ne("column.parent", columns);
		ExpressionList<CmArticleTbl> ptExpress = CmArticleTbl.find.where().eq("webSet", getWebSet(request)).eq("column.keyword", keyword).eq("isDelete", -1);
		if(StringUtils.isNotBlank(title)){
    		ptExpress = ptExpress.like("title", "%" + title + "%");
    	}
    	if(StringUtils.isNotBlank(time)){
    		ptExpress = ptExpress.like("issueTime", "%" + time + "%");
    	}
    	List<CmArticleTbl> list=ptExpress.orderBy("issueTime desc").findList();
    	mv.addObject("result", list);
    	
    	return mv;
    }
    
    @RequestMapping("obtain_article_by_column")
    public ModelAndView obtain_article_by_column(HttpServletRequest request,HttpServletResponse response,String url,String keywords,
    		PageParameter page,Integer pageIndex,Integer size){
    	ModelAndView mv=createView("", request, response);
    		if(StringUtils.isNotBlank(url)){
    			mv=createView(url, request, response);
    		}
    		if(StringUtils.isBlank(keywords)){
    			addResult(mv, false, "参数错误");
    		}
    		List<CmColumnTbl> clist=new ArrayList<CmColumnTbl>();
    		String [] keyword=StringUtils.split(keywords, ",");
    		CmColumnTbl column=null;
    		for (String key : keyword) {
				column=CmColumnTbl.find.where().eq("keyword", key).eq("isDelete", -1).eq("webSet", getWebSet(request)).findUnique();
				clist.add(column);
    		}
    		if(pageIndex==null) pageIndex=1;
    		if(size==null) size=99;
    		PageParameter pp =new PageParameter(pageIndex,size);
    		PagedList<CmArticleTbl> alist=CmArticleTbl.find.where().eq("isDelete", -1).in("column", clist).orderBy("issueTime desc").
    		setFirstRow(pp.getFirstRow()).setMaxRows(pp.getMaxRows()).findPagedList();
    		pagePrint(request, mv, alist);
    		mv.addObject("_jf", "thumbnail|column|articleExt");
    	return mv;
    }
    //比斯特网站使用
    @RequestMapping("obtain_article_by_keyword_column")
    public ModelAndView obtain_article_by_keyword_column(HttpServletRequest request,HttpServletResponse response,
    		String keyword,Integer column,PageParameter page){
    	ModelAndView mv=createView("", request, response);
    	ExpressionList<CmArticleTbl> ptExpress = CmArticleTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1);
		if(column!=null){
    		ptExpress = ptExpress.eq("column.id", column);
    	}
    	if(StringUtils.isNotBlank(keyword)){
    		ptExpress = ptExpress.like("title", "%" + keyword + "%");
    	}
    	PagedList<CmArticleTbl> list=ptExpress.orderBy("issueTime desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();;
    	pagePrint(request, mv, list);
    	mv.addObject("_jf", "thumbnail|column|articleExt");
    	
    	return mv;
    }
    
    //成都芯谷人员招聘接口
    /**
     * 
     * @param request
     * @param response
     * @param com 公司名称
     * @param address 工作地点
     * @param types 职位类型
     * @param dept 职位名称
     * @param url
     * @param size
     * @param page
     * @return
     */
    @RequestMapping("obtain_recruit_article")
    public ModelAndView obtain_recruit_article(HttpServletRequest request,HttpServletResponse response,String com,String address,String types,
    		String dept,String url,String size,PageParameter page,Integer columnid){
    	ModelAndView mv=null;
    	try{
	    	if(StringUtils.isBlank(url)){
	    		mv=createView("", request, response);
	    	}else{
	    		mv=createView(url, request, response);
	    	}
	    	
	    	ExpressionList<CmArticleTbl> ptExpress = CmArticleTbl.find.where().eq("isDelete", -1).eq("webSet", getWebSet(request));
	    	if(columnid!=null){
	    		ptExpress=ptExpress.eq("column", CmColumnTbl.find.byId(columnid));
	    	}
	    	if(StringUtils.isNotBlank(com)){//公司
	    		ptExpress=ptExpress.eq("title", com);
	    	}
	    	if(StringUtils.isNotBlank(address)){//工作地点
	    		ptExpress=ptExpress.eq("author", address);
	    	}
	    	if(StringUtils.isNotBlank(types)){//职位类型
	    		ptExpress=ptExpress.eq("source", types);
	    	}
	    	if(StringUtils.isNotBlank(dept)){//岗位名称
	    		ptExpress=ptExpress.like("describes", "%"+dept+"%");
	    	}
	    	PagedList<CmArticleTbl> crmlist=ptExpress.orderBy("sequence desc, issueTime desc ,updateTime desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, crmlist);
	    	mv.addObject("_jf", "articleExt");
    	}catch(Exception e){
    		e.printStackTrace();
    		addResult(mv, false, e.getMessage());
    	}
    	return mv;
    }
    //零句金服网站  通过栏目id查询他的下级栏目
    @RequestMapping("obtain_column_children_by_id")
    public ModelAndView obtain_column_children_by_id(HttpServletRequest request,HttpServletResponse response,String url,Integer cid){
    	ModelAndView mv=null;
    	try{
    	
	    	if(StringUtils.isNotBlank(url)){
	    		mv=createView(url, request, response);
	    	}else{
	    		mv=createView("", request, response);
	    	}
	    	if(StringUtil.isBlank(cid)){
	    		throw new ParameterException("参数错误，没有父级栏目信息");
	    	}
	    	CmColumnTbl pcolumn=CmColumnTbl.find.byId(cid);
	    	if(pcolumn!=null){
	    		List<CmColumnTbl> columnList=CmColumnTbl.find.where().eq("parent", pcolumn).eq("isDelete", -1).findList();//查询当前栏目下面的子集栏目
	    		List<CmArticleTbl> articleList=CmArticleTbl.find.where().eq("isDelete", -1).eq("column", pcolumn).findList();
	    		mv.addObject("pcArticlesList",articleList);
	    		mv.addObject("columnList", columnList);
	    		mv.addObject("pcolumn", pcolumn);
	    		mv.addObject("_jf", "thumbnail|articleExt");
	    		
	    	}
	    	return mv;
    	}catch(Exception e){
    		e.printStackTrace();
    		addResult(mv, false, e.getMessage());
    	}
    	return mv;
    }
}
