package com.tjsj.wp.mvc.controller.system;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.SqlUpdate;
import com.google.common.collect.Sets;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.m_util.string.StringUtil;  
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.fwk.mvc.shiro.annotation.SessionUser;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmModelTbl;
import com.tjsj.wp.orm.entity.SmRoleTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.tools.SystemLogService;


/**
 * 角色信息控制层
 * @author zp
 *
 */
@Controller
public class RoleAuthMaintenanceController extends WpBaseController{
	//日志文件
	private static Logger logger = LoggerFactory.getLogger(RoleAuthMaintenanceController.class);
	@Autowired
	private EbeanServer ebeanServer;
	
	/**
	 * 所有父级栏目
	 */
	Set<CmColumnTbl> columns = Sets.newHashSet();
	/***
	 * 加载角色表信息，显示所有的角色列表,分页查询
	 * @param request 
	 * @param response
	 * @return 返回地址
	 */
	@RequestMapping("/admin/role/obtain_role_list")
	public ModelAndView queryRoleList( HttpServletRequest request,
										HttpServletResponse response,
										PageParameter page,
										SmRoleTbl rule,
										@SessionUser SmUserTbl user){
		ModelAndView mv=createBgView("system_setting/security/role_list", request, response);
		try {
			PagedList<BaseEntity> role_list=rule.getLikeExpressionList().eq("webSet",getWebSet(request))
						.eq("creator", user)
						.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, role_list);
		} catch (Exception e) {
			logger.error("xception throws:"+e.getMessage());
		}
		
		return mv;
	}
	
	
	@RequestMapping("/admin/role/search_obtain_role_list")
	public ModelAndView obtainLikeUser(HttpServletRequest request,HttpServletResponse response,String name,String lastLoginStart,String lastLoginEnd,Integer pageIndex){
		ModelAndView mv=createBgView("system_setting/security/role_list", request, response);
		
		if(name!=null||lastLoginStart!=null&&lastLoginEnd!=null){
			if(pageIndex==null||Const.PAGE_SIZE<1) pageIndex=1;
			ExpressionList<SmRoleTbl> v = SmRoleTbl.find.setUseCache(true).where().eq("creator",this.getLoginUser());
			if(StringUtils.isNotBlank(name)){
				v.like("name", "%"+name+"%");
			}
			if(StringUtils.isNoneBlank(lastLoginStart)&&StringUtils.isNoneBlank(lastLoginEnd)){
				v.between("insertTime", lastLoginStart, lastLoginEnd);
			}
			PagedList<SmRoleTbl> user_list=v.setFirstRow((pageIndex-1)*Const.PAGE_SIZE+1).setMaxRows(pageIndex*Const.PAGE_SIZE).findPagedList();
			pagePrint(request, mv, user_list);
		}

		return mv;
	}
	
	/**
	 * 增加角色
	 * @param request 请求
	 * @param response 响应
	 * @param role 角色对象
	 */
	@RequestMapping("/admin/role/save_role")
	public ModelAndView addRole(HttpServletRequest request,
								HttpServletResponse response,
								String modelIds,
								String name,
								String keyword,
								String columnIds,
								int state,
								Integer id){
		ModelAndView mv=createView("", request, response);
		
		SmRoleTbl role = null;
		if(id==null||id<=0){
			role = new SmRoleTbl();
		}else{
			role = SmRoleTbl.find.byId(id);
		}
		
	try{
		if(modelIds==null){
			addResult(mv, false, "请选择权限!");
		}
		String[] modelId =modelIds.split(",");
		//子栏目idList
		String[] columnIdList =columnIds.split(",");
		//遍历spCodesTemps
		ebeanServer.beginTransaction();
		role.setName(name);
		role.setKeyword(keyword);
		role.setState(state);
		Set<SmModelTbl> models = Sets.newHashSet();
		for(int i=0;i<modelId.length;i++){
			if(StringUtils.isBlank(modelId[i])){
				continue;
			}
			int mid = Integer.parseInt(modelId[i]);
			SmModelTbl model = SmModelTbl.find.byId(mid);
			if(model.getParent()!=null){
				models.add(model.getParent());
				if(model.getParent().getParent()!=null){
					models.add(model.getParent().getParent());
				}
			}
			models.add(model);
		}
//		清除全局变量数据
		columns.clear();
		for(int i=0;i<columnIdList.length;i++){
			if(StringUtils.isBlank(columnIdList[i])){
				continue;
			}
			int cid = Integer.parseInt(columnIdList[i]);
			CmColumnTbl column = CmColumnTbl.find.byId(cid);
			getParentColumn(column);
		}
		role.setColumn(columns);
		role.setModels(models);
		role.setCreator(this.getLoginUser());
		role.setWebSet(getWebSet(request));
		if(role.getId()!=0){
			role.update();
		}else{
			role.save();
		}
		ebeanServer.commitTransaction();
		addResult(mv, true, "保存成功!");
		SystemLogService.SaveLogToDB("sm_role_tbl", 2, getLoginUser().getId(), "增加角色");
		logger.debug("保存成功");
		return mv;
		}catch(Exception e){
			addResult(mv, false, "保存失败!");
			logger.error("throw Exception",e);;
			return mv;
		}
	}


	/**
	 * 获取父栏目
	 * @param column
	 */
	public void getParentColumn(CmColumnTbl column){
		if(!StringUtil.isBlank(column)){
			columns.add(column);
			if(!StringUtil.isBlank(column.getParent())){
				this.getParentColumn(column.getParent());
			}
		}
	}
	/**
	 * 根据ID删除角色
	 * @param request 请求
	 * @param response 响应
	 * @param role_id 角色Id
	 */
	@RequestMapping("/admin/role/delete_role")
	public ModelAndView delRoleById(HttpServletRequest request,HttpServletResponse response,int role_id){
		ModelAndView mv=createView("", request, response);
		String roleName="";
		try{
		SmRoleTbl role=SmRoleTbl.find.byId(role_id);
		if(StringUtil.isBlank(role)){
			addResult(mv, false, "未找到该角色");
			return mv;
		}
		if(role.getCreator().getId()!=getLoginUser().getId()){
			addResult(mv, false, "权限不足");
			return mv;
		}
		//查询角色roleModel
		if(role.getModels().size()>0){
			logger.debug("删除角色权限");
			String sql="DELETE FROM sm_role_model_tbl WHERE role_id IN (?)";
			SqlUpdate sqlupdate=ebeanServer.createSqlUpdate(sql);
			sqlupdate.setParameter(1, role_id);
			sqlupdate.execute();
			SystemLogService.SaveLogToDB("sm_role_model_tbl", 3, getLoginUser().getId(), "删除角色权限");
		}
		if(role.getColumn().size()>0){
			logger.debug("删除角色栏目权限");
			String sql="DELETE FROM sm_role_column_tbl WHERE role_id IN (?)";
			SqlUpdate sqlupdate=ebeanServer.createSqlUpdate(sql);
			sqlupdate.setParameter(1, role_id);
			sqlupdate.execute();
			SystemLogService.SaveLogToDB("sm_role_model_tbl", 3, getLoginUser().getId(), "删除角色栏目权限");
		}
		roleName=role.getName();
		role.delete();
		SystemLogService.SaveLogToDB("sm_role_tbl"
				, 3, getLoginUser().getId(), 
				"删除了角色["+roleName+"]");
		addResult(mv, true, "删除角色成功");
		//查询
		}catch(Exception e){
			logger.error("删除角色失败:"+e.getMessage());
			addResult(mv, false, "删除角色失败");
		}
		return mv;
	}

	/**
	 * 根据角色id去修改界面
	 * @param request 
	 * @param response
	 * @param role_id 角色id
	 * @return
	 */
	@RequestMapping("/admin/role/obtain_edit_role")
	public ModelAndView goEditRole(HttpServletRequest request,HttpServletResponse response,int role_id){
		ModelAndView mv=createBgView("bg/role/role_edit", request, response);
		SmRoleTbl role=SmRoleTbl.find.byId(role_id);
		mv.addObject("role",role);
		return mv;
	}
	/**
	 * 更新角色名称
	 * @param response
	 * @param role 角色
	 */
	@RequestMapping("/admin/role/disable_role")
	public void disableRole(HttpServletResponse response,HttpServletRequest request,Integer id){
		ResultJson rj = new ResultJson();
		try {
			if(id!=null&&id>0){
				SmRoleTbl role = SmRoleTbl.find.byId(id);
				role.setState(9);
				role.update();
				rj.setResult(true);
				rj.setResultMsg("操作成功");
			}
		} catch (Exception e) {
			logger.error("抛出异常：", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	
	/**
	 * 去添加角色功能页面
	 * @param request
	 * @param response
	 * @return 返回功能模块页面
	 */
	@RequestMapping("/admin/role/obtain_role_add")
	public ModelAndView authRole1(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("system_setting/security/role_add", request, response);
		Set<SmModelTbl> model_list=new HashSet<>();
		Set<CmColumnTbl> column_list=new HashSet<>();
		//根据用户查询当前角色
		Set<SmRoleTbl> roles=SmUserTbl.find.byId(this.getLoginUser().getId()).getRoles();
		//循环用户角色
		for (SmRoleTbl role : roles) {
			//将角色的功能添加到model_list中
			model_list.addAll(role.getModels());
		}
		//判断是否为网站最高管理员,最高管理员获得全部栏目权限
		if(this.getLoginUser().getType()==4&&this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)){
			 column_list = CmColumnTbl.find.setUseCache(true).where().eq("webSet",getWebSet(request)).eq("isDelete", -1).orderBy("datatime desc").findSet();
		}else{
			//更具当前登录用户获取column
			for (SmRoleTbl role : roles) {
				//将角色的功能添加到model_list中
				column_list.addAll(role.getColumn());
			}
		}
		
		JSONArray jsArray=new JSONArray();
		//使用for循环将数据转为ztree的特定格式
		for (SmModelTbl model : model_list) {
			JSONObject job=new JSONObject();
			job.put("id", model.getId());
			job.put("name", model.getName());
			//判断角色父id是否为空
			if(StringUtil.isBlank(model.getParent())){
				job.put("pId", 0);
			}else{
				job.put("pId", model.getParent().getId());
			}
			jsArray.add(job);
		}
		JSONArray jsArray_c=new JSONArray();
		for (CmColumnTbl column : column_list) {
			JSONObject job=new JSONObject();
			job.put("id", column.getId());
			job.put("name", column.getTitle());
			if(StringUtil.isBlank(column.getParent())){
				job.put("pId", 0);
			}else{
				job.put("pId", column.getParent().getId());
			}
			jsArray_c.add(job);
		}
		/*mv.addObject("roleId",role_id);*/
		mv.addObject("column_zTreeNodes", jsArray_c.toString());
		mv.addObject("zTreeNodes",jsArray.toJSONString());
		return mv;
	}
	
	
	
	/**
	 * 去修改角色功能页面
	 * @param request
	 * @param response
	 * @param role_id 角色id
	 * @return 返回功能模块页面
	 */
	@RequestMapping("/admin/role/auth_role_go2")
	public ModelAndView authRole(HttpServletRequest request,HttpServletResponse response,int role_id){
		ModelAndView mv=createBgView("system_setting/security/role_edit", request, response);
		
		Set<SmModelTbl> model_list=new HashSet<>();
		Set<CmColumnTbl> column_list = new HashSet<>();
		//根据用户查询当前角色
		Set<SmRoleTbl> roles=SmUserTbl.find.byId(this.getLoginUser().getId()).getRoles();
		//循环用户角色
		for (SmRoleTbl role : roles) {
			//将角色的功能添加到model_list中
			model_list.addAll(role.getModels());
		}
		//判断是否为网站最高管理员,最高管理员获得全部栏目权限
		if(this.getLoginUser().getType()==4&&this.getLoginUser().getUsername().equalsIgnoreCase(Const.ADMIN)){
			 column_list = CmColumnTbl.find.setUseCache(true).where().eq("webSet",getWebSet(request)).eq("isDelete", -1).findSet();
		}else{
				//更具当前登录用户获取column
				for (SmRoleTbl role : roles) {
					//将角色的功能添加到model_list中
					column_list.addAll(role.getColumn());
				}
		}		
		/*List<SmModelTbl> model_list= SmModelTbl.find.findList();*/
		//查询当前角色拥有的模块
		SmRoleTbl role=SmRoleTbl.find.byId(role_id);
		Set<SmModelTbl> role_models=SmRoleTbl.find.byId(role_id).getModels();
		Set<CmColumnTbl> role_columns = SmRoleTbl.find.byId(role_id).getColumn();
		System.err.println(role_models.size());
		JSONArray jsArray=new JSONArray();
		//使用for循环将数据转为ztree的特定格式
		for (SmModelTbl model : model_list) {
			JSONObject job=new JSONObject();
			job.put("id", model.getId());
			job.put("name", model.getName());
			//判断角色父id是否为空
			if(StringUtil.isBlank(model.getParent())){
				job.put("pId", 0);
			}else{
				job.put("pId", model.getParent().getId());
			}
			//判断角色功能里面是否有此功能
			if(role_models.contains(model)){
				job.put("checked",true);
			}
			
			jsArray.add(job);
		}
		JSONArray jsArray_c=new JSONArray();
		for (CmColumnTbl column : column_list) {
			JSONObject job=new JSONObject();
			job.put("id", column.getId());
			job.put("name", column.getTitle());
			if(StringUtil.isBlank(column.getParent())){
				job.put("pId", 0);
			}else{
				job.put("pId", column.getParent().getId());
			}
			if(role_columns.contains(column)){
				job.put("checked", true);
			}
			jsArray_c.add(job);
		}
		mv.addObject("column_zTreeNodes", jsArray_c.toJSONString());
		mv.addObject("zTreeNodes",jsArray.toJSONString());
		mv.addObject("role",role);
		return mv;
	}
	/**
	 * 保存角色选择的功能
	 * @param request 
	 * @param response
	 * @param role_id 角色id
	 * @param modelIds 选择的模块ids
	 */
	/*@RequestMapping("/admin/role/save_auth_role")
	public ModelAndView authRole_save(HttpServletRequest request,HttpServletResponse response,int role_id,String modelIds,String columnIds,String name,String keyword,int state){
		ModelAndView mv=createView("", request, response);
		System.err.println(modelIds);
		try{
			ebeanServer.beginTransaction();
			Set<SmModelTbl> model_list=new HashSet();
			//将模块id数组，截取出来
			String [] ids=StringUtils.split(modelIds,",");
			//使用for循环添加到模块的set
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				SmModelTbl model = SmModelTbl.find.byId(Integer.valueOf(id));
				if(model.getParent()!=null){
					model_list.add(model.getParent());
					if(model.getParent().getParent()!=null){
						model_list.add(model.getParent().getParent());
					}
				}
				model_list.add(model);
			}
			String[] columnIdList =columnIds.split(",");
			for(int i=0;i<columnIdList.length;i++){
				int id = Integer.parseInt(columnIdList[i]);
				CmColumnTbl column = CmColumnTbl.find.byId(id);
				getParentColumn(column);
			}
			
			//根据角色Id查询出角色
			SmRoleTbl role=SmRoleTbl.find.byId(role_id);
			role.setName(name);
			role.setKeyword(keyword);
			role.setState(state);
			//将model添加到中间表
			role.setModels(model_list);
			role.setColumn(columns);
			//更新
			role.update();
			ebeanServer.commitTransaction();
			addResult(mv, true, "修改成功!");
			return mv;
		}catch (Exception e) {
			addResult(mv, false, "修改失败!");
		}
		
		return mv;
	}*/
	
} 
