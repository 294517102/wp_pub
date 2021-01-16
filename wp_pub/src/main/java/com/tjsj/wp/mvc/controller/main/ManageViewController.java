package com.tjsj.wp.mvc.controller.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.ExpressionList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.codec.MD5;
import com.tjsj.m_util.date.DateUtil;
import com.tjsj.wp.entity.SystemInfo;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.mvc.controller.user.UserSystemMaintenanceController;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmModelTbl;
import com.tjsj.wp.orm.entity.SmRoleTbl;
import com.tjsj.wp.orm.entity.SmSiteSkimTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.tools.SystemLogService;

/**
 * 后台首页
 * @author zp
 *
 */
@Controller
public class ManageViewController extends WpBaseController {
	//日志文件
	private static Logger logger = Logger.getLogger(UserSystemMaintenanceController.class);
	
	/**
	 * 加载主页，显示主页左侧菜单栏
	 * @param request
	 * @param response
	 * @return 返回主界面
	 */
	@RequestMapping("/admin/obtain_home_view")
	public ModelAndView  obtain_main_view(HttpServletRequest request, HttpServletResponse response,Integer id){
		ModelAndView mv = createBgView("home", request, response);
		//判断用户是否为管理员，管理员获取站点的最高权限，非管理员获取个人用户权限
		Set<SmRoleTbl> roles = new HashSet<>();
		
		List<SmWebSetTbl> webSets =new ArrayList<>();
		SmWebSetTbl webSet = getWebSet(request);	
		webSets.add(webSet);
//		操机管理员权限
		if(this.getLoginUser().getType()==4){
			roles =   webSet.getRoles();
			for(SmWebSetTbl ws :webSet.getChilren()){
				roles.addAll(ws.getRoles());
				webSets.add(ws);
			}
		}else{ 
			roles = SmUserTbl.find.byId(this.getLoginUser().getId()).getRoles();
		}	
		//获取用户权限菜单
		Set<SmModelTbl> endModels=getModelListByUser(this.getLoginUser(), request);


		Map<String,SmModelTbl> modelTree = Maps.newHashMap();
		for (SmModelTbl m : endModels) {
			modelTree.putAll(getParentModel(m));
			
		}
		mv.addObject("modelTree", modelTree);
		
		//加载顶部一级菜单,去掉未授权的菜单
		List<SmModelTbl>  topModelscer = SmModelTbl.find.where().eq("parent.id", null).orderBy("sequence asc,id asc").findList();
//		避免it.remove(); 删除二级缓存数据导致栏目加载错误
		List<SmModelTbl>  topModels=new ArrayList<>(topModelscer) ;
		
		Iterator<SmModelTbl> it = topModels.iterator();
		while (it.hasNext()) {
			SmModelTbl m = it.next();
			if(modelTree.get(String.valueOf(m.getId()))==null){
				it.remove();
			}
		}
		mv.addObject("topModels", topModels);
		//左侧菜单
		if(id==null&& topModels.size()>0){
			id = topModels.get(0).getId();
		}
		System.out.println("获取的顶级栏目："+id);
		if(id !=null){
			SmModelTbl dModel  = SmModelTbl.find.byId(id);
			
			//通过modelTree控制本站的model权限
			mv.addObject("models", dModel.getChildren());		
		
			//加载文章,超级管理员取用户所在站点
			if(webSets.size()<=0){
				if(getWebSet(request)!=null){
					webSets.add(getWebSet(request));
//					郑彬彬 只管理各自栏目
					/*for(SmWebSetTbl ws :getWebSet(request).getChilren()){
						webSets.add(ws);
					}*/
				}else{
					if(this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)&&
							this.getLoginUser().getWebSet()!=null){
								webSets.add(this.getLoginUser().getWebSet());
								for(SmWebSetTbl ws :this.getLoginUser().getWebSet().getChilren()){
									webSets.add(ws);
								}
					}
				}
			}


//				mv.addObject("columns", endColumn);
				mv.addObject("columns", getColumnListByUser(this.getLoginUser(), request, id));
//			获取系统基本信息
			SystemInfo sysinfo=new SystemInfo(request);
			request.getSession().setAttribute("sysinfo", sysinfo);
			//网站信息统计信息
//			查询网站七天访问量信息
			List<SmSiteSkimTbl> skimlist=SmSiteSkimTbl.find.setUseQueryCache(true).where().between("insertTime", DateUtil.removeDays(new Date(), 7), new Date()).orderBy("insertTime").findList();
			Map<String,String> map=skilmLineMap(skimlist);
			request.getSession().setAttribute("skilmLineMap",map);
			mv.addObject("selected_model",id);//被选中的model栏目

		}
		return mv;
	}
	
	/**
	 * 柱状图返回的map数据
	 * @param list
	 * @return
	 */
	private Map<String,String> skilmLineMap(List<SmSiteSkimTbl> list){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		  Map<String, String> treemap = new TreeMap<String, String>(
	                new Comparator<String>() {
	                    public int compare(String obj1, String obj2) {
	                        // 降序排序
	                        return obj1.compareTo(obj2);
	                    }
	                });
		for (SmSiteSkimTbl siteSkim : list) {
				treemap.put(sdf.format(siteSkim.getInsertTime()), siteSkim.getPvSkim().toString());
		}
		
		
		
		return treemap;				
		
	}
	 /** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, String> sortMapByKey(Map<String, String> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  

        //这里将map.entrySet()转换成list  
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());  
        //然后通过比较器来实现排序  
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {  
            //升序排序  
            public int compare(Entry<String, String> o1,  
                    Entry<String, String> o2) {  
                return o1.getValue().compareTo(o2.getValue());  
            }  
  
        });  
//        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());  
//        sortMap.putAll(map);  
        return map;  
    }
    
	/**
	 * 显示默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/admin/obtain_default_view")
	public ModelAndView showIndexView(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("bg/default", request, response);
		return mv;
	}
	@RequestMapping("/admin/obtain_home_model")
	public ModelAndView obtain_home_model(HttpServletRequest request,HttpServletResponse response,int id){
		ModelAndView mv=createBgView("bg/home", request, response);
		return mv;
	}
	/**
	 * 登录用户去修改的页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/admin/user/obtain_user_update")
	public ModelAndView obtain_user_update(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("user/system_login_user_edit", request, response);
			SmUserTbl user_edit=SmUserTbl.find.byId(this.getLoginUser().getId());
			for (SmRoleTbl user : user_edit.getRoles()) {
				System.err.println(user.getId()+"修改的数据");
			}
			
			mv.addObject("user_edit",user_edit);
		return mv;
	}
	/**
	 * 保存登录用户修改的资料
	 * @param request
	 * @param response
	 * @param user
	 * @param role_id
	 * @param password
	 */
	@RequestMapping("/admin/user/update_login_user")
	public ModelAndView updateUser(HttpServletRequest request,HttpServletResponse response,SmUserTbl user,Integer role_id,String password,String thumbnails){
		ModelAndView mv = createView("", request, response);
		try{
			if(user.getId()==0){
				addResult(mv, false, "参数错误!");
				return mv;
			}
			SmUserTbl dUser = SmUserTbl.find.byId(user.getId());
			if(StringUtils.isNotBlank(password)){
				user.setPassword(MD5.md5(password));
			}
			if(role_id!=null){
				dUser.getRoles().clear();
				dUser.getRoles().add(SmRoleTbl.find.byId(role_id));
			}
			ClassUtil.copyObjectByPropertyValues(dUser, user);
			System.err.println(thumbnails);
			if(StringUtils.isNotBlank(thumbnails)){
				SmAccessoryTbl accwssory = SmAccessoryTbl.find.byId(Integer.valueOf(thumbnails));
				dUser.setThumbnail(accwssory);
			}
			dUser.update();
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;
		}
		SystemLogService.SaveLogToDB("sm_user_tbl",2, user.getId() , "修改了用户["+user.getUsername()+"]资料");
		addResult(mv, true, "更新用户成功!");
		logger.debug("更新用户成功");		
		return mv;
	}
	/**
	 * 读取站点列表
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	@RequestMapping("/admin/web_site_list")
	public ModelAndView webSiteList(HttpServletRequest request,HttpServletResponse response,Integer pageSize,Integer pageIndex){
		ModelAndView mv = createBgView("system_setting/site_manager/web_site_list", request, response);
		try{
			pageIndex = pageIndex==null?0:pageIndex;
			pageSize = pageSize==null?Const.PAGE_SIZE:pageSize;
			PageParameter page=new PageParameter(pageIndex,pageSize);
			ExpressionList<SmWebSetTbl> qExpress = SmWebSetTbl.find.where();
			pagePrint(request, mv, qExpress.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList());
		}catch(Exception e){
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
			return mv;
		}
			
		return mv;
	}
	
	/**
	 * 后台切换语音版本读取对应的栏目
	 * @param request
	 * @param response
	 * @param id
	 * @param language
	 * @return
	 */
	@RequestMapping("/admin/obtain_main_view_language")
	public ModelAndView  obtain_main_view_language(HttpServletRequest request, HttpServletResponse response,Integer site_id,String language){
		ModelAndView mv = createBgView("bg/home", request, response);
		//判断用户是否为管理员，管理员获取站点的最高权限，非管理员获取个人用户权限
		Set<SmRoleTbl> roles = new HashSet<>();
		List<SmWebSetTbl> webSets = Lists.newArrayList();
		if(this.getLoginUser().getType()==4&&!this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)){
			SmWebSetTbl webSet = getWebSet(request);
		
			webSets.add(webSet);
			/*roles =   webSet.getRoles();
			for(SmWebSetTbl ws :webSet.getChilren()){
				roles.addAll(ws.getRoles());
				webSets.add(ws);
			}*/
		}else{ 
			roles = SmUserTbl.find.byId(this.getLoginUser().getId()).getRoles();
		}	
		//获取用户权限菜单
		Set<SmModelTbl> endModels=new HashSet<>();
		Set<CmColumnTbl> endColumn = new HashSet<>();
		
		for (SmRoleTbl role : roles) {
			endModels.addAll(role.getModels());
			//获取用户栏目权限
			if(!this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)){
				endColumn = CmColumnTbl.find.where()
						.in("webSet",webSets)
						.eq("is_delete", -1)
						.findSet();
			}else{
//				超级管理员拥有所有栏目管理权限
				endColumn=CmColumnTbl.find.where().eq("is_delete", -1).findSet();
				
//				endColumn.addAll(role.getColumn());
				/*System.err.println(role.getId());
				for (CmColumnTbl cmColumnTbl : endColumn) {
				}*/
			}
		}
		
		System.err.println("endModels:"+endModels);
		
		Map<String,SmModelTbl> modelTree = Maps.newHashMap();
		for (SmModelTbl m : endModels) {
			modelTree.putAll(getParentModel(m));
			
		}
		mv.addObject("modelTree", modelTree);
		//加载顶部一级菜单,去掉未授权的菜单
		List<SmModelTbl>  topModels = SmModelTbl.find.where().eq("parent.id", null).orderBy("sequence asc").findList();
		
		Iterator<SmModelTbl> it = topModels.iterator();
		while (it.hasNext()) {
			SmModelTbl m = it.next();
			if(modelTree.get(String.valueOf(m.getId()))==null){
				it.remove();
			}
		}
		mv.addObject("topModels", topModels);
		//左侧菜单
		SmModelTbl dModel  = topModels.get(0);
			
			//通过modelTree控制本站的model权限
			mv.addObject("models", dModel.getChildren());		
			
			//加载文章,超级管理员取用户所在站点
			if(webSets.size()<=0){
				if(getWebSet(request)!=null){
					webSets.add(getWebSet(request));
					for(SmWebSetTbl ws :getWebSet(request).getChilren()){
						webSets.add(ws);
					}
				}else{
					if(this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)&&
							this.getLoginUser().getWebSet()!=null){
								webSets.add(this.getLoginUser().getWebSet());
								for(SmWebSetTbl ws :this.getLoginUser().getWebSet().getChilren()){
									webSets.add(ws);
						}
					}
				}
			}
//			获取系统基本信息
			SystemInfo sysinfo=new SystemInfo(request);
			request.getSession().setAttribute("sysinfo", sysinfo);
			mv.addObject("selected_model",dModel.getId());//被选中的model栏目

		return mv;
	}
	/**
	 * 通过用户得到用户的权限栏目
	 * @param u
	 * @return
	 */
	private Set<SmModelTbl> getModelListByUser(SmUserTbl u,HttpServletRequest request){
//		超级管理员获取最高权限
		Set<SmModelTbl> all=new HashSet<>();
		if(u.getType()==4 && u.getUsername().equalsIgnoreCase(Const.ADMIN)){
			all.addAll(SmModelTbl.find.all());
		}else{
		Set<SmRoleTbl> list =u.getRoles();
			for (SmRoleTbl role : list) {
				all.addAll(role.getModels());
			}
		}
		return all;
		}
	private Set<CmColumnTbl> getColumnListByUser(SmUserTbl u,HttpServletRequest request,Integer mid){
//		超级管理员获取最高权限
		Set<CmColumnTbl> all=new HashSet<>();
		ExpressionList<CmColumnTbl> exp=CmColumnTbl.find.setUseQueryCache(true).where().eq("model.id", mid).eq("is_delete", -1).eq("webSet", getWebSet(request));
		if(u.getType()==4 && u.getUsername().equalsIgnoreCase(Const.ADMIN)){
			all=exp.orderBy("datatime asc").findSet();
		}else{
			Set<SmRoleTbl> list =u.getRoles();
			all=exp.in("roleList",list).orderBy("datatime asc").findSet();
		}
		return all;
	}

	
	
}
