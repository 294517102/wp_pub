package com.tjsj.wp.mvc.controller.cms;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.DefSet;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmArticleExtTbl;
import com.tjsj.wp.orm.entity.CmArticleTbl;
import com.tjsj.wp.orm.entity.CmArticleTemplateTbl;
import com.tjsj.wp.orm.entity.CmCodeTbl;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.tools.L2CacheManagerService;
import com.tjsj.wp.tools.SensitiveWord;

/**
 * 文章控制层,继承WpBaseController，实现对文章的增删改查
 * @author zp
 *
 */
@Controller
public class ArticleMaintenanceController extends WpBaseController{
	@Autowired
	private EbeanServer ebeanServer;
	
	private static Logger logger = LoggerFactory.getLogger(ArticleMaintenanceController.class);
	@Autowired
	private DefSet  defSet;
	
	
	/**
	 * used
	 * 跳转到新增页面 
	 * @author zp
	 * @param request
	 * @param response
	 * @param id 栏目id
	 */
	@RequestMapping("/admin/article/obtain_article_add")
	public ModelAndView obtainArticleAdd(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv=mv=createBgView("content_manager/article/article_add", request, response);
		try {
			if(id!=null&&id>0){
				CmColumnTbl pcolumn=CmColumnTbl.find.byId(id);
				mv.addObject("pcolumn",pcolumn);
			}
			List<CmColumnTbl> column_list=CmColumnTbl.find.setUseQueryCache(true).where().eq("webSet",getWebSet(request)).eq("isDelete", -1).findList();
			mv.addObject("column_list",column_list);
			
			List<CmArticleTemplateTbl> templist=CmArticleTemplateTbl.find.setUseQueryCache(true).where().eq("isDelete", -1).eq("state", 1).eq("webSet", getWebSet(request)).findList();
			mv.addObject("templist",templist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * used
	 * 保存文章
	 * @param response
	 * @param webSet_id 应用id
	 * @param content 文章内容
	 * @param column_id 栏目id
	 * @param article 文章对象
	 */
	@RequestMapping("/admin/article/add_article")
	public ModelAndView addArticle(HttpServletRequest request,HttpServletResponse response,String content,String column_id,CmArticleTbl article,
		Integer pic_id,String issue_time,String pic_list,Integer temp_id,String parentid){
		ModelAndView mv = createView("", request, response);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			if(getWebSet(request)!=null && getWebSet(request).getTitleAdditional()!=null){
				String path=this.getClass().getResource("/").getPath()+"webapp/CensorWords.txt";
				SensitiveWord sw = new SensitiveWord(path);
				sw.InitializationWork();	
				sw.filterInfo(article.getTitle(),"文章标题");
				sw.filterInfo(content,"文章内容");				
			}	
		//将所属栏目添加到文章中
		if(StringUtils.isNotBlank(column_id)){
			CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(column_id));
			article.setColumn(column);
		}
		//父级文章
		if(StringUtils.isNotBlank(parentid)){
			article.setParent(CmArticleTbl.find.byId(Integer.valueOf(parentid)));
		}
		
		//将所属应用添加到文章对象中
			article.setWebSet(getWebSet(request));
			
			if(StringUtils.isBlank(issue_time)){
				article.setIssueTime(new Date());	
			}else{
				article.setIssueTime(sdf.parse(issue_time));
			}
		//设置文章所属用户为当前登录用户
		article.setCreator(this.getLoginUser());
		//添加图片
		if(pic_id!=null && pic_id>0){
			SmAccessoryTbl pic=SmAccessoryTbl.find.byId(pic_id);
			if(pic!=null){
				article.setThumbnail(pic);	
			}
		} 
		if(StringUtils.isNotBlank(pic_list)){
		String[] ass=StringUtils.split(pic_list, ",");
		List<SmAccessoryTbl> list=new ArrayList<>();
		for (String pid : ass) {
			SmAccessoryTbl sa=SmAccessoryTbl.find.byId(Integer.valueOf(pid));
			if(sa!=null){
				list.add(sa);
			}
		}
		if(list.size()>0){
			article.setPic(list);
		}
		}
		
//		判断审核
		System.out.println("是否设置审核"+getLoginUser().getIsAudit());
		if(getLoginUser().getIsAudit()!=null&& getLoginUser().getIsAudit()==1){
			article.setState(5);
		}
		article.save();
//		article.setSequence(article.getId());
		if(temp_id!=null && temp_id>0){
			CmArticleTemplateTbl temp=CmArticleTemplateTbl.find.byId(temp_id);
			if(temp!=null){
//				设置文章的路劲
				
//				String url=StringUtils.substringBetween(temp.getUrl(), "_url=", "&");
//				String url=StringUtils.substringAfter(temp.getUrl(), "?");
//				String url=temp.getUrl();
//				if(StringUtils.isNotBlank(url)){
//					article.setUrl(url+"&id="+article.getId());	
//				}
				String url=temp.getUrl();
				if(StringUtils.isNotBlank(url)){
					if(StringUtils.contains(temp.getUrl(), "i=")){
						article.setUrl("http://"+getDomain(request)+"/"+url+article.getId());	
					}else{
						article.setUrl("http://"+getDomain(request)+"/"+url+"&c="+article.getColumn().getKeyword()+"&id="+article.getId());	
					}
				}
				article.setAtemplate(temp);
				article.update();
			}
		}
		//设置文章内容
		CmArticleExtTbl articleext=new CmArticleExtTbl();
		articleext.setContent(content);
		articleext.setArticle(article);
		articleext.save();
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;
		}
		addResult(mv, true, "添加成功");
		mv.addObject("column_id", column_id);
		return mv;
	}

	/**
	 * 根据id删除文章，将isdelete状态修改为1，表示删除
	 * @param response
	 * @param id 文章Id
	 */
	@RequestMapping("/admin/article/delete_article")
	public ModelAndView deleteArticle(HttpServletResponse response,HttpServletRequest request,int id){
		ModelAndView mv = createView("",request, response);
		try{
			//通过id查询文章实体
			CmArticleTbl article=CmArticleTbl.find.byId(id);
			//判断对象是否存在
			if(article==null){
				addResult(mv,false, "文章不存在!");
				return mv;
			}
			//执行删除操作
			article.setIsDelete(1);
			article.update();
		}catch(Exception e){
			addResult(mv,false, "删除文章失败!");
			return mv;
		}
			addResult(mv,true, "删除文章成功!");
			return mv;
	}
	
	/**
	 * used
	 * 通过id查询文章
	 * @param request
	 * @param response
	 * @param id 文章id
	 * @return 返回文章页面
	 */
	@RequestMapping("/admin/article/obtain_column_article_edit")
	public ModelAndView obtainEditArticleByColumnId(HttpServletRequest request,HttpServletResponse response,int id,PageParameter page,String title,String c_title,String orderby,String url){
		ModelAndView mv=null;
	try {
		if(StringUtils.isBlank(url)) {
			url="content_manager/article/article_list";
		}
		//通过栏目Id查询文章
		if(id!=0){
			CmColumnTbl column=CmColumnTbl.find.byId(id);
			ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("column", column).eq("isDelete",-1);
			if(StringUtils.isNotBlank(title)){
				exp.like("title", "%"+title+"%");
			}
			if(StringUtils.isNotBlank(c_title)){
				exp.like("column.title", "%"+c_title+"%");
			}
			
			
			if(column.getType()!=null && column.getType()!=1){
//			通过栏目的类型跳转页面
				ExpressionList<CmCodeTbl> c=CmCodeTbl.find.where().eq("tablename", "cm_column_tbl").eq("colname","type");
				String	turl=c.eq("value",column.getType()).findUnique().getRemark();
				mv=createBgView(turl, request, response);
			}else{
				if(StringUtils.isBlank(orderby)) {
					//orderby="insertTime desc ,updateTime desc";	
					orderby="isHome desc, sequence desc, issueTime desc ,updateTime desc";	
				}
				PagedList<CmArticleTbl> articles=exp.order(orderby).setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
				mv=createBgView(url, request, response);
				pagePrint(request, mv, articles);
			}
			
			mv.addObject("column", column);
		}
	} catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
		return mv;
	}
	
	/**
	 * 跳转到文章复制页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/admin/to_article_copy")
	public ModelAndView to_article_copy(HttpServletRequest request,HttpServletResponse response,Integer column_id,String articlelist){
		ModelAndView mv=createBgView("/content_manager/article/article_copy", request, response);
		try {
			ExpressionList<CmColumnTbl>	ext=CmColumnTbl.find.setUseQueryCache(true).where().eq("isDelete", -1).eq("webSet", getWebSet(request));
//			获取站点的所有栏目
			if(column_id!=null && column_id>0){
			ext.notIn("id", column_id);
			}
			List<CmColumnTbl> columnlist=ext.orderBy("datatime asc").findList();
			mv.addObject("columnlist", columnlist);
//			选择的文章列表
			if(StringUtils.isNoneBlank(articlelist)){
				List<CmArticleTbl> alist=new ArrayList<>();
			String arr[]=StringUtils.split(articlelist, ",");
			for (String string : arr) {
				CmArticleTbl article=CmArticleTbl.find.byId(Integer.valueOf(string));
				if(article!=null){
					alist.add(article);
				}
			}
			if(alist!=null && alist.size()>0){
				mv.addObject("alist", alist);
			}
			}
			
			JSONArray rightList=new JSONArray();
			for (CmColumnTbl cmColumnTbl : columnlist){
				JSONObject map=new JSONObject();
				map.put("id", cmColumnTbl.getId());
				if(cmColumnTbl.getParent()!=null){
				map.put("pId", cmColumnTbl.getParent().getId());
				}else{
				map.put("pId", 0);
				}
				map.put("name", cmColumnTbl.getTitle());
				map.put("open", true);
				if(!map.isEmpty()){
					rightList.add(map);
				}
			}
			mv.addObject("rightList", rightList.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * @author andrew_silence
	 * 文章复制
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/admin/article_copy")
	public ModelAndView obtArticleCopy(HttpServletRequest request,HttpServletResponse response,String columnList,String articlelist){
		ModelAndView mv=createBgView("/content_manager/article/article_copy", request, response);
		try {
			if(StringUtils.isBlank(columnList)||StringUtils.isBlank(articlelist)){
				addResult(mv, false, "参数错误!");
				return mv;
			}
			String arrcolumn[]=StringUtils.split(columnList, ",");
			String arrarticle[]=StringUtils.split(articlelist, ",");
			for (String string : arrarticle) {
				CmArticleTbl article=CmArticleTbl.find.byId(Integer.valueOf(string));
				if(article!=null){
					CmArticleTbl newarticle=new CmArticleTbl();
		
					
//					第二个从哪里复制
					for (String c : arrcolumn) {
						CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(c));
						if(column!=null){
							ClassUtil.copyObjectByPropertyValues(newarticle,article);
							newarticle.setId(0);
							newarticle.setColumn(column);  
							newarticle.save();
							
							CmArticleExtTbl aext=CmArticleExtTbl.find.byId(article.getArticleExt().getId());
							if(aext!=null && aext.getId()>0){
								CmArticleExtTbl newext=new CmArticleExtTbl();
								ClassUtil.copyObjectByPropertyValues(newext,aext);
								newext.setId(0);
								newext.setArticle(newarticle);
								newext.save();
							}
//							System.out.println("得到老的"+aext.getContent());
//							CmArticleExtTbl newaext=new CmArticleExtTbl();
//							ClassUtil.copyObjectByPropertyValues(newaext,aext);
							
						}
					}
				}
			}
			addResult(mv, true, "复制成功");
			return mv;
			
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv,false, e.getMessage());
			return mv;
		}
	}
	
	/**
	 * used
	 * 更新文章信息
	 * @param response
	 * @param article
	 */
	@RequestMapping("/admin/article/update_article")
	public ModelAndView updateArticle(HttpServletRequest request, HttpServletResponse response,CmArticleTbl article,String column_id,Integer temp_id, String content,
		Integer pic_id,String issue_time,String pic_list,String del_pic_list,String parentid,String describe){
		ModelAndView mv = createView("", request, response);
	try {
		if(article.getId()==0){
			addResult(mv, false, "参数错误!");
			return mv;
		}
		if(getWebSet(request)!=null && getWebSet(request).getTitleAdditional()!=null){
			String path=this.getClass().getResource("/").getPath()+"webapp/CensorWords.txt";
			SensitiveWord sw = new SensitiveWord(path);
			sw.InitializationWork();	
			sw.filterInfo(article.getTitle(),"文章标题");
			sw.filterInfo(content,"文章内容");				
		}	
		//根据文章id查询文章对象
		CmArticleTbl dArticle=CmArticleTbl.find.byId(article.getId());
		ClassUtil.copyObjectByPropertyValues(dArticle, article);
		
		//添加栏目对象
		if(StringUtils.isNotBlank(column_id)){
			CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(column_id));
			dArticle.setColumn(column);
		}
		if(StringUtils.isNotBlank(parentid)){
			dArticle.setParent(CmArticleTbl.find.byId(Integer.valueOf(parentid)));
		}
		if(StringUtils.isNotBlank(describe)){
			dArticle.setDescribes(describe);
		}
		if(StringUtils.isBlank(article.getUrl())){
			if(temp_id!=null && temp_id>0){
				CmArticleTemplateTbl temp=CmArticleTemplateTbl.find.byId(temp_id);
				if(temp!=null){
					//设置文章的路劲
//					String url=StringUtils.substringBetween(temp.getUrl(), "_url=", "&");
//					if(StringUtils.isNotBlank(url)){
//						dArticle.setUrl("general_service_c6Sw7Y6vOG4vjYW5Fje.htm?id="+article.getId()+"&_url="+url+"&_ttype=1");	
//					}
					String url=temp.getUrl();
					if(StringUtils.isNotBlank(url)){
						if(StringUtils.contains(temp.getUrl(), "i=")){
							dArticle.setUrl("http://"+getDomain(request)+"/"+url+dArticle.getId());	
						}else{
							dArticle.setUrl("http://"+getDomain(request)+"/"+url+"&c="+dArticle.getColumn().getKeyword()+"&id="+dArticle.getId());	
						}
					}
					
					dArticle.setAtemplate(temp);
				}
			}
		}
		dArticle.setWebSet(getWebSet(request));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(dArticle.getIssueTime()==null){
				String time=format.format(new Date());
				Date date=format.parse(time);
				dArticle.setIssueTime(date);	
			}
		if(StringUtils.isNotBlank(issue_time)){	
			dArticle.setIssueTime(format.parse(issue_time));
		}
////			多图删除操作
//			if(StringUtils.isNotBlank(del_pic_list)){
//				String[] ass=StringUtils.split(del_pic_list, ",");
//				Set<SmAccessoryTbl> list=new HashSet<>();
//				for (String pid : ass) {
//					SmAccessoryTbl sa=SmAccessoryTbl.find.byId(Integer.valueOf(pid));
//					if(sa!=null){
//						dArticle.getPic().remove(sa);
//						dArticle.update();
//					}
//				}
//			}
			if(StringUtils.isNotBlank(pic_list)){
				/**
				 * zp添加 2018-4-8  因为图片需要排序 修改
				 */
				if(dArticle.getPic().size()>0){
					dArticle.getPic().clear();
					dArticle.update();
				}
				//end
				String[] ass=StringUtils.split(pic_list, ",");
				List<SmAccessoryTbl> list=new ArrayList<>();
				for (String pid : ass) {
					SmAccessoryTbl sa=SmAccessoryTbl.find.byId(Integer.valueOf(pid));
					if(sa!=null){
						list.add(sa);
					}
				}
				if(list.size()>0){
					dArticle.getPic().addAll(list);
				}
				}
			
			if(pic_id!=null && pic_id>0){
				SmAccessoryTbl pic=SmAccessoryTbl.find.byId(pic_id);
				if(pic!=null){
					dArticle.setThumbnail(pic);	
				}
			}	
			
			logger.info("文章内容："+content);
		//添加文章内容
			CmArticleExtTbl articleExt=CmArticleExtTbl.find.setUseQueryCache(true).where().eq("article_id", dArticle.getId()).findUnique();
			if(articleExt==null){
				CmArticleExtTbl ext=new CmArticleExtTbl();
				ext.setContent(content);
				ext.setArticle(dArticle);
				ext.save();
			}else{
				articleExt.setContent(content);
				articleExt.update();
				dArticle.setArticleExt(articleExt);
			}
//			判断审核
			if(getLoginUser().getIsAudit()!=null&& getLoginUser().getIsAudit()==1){
				dArticle.setState(5);
			}
			//更新修改过的值
			dArticle.update();
			L2CacheManagerService.clear(CmArticleTbl.class);
			L2CacheManagerService.clear(SmAccessoryTbl.class);
			L2CacheManagerService.clear(CmColumnTbl.class);
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, "保存失败,"+e.getMessage());
			return mv;
		}
		
		addResult(mv, true, "保存成功");
		mv.addObject("column_id", column_id);
		return mv;
	}
	/**
	 * 根据ID批量删除
	 * @author zp
	 * @param request
	 * @param response
	 * @param spCodesTemp
	 */
	@RequestMapping("/admin/article/deleteAll_article")
	@ResponseBody
	public ModelAndView deleteAll(HttpServletRequest request,HttpServletResponse response,String spCodesTemp){
		ModelAndView mv = createView("", request, response);
		CmArticleTbl article=null;
		
		if(spCodesTemp==null){
			addResult(mv, false, "没有选中的文章!");
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
					 article = CmArticleTbl.find.byId(id);
					//判断为空
					if(article == null){
						addResult(mv, false, "删除失败,没有该文章!");
						return mv;
						}
						//调用持久层
						article.setIsDelete(1);
						article.update();
						addResult(mv, true, "删除成功!");	
				} catch (Exception e) {
					addResult(mv, false, "删除失败!");
					return mv;
				}	
			}
		ebeanServer.commitTransaction();
		return mv;
	}
	/**
	 * 根据id 查询文章
	 * @author andrew_silence
	 * @param request
	 * @param response
	 */
	@RequestMapping("/admin/searchArticleById")
	public ModelAndView SearchArticleById(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv = createBgView("content_manager/article/article_editor", request, response);
		try {
			if(id==null && id==0){
				addResult(mv, false, "参数错误!");
				return mv;
			}
			CmArticleTbl article=CmArticleTbl.find.byId(id);
			if(article==null){
				addResult(mv, false, "参数错误!");
				return mv;	
			}
			mv.addObject("article",article);
			List<CmColumnTbl> column_list=CmColumnTbl.find.setUseQueryCache(true).where().eq("webSet",getWebSet(request)).eq("isDelete", -1).findList();
			mv.addObject("column_list",column_list);
			
			List<CmArticleTemplateTbl> templist=CmArticleTemplateTbl.find.setUseQueryCache(true).where().eq("isDelete", -1).eq("state", 1).eq("webSet", getWebSet(request)).findList();
			mv.addObject("templist",templist);
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;	
		}
		return mv;
	}
	/**
	 * 待审核文章列表
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/checkArticleList")
	public ModelAndView checkArticleList(HttpServletRequest request,HttpServletResponse response,String atitle,String ctitle,PageParameter page){
		ModelAndView mv = createBgView("content_manager/article/checkarticlelist", request, response);
		try {
			ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("isDelete",-1).ne("state",1);
			if(StringUtils.isNotBlank(atitle)){
				exp.like("title", "%"+atitle.trim()+"%");
			}
			if(StringUtils.isNotBlank(ctitle)){
				exp.like("column.title", "%"+ctitle.trim()+"%");
			}
 			PagedList<CmArticleTbl> articles=exp.orderBy("updateTime desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
				mv=createBgView("content_manager/article/checkarticlelist", request, response);
				pagePrint(request, mv, articles);
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;	
		}
		return mv;
	}
	/**
	 * 跳转到图片展示
	 * @param request
	 * @param response
	 * @param id
	 * @param pojson
	 * @return
	 */
	@RequestMapping("admin/savePicArticle")
	public ModelAndView savePicArticle(HttpServletRequest request,HttpServletResponse response,Integer id,String pojson){
		ModelAndView mv = createView("news_maps", request, response);
		try {
			System.out.println("数据格式："+pojson);
			JSONArray arr=JSONArray.parseArray(pojson);
			   List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(pojson);  
			   mv.addObject("listObjectFir",listObjectFir);
			for (Object object : arr) {
				JSONObject o=(JSONObject) object;
				System.out.println(o.get("with"));
				System.out.println(o.get("height"));
				System.out.println(o.get("x"));
				System.out.println(o.get("y"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;	
		}
		return mv;
	}
	
	/**
	 * 通过文章模板获取文章的url	
	 * @param temp
	 * @param article
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private String getTempUrl( CmArticleTemplateTbl temp, CmArticleTbl article,HttpServletRequest request) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		StringBuffer url=new StringBuffer();
		if(temp!=null&&temp.getParameter()!=null){
			JSONObject  jasonObject = JSONObject.parseObject(temp.getParameter());
			Map<String,Object> map=jasonObject.getInnerMap();
			url.append("http://"+getDomain(request)+"/go.htm?url="+map.get("url"));
			
			map.get("url");
			for (String key : map.keySet()) {  
			  if(key!="url"){
				  Object value = map.get(key);  
//				  通过key获取字段值
					Field classField=article.getClass().getDeclaredField(key);
					classField.setAccessible(true);
					System.out.println("得到的字段值"+classField.get(article));
					url.append("&"+value.toString()+"="+classField.get(article).toString());
//				  System.out.println("Key = " + key + ", Value = " + value);  
			  }
			  
			}  
		}
		return url.toString();
	}

	
	@RequestMapping("testTempa")
	public ModelAndView testTempa(HttpServletRequest request,HttpServletResponse response,Integer tid,Integer id){
		ModelAndView mv = createView("", request, response);
		try {
			CmArticleTemplateTbl t=CmArticleTemplateTbl.find.byId(tid);
			JSONObject obj=JSONObject.parseObject( t.getParameter());
			Map<String,Object>  parMap = new HashMap<>();
			parMap.put("id", id);
			 Class classz=Class.forName("com.tjsj.wp.orm.entity.CmColumnTbl"); //以String类型的className实例化类

//			 Class classz = (Class)defSet.getClassType().get("com.tjsj.wp.orm.entity.CmColumnTbl");
//				BaseEntity  baseEntity =   (BaseEntity) BeanToMapUtil.convertMap(classz, parMap);
//			    if(baseEntity==null){
//			    	baseEntity =  (BaseEntity) classz.newInstance();
//			    }
			 BaseEntity  baseEntity= (BaseEntity) classz.newInstance();
			 baseEntity = (BaseEntity) baseEntity.getFind().byId(id);
			 Field[] fields = classz.getClass().getDeclaredFields();
	            for (int i = 0; i < fields.length; i++) {
	                System.out.println("所有方法之" + fields[i].getName());
	                System.out.println("修饰符类型：" + fields[i].getModifiers());
	            }
			
			    Field classField=classz.getClass().getDeclaredField("id");
				classField.setAccessible(true);
				System.out.println("得到的字段值"+classField.get(classz));
			    
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;	
		}
		return mv;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param column_id
	 * @param page
	 * @return
	 */
	@RequestMapping("/ajaxArticleList")
	public ModelAndView ajaxArticleList(HttpServletRequest request,HttpServletResponse response,Integer column_id,PageParameter page){
		ModelAndView mv = createBgView("", request, response);
		try {
			ExpressionList<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("column.id", column_id).eq("isDelete", "-1").eq("state",1);
 			PagedList<CmArticleTbl> articles=exp.orderBy("sequence desc, issueTime desc,updateTime desc ").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, articles);
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;	
		}
		return mv;
	}
	
	@RequestMapping("/addArticleDescribes")
	public void addArticleDescribes(HttpServletRequest request,HttpServletResponse response,Integer web_set_id){
		ResultJson rj=new ResultJson();
		try {
			if(web_set_id==null || web_set_id==0) {
				 rj.setResult(false);
				 rj.setResultMsg("请选择站点");
				 outJson(response, rj);
				 return ;
			}
			List<CmArticleTbl> exp=CmArticleTbl.find.setUseQueryCache(true).where().eq("isDelete", "-1").eq("state",1).eq("webSet.id", web_set_id).findList();
 			for (CmArticleTbl cmArticleTbl : exp) {
				if(cmArticleTbl.getDescribes()==null || cmArticleTbl.getDescribes()=="") {
					if(cmArticleTbl.getExt()!=null && cmArticleTbl.getExt().getContent()!=null) {
						if(parseHtml(cmArticleTbl.getExt().getContent()).length()>256) {
							cmArticleTbl.setDescribes(parseHtml(cmArticleTbl.getExt().getContent()).substring(0, 256));
						}else {
							cmArticleTbl.setDescribes(parseHtml(cmArticleTbl.getExt().getContent()));
						}
						cmArticleTbl.update();
					}
				}
			}
			
			 rj.setResult(true);
			 rj.setResultMsg("保存成功");
			 outJson(response, rj);
			 return ;
 			
		} catch (Exception e) {
			e.printStackTrace();
			 rj.setResult(false);
			 rj.setResultMsg(e.getMessage());
			 outJson(response, rj);
			 return ;
		}
	}
	  

	/**
	 * used
	 * 保存文章
	 * @param response
	 * @param webSet_id 应用id
	 * @param content 文章内容
	 * @param column_id 栏目id
	 * @param article 文章对象
	 */
	@RequestMapping("/article/add_article")
	public ModelAndView art_addArticle(HttpServletRequest request,HttpServletResponse response,String content,String column_id,CmArticleTbl article,
		Integer pic_id,String issue_time,String pic_list,Integer temp_id,String parentid){
		ModelAndView mv = createView("", request, response);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
		//将所属栏目添加到文章中
		if(StringUtils.isNotBlank(column_id)){
			CmColumnTbl column=CmColumnTbl.find.byId(Integer.valueOf(column_id));
			article.setColumn(column);
		}
		
		//父级文章
		if(StringUtils.isNotBlank(parentid)){
			article.setParent(CmArticleTbl.find.byId(Integer.valueOf(parentid)));
		}
		
		//将所属应用添加到文章对象中
			article.setWebSet(getWebSet(request));
			
			if(StringUtils.isBlank(issue_time)){
				article.setIssueTime(new Date());	
			}else{
				article.setIssueTime(sdf.parse(issue_time));
			}
		//设置文章所属用户为当前登录用户
		article.setCreator(this.getLoginUser());
		//添加图片
		if(pic_id!=null && pic_id>0){
			SmAccessoryTbl pic=SmAccessoryTbl.find.byId(pic_id);
			if(pic!=null){
				article.setThumbnail(pic);	
			}
		} 
		if(StringUtils.isNotBlank(pic_list)){
		String[] ass=StringUtils.split(pic_list, ",");
		List<SmAccessoryTbl> list=new ArrayList<>();
		for (String pid : ass) {
			SmAccessoryTbl sa=SmAccessoryTbl.find.byId(Integer.valueOf(pid));
			if(sa!=null){
				list.add(sa);
			}
		}
		if(list.size()>0){
			article.setPic(list);
		}
		}
		
//		判断审核
		System.out.println("是否设置审核"+getLoginUser().getIsAudit());
		if(getLoginUser().getIsAudit()!=null&& getLoginUser().getIsAudit()==1){
			article.setState(5);
		}
		article.save();
		if(temp_id!=null && temp_id>0){
			CmArticleTemplateTbl temp=CmArticleTemplateTbl.find.byId(temp_id);
			if(temp!=null){
//				设置文章的路劲
				
//				String url=StringUtils.substringBetween(temp.getUrl(), "_url=", "&");
//				String url=StringUtils.substringAfter(temp.getUrl(), "?");
//				String url=temp.getUrl();
//				if(StringUtils.isNotBlank(url)){
//					article.setUrl(url+"&id="+article.getId());	
//				}
				String url=temp.getUrl();
				if(StringUtils.isNotBlank(url)){
					if(StringUtils.contains(temp.getUrl(), "i=")){
						article.setUrl("http://"+getDomain(request)+"/"+url+article.getId());	
					}else{
						article.setUrl("http://"+getDomain(request)+"/"+url+"&c="+article.getColumn().getKeyword()+"&id="+article.getId());	
					}
				}
				article.setAtemplate(temp);
				article.update();
			}
		}
		//设置文章内容
		CmArticleExtTbl articleext=new CmArticleExtTbl();
		articleext.setContent(content);
		articleext.setArticle(article);
		articleext.save();
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;
		}
		addResult(mv, true, "添加成功");
		mv.addObject("column_id", column_id);
		return mv;
	}
	
	public String parseHtml(String content) {

			String txtcontent = content.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
			txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
			return txtcontent;
	}
	/**
	 * 判断参数是否含有攻击串
	 * 
	 * @param value
	 * @return
	 */
	public boolean sensitive(String value,String sensitive) {
		if (value == null || "".equals(value)) {
			return false;
		}
		if (value.contains(sensitive)) {
			return true;
		}
		/*String[] xssArr = sensitive.split("\\|");
		
		for (int i = 0; i < xssArr.length; i++) {
			System.err.println("value:"+value+"--sensitive-->"+sensitive+"xss:-->"+value.contains(xssArr[i]));
			if (value.contains(xssArr[i])) {
				return true;
			}
		}*/
		return false;
	}

}
