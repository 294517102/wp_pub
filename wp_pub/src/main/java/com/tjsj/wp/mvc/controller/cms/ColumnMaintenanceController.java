package com.tjsj.wp.mvc.controller.cms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.stat.TableStat.Mode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.sun.org.apache.regexp.internal.recompile;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnAliPayConfigTbl;
import com.tjsj.wp.orm.entity.CmArticleTbl;
import com.tjsj.wp.orm.entity.CmArticleTemplateTbl;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.CmFriendlyLinkTbl;
import com.tjsj.wp.orm.entity.SmModelTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmIconTbl;
/**
 * 栏目控制层，继承WpBaseController，实现对栏目的增删改查
 * @author zp
 *
 */
@Controller
public class ColumnMaintenanceController extends WpBaseController {
	@Autowired
	private EbeanServer ebeanServer;
	
	
	
	
	/**
	 * 加载栏目列表，返回栏目列表信息到页面
	 * @param request 
	 * @param response
	 * @return 返回的页面
	 */
	@RequestMapping("admin/column/obtain_column_list")
	public ModelAndView obtain_column_list(HttpServletRequest request,HttpServletResponse response,PageParameter page,Integer judge,CmColumnTbl searchColumn,String ptitle,Integer state){
		
		ModelAndView mv = createBgView("content_manager/column/column_list", request, response);
		
		try {
			ExpressionList<CmColumnTbl> explist= CmColumnTbl.find.setUseQueryCache(true).where().eq("webSet.id",getWebSet(request).getId()).eq("isDelete", -1);
			//		栏目查看
			if(judge!=null&&judge==1){
				mv = createBgView("content_manager/column/column_show", request, response);	
				explist.isNull("parent");
			}
			if(StringUtils.isNoneBlank(searchColumn.getTitle())){
				explist.like("title", "%"+searchColumn.getTitle()+"%");
			}
			if(StringUtils.isNoneBlank(searchColumn.getKeyword())){
				explist.like("keyword", "%"+searchColumn.getKeyword()+"%");
			}
			if(StringUtils.isNoneBlank(ptitle)){
				explist.like("parent.title", "%"+ptitle+"%");
			}
			if(state!=null&&state>0){
				explist.eq("state",state);
			}
			PagedList<CmColumnTbl> columnList=explist.orderBy("id desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request,mv,columnList);
			
		} catch (Exception e) {
			e.printStackTrace();
			}
		return mv;
	}
	
	/**
	 * 添加栏目
	 * @param request
	 * @param response
	 * @param column 页面传递过来的栏目对象
	 * @param column_id 页面获取的父栏目Id
	 * @param type 栏目类型
	 */
	@RequestMapping("admin/column/save_column")
	public ModelAndView addColumn(HttpServletRequest request,HttpServletResponse response,CmColumnTbl column,Integer column_id,String type,Integer pic_id,Integer icon_id,Integer temp_id){
		ModelAndView mv=createView("", request, response);
		try{
			int count=CmColumnTbl.find.where().eq("keyword", column.getKeyword()).eq("webSet", getWebSet(request)).eq("is_delete", -1).findCount();
			
			//将所属的父栏目添加到column中
			if(column_id!=null&&column_id!=0){
				CmColumnTbl columns=CmColumnTbl.find.byId(column_id);
				column.setParent(columns);
			}
			if(pic_id!=null&&pic_id!=0 ){
				SmAccessoryTbl accessory=SmAccessoryTbl.find.byId(pic_id);
				column.setThumbnail(accessory);
			}
//			设置模板
			if(temp_id!=null&&temp_id!=0){
			CmArticleTemplateTbl temp=CmArticleTemplateTbl.find.byId(temp_id);
			if(temp!=null){
				column.setTemplate(temp);
			}
			}
			//添加图标
			if(icon_id!=null&&icon_id!=0 ){
				SmAccessoryTbl icon=SmAccessoryTbl.find.byId(Integer.valueOf(icon_id));
				column.setIcon(icon);
			}
			//将所属的应用添加到栏目中
			column.setWebSet(getWebSet(request));

			column.setDatatime(new Date());
			
			//将所属模块添加到栏目中，所有都是栏目模块
			List<SmModelTbl> list=SmModelTbl.find.where().like("name", "%内容维护%").findList();
			if(list!=null&& list.size()>0){
				column.setModel(list.get(0));
			}
			
			//设置栏目所属用户为系统登录用户
			column.setCreator(this.getLoginUser());
//			设置栏目链接
			if(StringUtils.isBlank(column.getUrl())){
//				设置默认url
				String url="/go.htm?k="+column.getKeyword();
				column.setUrl(url);
			}
			
			if(column.getId()==0){
				
				if(count>0){
					addResult(mv, false, "关键字重复请重新输入");
					return mv;
				}

				column.save();
				addResult(mv, true, "保存成功");
			}else{
				CmColumnTbl olderc=CmColumnTbl.find.byId(column.getId());
				if(count>0&&!olderc.getKeyword().equals(column.getKeyword())){
					addResult(mv, false, "关键字重复请重新输入");
					return mv;
				}
				ClassUtil.copyObjectByPropertyValues(olderc, column);
				
				
				olderc.update();
//				更新session
				SmWebSetTbl webset=SmWebSetTbl.find.byId(getWebSet(request).getId());
				HttpSession session =	request.getSession();	
				session.setAttribute(Const.SESSION_WEB_SET,webset);	
				addResult(mv, true, "修改成功");
			}
			return mv;
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;
		}
	}
	
	/**
	 * 加载栏目列表，返回栏目列表信息到页面
	 * @param request 
	 * @param response
	 * @return 返回的页面
	 */
	@RequestMapping("admin/single_search_column")
	public ModelAndView obtain_column_list(HttpServletRequest request,HttpServletResponse response,Integer cid){
		ModelAndView mv=createBgView("content_manager/column/column_editor", request, response);
		try {
			if(StringUtil.isNotBlank(cid)){
				CmColumnTbl data=CmColumnTbl.find.byId(cid);
				mv.addObject("data", data);
				List<CmColumnTbl> column_list=CmColumnTbl.find.where().eq("isDelete", -1).eq("webSet",getWebSet(request)).findList();
				mv.addObject("column_list",column_list);
			}
		} catch (Exception e) {
		e.printStackTrace();
		addResult(mv, false, e.getMessage());
		}
		return mv;
	}
	
	/**
	 * 添加栏目
	 * @param request
	 * @param response
	 * @param column 页面传递过来的栏目对象
	 * @param column_id 页面获取的父栏目Id
	 * @param type 栏目类型
	 */
	@RequestMapping("admin/column/save_column_cn")
	public ModelAndView addEnglishColumn(HttpServletRequest request,HttpServletResponse response,CmColumnTbl column,String column_id,String type,String thumbnails,String icon_id){
		ModelAndView mv = createBgView("", request, response);
		try{
			//将所属的父栏目添加到column中
			if(StringUtils.isNotBlank(column_id)){
				CmColumnTbl columns=CmColumnTbl.find.byId(Integer.valueOf(column_id));
				column.setParent(columns);
			}
			
			if(StringUtils.isNotBlank(thumbnails)){
				SmAccessoryTbl accessory=SmAccessoryTbl.find.byId(Integer.valueOf(thumbnails));
				column.setThumbnail(accessory);
			}
			//添加图标
			if(StringUtils.isNotBlank(icon_id)){
				SmAccessoryTbl icon=SmAccessoryTbl.find.byId(Integer.valueOf(icon_id));
				column.setIcon(icon);
			}
			//将所属的应用添加到栏目中
				column.setWebSet(getWebSet(request));
			if(column.getIcon().getId()!=0){
				SmAccessoryTbl icon=SmAccessoryTbl.find.byId(column.getIcon().getId());
				column.setIcon(icon);
			}
			//将所属模块添加到栏目中，所有都是栏目模块
			column.setModel(SmModelTbl.find.byId(21));
			//设置栏目所属用户为系统登录用户
			column.setCreator(this.getLoginUser());
			//添加栏目类型
			column.setType(Integer.valueOf(type));
			column.save();
			addResult(mv, true, "添加成功!");
			return mv;
		}catch(Exception e){
			addResult(mv, false, "添加失败!");
			return mv;
		}
	}
	
	
	

	/**
	 * 新增栏目页跳转
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 */
	@RequestMapping("admin/column/to_column_add")
	public ModelAndView toColumnAdd(HttpServletRequest request,HttpServletResponse response,String url){
		ModelAndView mv=createBgView(url, request, response);
		List<CmColumnTbl> column_list=CmColumnTbl.find.setUseQueryCache(true).where().eq("isDelete", -1).eq("webSet",getWebSet(request)).findList();
		mv.addObject("column_list",column_list);
		List<CmArticleTemplateTbl> templist=CmArticleTemplateTbl.find.setUseQueryCache(true).where().eq("isDelete", -1).eq("state", 1).eq("webSet", getWebSet(request)).findList();
		mv.addObject("templist",templist);
		return mv;
	}
	
	/**
	 * 英文新增栏目页跳转
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 */
	@RequestMapping("admin/column/to_column_cn")
	public ModelAndView to_column_cn(HttpServletRequest request,HttpServletResponse response,String url){
		ModelAndView mv=createBgView(url, request, response);
		List<SmWebSetTbl> webSets = Lists.newArrayList();
		for(SmWebSetTbl ws :getWebSet(request).getChilren()){
			webSets.add(ws);
		}
		List<CmColumnTbl> column_list=CmColumnTbl.find.where().in("webSet",webSets).eq("is_delete", -1).eq("model.id", 21).findList();
		List<SmIconTbl>   icon_list=SmIconTbl.find.all();
		mv.addObject("column_list",column_list);
		mv.addObject("icon_list",icon_list);
		return mv;
	}
	
	/**
	 * 根据id删除栏目，改变栏目状态为1
	 * @param response
	 * @param id 栏目id
	 */
	@RequestMapping("admin/column/delete_column")
	public ModelAndView deleteColumn(HttpServletRequest request,HttpServletResponse response,int id,int judge){
		ModelAndView mv = createView("", request, response);
		try{
		//通过ID查询栏目
		CmColumnTbl column=CmColumnTbl.find.byId(id);
		//判断栏目是否为空
		if(column==null){
			addResult(mv, false, "栏目不存在!");
			return mv;
		}
		if(column.getChildren().size()>0){
			addResult(mv, false, "删除栏目失败,确认当前栏目下是否有子栏目");
			return mv;
		}
		//执行删除操作,将删除状态改为1
		if(judge==1){
			column.setIsDelete(1);
			column.update();
			addResult(mv, true, "栏目删除成功!");
		}else{
			//判断当前状态,改变为相反状态
			if(column.getState()==1)column.setState(0);
			else column.setState(1);
			column.update();
			addResult(mv, true, "栏目操作成功!");
		}
		}catch(Exception e){
			addResult(mv, false, "操作栏目失败,确认当前栏目下是否有子栏目");
			return mv;
		}
		return mv;
		
	}
	/**栏目启用or禁用
	 * 根据id启用or禁用栏目，改变栏目状态为1
	 * @param response
	 * @param id 栏目id
	 */
	@RequestMapping("admin/column/open_close_column")
	public ModelAndView openCloseColumn(HttpServletRequest request,HttpServletResponse response,int id){
		ModelAndView mv=createView("", request, response);
		try{
		//通过ID查询栏目
		CmColumnTbl column=CmColumnTbl.find.byId(id);
		//判断栏目是否为空
		if(column==null){
			addResult(mv, false, "无可操作目录!");
			return mv;
		}
		//执行启用或禁用操作,改变state状态 0:启用   1:禁用
		int state;
		if(column.getState()==0){
			state=1;
			column.setState(state);
		}else {
			state=0;
			column.setState(state);
		}
		column.update();
		//如果存在子级目录   并禁用子级目录
		if(column.getChildren().size()>0){
			List<CmColumnTbl> columnChilren = CmColumnTbl.find.where().eq("parent_id", id)
																	  .eq("is_delete",0)
																	  .findList();
			for (CmColumnTbl cmColumnTbl : columnChilren) {
				cmColumnTbl.setState(state);
				cmColumnTbl.update();
			}
		}
		
		addResult(mv, true, "操作成功!");
		/*System.err.println("栏目删除成功");*/
		}catch(Exception e){
			addResult(mv, false, "修改栏目失败");
		}
		return mv;
	}

	
	/**
	 * 到修改页面，通过Id查询到栏目栏目对象，将对象add到页面上
	 * @param request
	 * @param response
	 * @param id 栏目Id
	 * @return 返回页面地址
	 */
	@RequestMapping("admin/column/obtain_edit_column")
	public ModelAndView ObtainEditColumn(HttpServletRequest request,HttpServletResponse response,int id,Integer judge){
		ModelAndView mv =null;
	try{
		if(judge!=null){
			mv =createBgView("cms/column/column_view_edit", request, response);
		}else{
			mv=createBgView("cms/column/column_edit", request, response);
		}
		//栏目对象
		CmColumnTbl column=CmColumnTbl.find.byId(id);
		//栏目集合
		List<CmColumnTbl> column_list=CmColumnTbl.find.where().eq("isDelete", -1).eq("webSet", getWebSet(request)).findList();
		List<SmIconTbl> icon_list=SmIconTbl.find.all();
		mv.addObject("column_list",column_list);
		mv.addObject("icon_list",icon_list);
		mv.addObject("column",column);
	}catch(Exception e){
		e.printStackTrace();
	}
	return mv;
	}
	/**
	 * 到英文栏目修改页面，通过Id查询到栏目栏目对象，将对象add到页面上
	 * @param request
	 * @param response
	 * @param id 栏目Id
	 * @return 返回页面地址
	 */
	@RequestMapping("admin/column/obtain_edit_column_cn")
	public ModelAndView obtain_edit_column_cn(HttpServletRequest request,HttpServletResponse response,int id){
		ModelAndView mv=createBgView("cms/column_cn/column_edit", request, response);
		System.err.println(id+"修改的Id");
		List<SmWebSetTbl> webSets = Lists.newArrayList();
		for(SmWebSetTbl ws :getWebSet(request).getChilren()){
			webSets.add(ws);
		}
		//栏目对象
		CmColumnTbl column=CmColumnTbl.find.byId(id);
		//栏目集合
		List<CmColumnTbl> column_list=CmColumnTbl.find.where().eq("is_delete", 0).in("webSet",webSets).findList();
		List<SmIconTbl> icon_list=SmIconTbl.find.all();
		mv.addObject("column_list",column_list);
		mv.addObject("icon_list",icon_list);
		mv.addObject("column",column);
		return mv;
	}
	/**
	 * 保存需要修改的栏目信息
	 * @param response
	 * @param column
	 * @param column_id
	 * @param webSet_id
	 * @param type
	 */
	@RequestMapping("admin/column/update_column")
	public ModelAndView saveEditColumn(HttpServletRequest request,HttpServletResponse response,CmColumnTbl column,String column_id,String type,String thumbnails,String icon_id){
		ModelAndView mv = createView("", request, response);
		try {
		//判断参数是否正确
		if(column.getId()==0){
			addResult(mv, false, "参数错误!");
			return mv;
		}
		CmColumnTbl dColumn=CmColumnTbl.find.byId(column.getId());
		if(StringUtils.isBlank(column_id)){
			column.setParent(null);
		}
		if(StringUtils.isNotBlank(thumbnails)){
			column.setThumbnail(SmAccessoryTbl.find.byId(Integer.valueOf(thumbnails)));
		}
		//将父栏目添加到栏目中
		if(StringUtils.isNotBlank(column_id)){
			CmColumnTbl cm=CmColumnTbl.find.byId(Integer.valueOf(column_id));
			column.setParent(cm);
		}
			column.setWebSet(getWebSet(request));
		if(StringUtils.isNotBlank(icon_id)){
			column.setIcon(SmAccessoryTbl.find.byId(Integer.valueOf(icon_id)));
		}
		//时间添加
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(new Date());
		Date date=format.parse(time);
		column.setDatatime(date);
		if(type !=null){
			column.setType(Integer.valueOf(type));
		}
			ClassUtil.copyObjectByPropertyValues(dColumn, column);
			dColumn.update();
			addResult(mv, true, "更新栏目成功!");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, "更新栏目失败!");
			return mv;
		}
		
		
	}
	
	/**
	 * 根据ID批量删除或禁用
	 * 添加   judge judge=1为删除   judge=2为改变状态禁用,启用
	 * @author zp
	 * @param request
	 * @param response
	 * @param spCodesTemp
	 */

	@RequestMapping("/admin/column/deleteAll_column")
	@ResponseBody
	public ModelAndView deleteAll(HttpServletRequest request,HttpServletResponse response,String spCodesTemp,int judge){
		System.out.println("进入方法并获取到参数"+spCodesTemp);
		ModelAndView mv = createView("", request, response);
		if(spCodesTemp==null){
			addResult(mv, false, "没有选中要删除的栏目");
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
					CmColumnTbl column = CmColumnTbl.find.byId(id);
					//判断为空
					if(column == null){
						addResult(mv, false, "操作失败,没有该栏目!");
						return mv;
					}
						//调用持久层,,,根据
					if(judge==1){
						column.setIsDelete(1);
						column.update();
						addResult(mv, true, "删除成功!");
					}else{
						column.setState(1);
						column.update();
						addResult(mv, true, "改变状态成功!");
					}	
				} catch (Exception e) {
					addResult(mv, false, "操作失败!");
					return mv;
				}		
			}
		ebeanServer.commitTransaction();
		return mv;
	}
	
	
	@RequestMapping("admin/column/to_column_list")
	public ModelAndView to_column_list(HttpServletRequest request,HttpServletResponse response,String url){
		System.out.println("树形菜单："+url);
		ModelAndView mv=createBgView(url, request, response);
				//树形栏目菜单
				List<CmColumnTbl> column_list=CmColumnTbl.find.where().eq("web_set_id", getWebSet(request).getId()).eq("is_delete", 0).findList();
				JSONArray jsonArray=new JSONArray();
				//循环栏目集合，把栏目集合转换成ztree特定格式
				for (CmColumnTbl column : column_list) {
					JSONObject json=new JSONObject();
					json.put("id", column.getId());
					json.put("name", column.getTitle());
					//json.put("icon", column.getIcon().getName());
					//判断是否有父节点
					if(StringUtil.isBlank(column.getParent())){
						json.put("pId",0);
					}else{
						json.put("pId", column.getParent().getId());
					}
					jsonArray.add(json);
				}
				mv.addObject("zTreeNodes",jsonArray.toJSONString());
				System.err.println(jsonArray.toJSONString());
				System.err.println("栏目id："+column_list.size());
		return mv;
	}
	
	@RequestMapping("admin/column/to_column_cn_list")
	public ModelAndView to_column_cn_list(HttpServletRequest request,HttpServletResponse response,String url){
		List<SmWebSetTbl> webSets = Lists.newArrayList();
		for(SmWebSetTbl ws :getWebSet(request).getChilren()){
			webSets.add(ws);
		}
		ModelAndView mv=createBgView(url, request, response);
				//树形栏目菜单
				List<CmColumnTbl> column_list=CmColumnTbl.find.where().in("webSet",webSets).eq("model_id", 21).eq("is_delete", 0).findList();
				JSONArray jsonArray=new JSONArray();
				//循环栏目集合，把栏目集合转换成ztree特定格式
				for (CmColumnTbl column : column_list) {
					JSONObject json=new JSONObject();
					json.put("id", column.getId());
					json.put("name", column.getTitle());
					//json.put("icon", column.getIcon().getName());
					//判断是否有父节点
					if(StringUtil.isBlank(column.getParent())){
						json.put("pId",0);
					}else{
						json.put("pId", column.getParent().getId());
					}
					jsonArray.add(json);
				}
				mv.addObject("zTreeNodes",jsonArray.toJSONString());
				System.err.println(jsonArray.toJSONString());
				System.err.println("栏目id："+column_list.size());
		return mv;
	}
	
	
	@RequestMapping("admin/column/column_by_id")
	public void column_by_id(HttpServletRequest request,HttpServletResponse response,Integer id){
		CmColumnTbl column=CmColumnTbl.find.byId(id);
		ResultJson rj=new ResultJson();
		   String jsonString = JSON.toJSONString(column);  
		rj.setResultData(jsonString);
		rj.setResult(true);
		outJson(response, rj);
		System.out.println("进入这个方法："+column.getTitle());
	}
	
	@RequestMapping("admin/verify_ckeyword")
	public void column_by_id(HttpServletRequest request,HttpServletResponse response,String keyword){
		ResultJson rj=new ResultJson();
		try {
			if(StringUtils.isBlank(keyword)){
				rj.setResultMsg("参数错误");
				rj.setResult(false);
				outJson(response, rj);
			}
			int count=CmColumnTbl.find.where().eq("keyword", keyword).eq("webSet", getWebSet(request)).findCount();
			if(count==0){
				rj.setResult(true);
			}else{
				rj.setResult(false);
				rj.setResultMsg("内容已存在,请重新输入");
			}
		} catch (Exception e) {
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}
		outJson(response, rj);
	}
	/**
	 * 修改栏目的url url+key 方式注入url
	 * @param request
	 * @param response
	 * @param keyword
	 */
	@RequestMapping("admin/update_column_url")
	public void update_column_url(HttpServletRequest request,HttpServletResponse response,Integer c_id){
		ResultJson rj=new ResultJson();
		try {
			if(c_id!=null&& c_id>0){
				CmColumnTbl c=CmColumnTbl.find.byId(c_id);
				if(c.getUrl()==null){
					String url="/go.htm?k="+c.getKeyword();
					c.setUrl(url);
				}else{
					if(!StringUtils.contains(c.getUrl(), "k="+c.getKeyword())&&StringUtils.contains(c.getUrl(), "url=")){
						String url=c.getUrl();
						c.setUrl(url+"&k="+c.getKeyword());
					}
				}
				c.update();
			}else{
				List<CmColumnTbl> clist=CmColumnTbl.find.where().eq("webSet", getWebSet(request)).findList();
				for (CmColumnTbl cmColumnTbl : clist) {
					if(cmColumnTbl.getUrl()==null){
						String url="/go.htm?k="+cmColumnTbl.getKeyword();
						cmColumnTbl.setUrl(url);
					}else{
						if(!StringUtils.contains(cmColumnTbl.getUrl(), "k="+cmColumnTbl.getKeyword())&&StringUtils.contains(cmColumnTbl.getUrl(), "url=")){
							String url=cmColumnTbl.getUrl();
							cmColumnTbl.setUrl(url+"&k="+cmColumnTbl.getKeyword());
						}
					}
					cmColumnTbl.update();
				}
				
			}
			
			rj.setResult(true);
			rj.setResultMsg("操作成功");
			
		} catch (Exception e) {
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}
		outJson(response, rj);
	}
	/**
	 * 修改文章的url url+key 方式注入url
	 * @param request
	 * @param response
	 * @param keyword
	 */
	@RequestMapping("admin/update_article_url")
	public void update_article_url(HttpServletRequest request,HttpServletResponse response,Integer c_id,String url){
		ResultJson rj=new ResultJson();
		try {
			if(StringUtils.isBlank(url)){
				rj.setResult(false);
				rj.setResultMsg("文章路径不能为空");
			}
			if(c_id!=null&& c_id>0){
				CmColumnTbl c=CmColumnTbl.find.byId(c_id);
			List<CmArticleTbl> list=CmArticleTbl.find.where().eq("webSet", getWebSet(request)).eq("column", c).eq("isDelete", -1).findList();
				for (CmArticleTbl cmArticleTbl : list) {
					if(StringUtils.isBlank(cmArticleTbl.getUrl())){
						String turl="/go.htm?id="+cmArticleTbl.getId()+"&url="+url;
						cmArticleTbl.setUrl(turl);
						cmArticleTbl.update();
					}
				}
				rj.setResult(true);
				rj.setResultMsg("操作成功");
			}
			
			
		} catch (Exception e) {
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}
		outJson(response, rj);
	}
	
	
	
	
}
