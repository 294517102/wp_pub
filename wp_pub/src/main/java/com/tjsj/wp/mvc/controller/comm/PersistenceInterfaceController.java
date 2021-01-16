package com.tjsj.wp.mvc.controller.comm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.fwk.mvc.shiro.annotation.SessionUser;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.classz.ReflectionUtils;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.m_util.json.FastjsonPropertyPreFilter;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.tools.SystemLogService;
import com.tjsj.wp.orm.entity.IfPiInterfaceTbl;
import com.tjsj.wp.orm.entity.IfPiServiceTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

/**
 * 
 * @author gongdzh
 *
 */

@Controller
public class PersistenceInterfaceController extends WpBaseController {
	
	private static Logger logger = LoggerFactory.getLogger(PersistenceInterfaceController.class);
	
	@Autowired
	private EbeanServer ebeanServer;
	@Autowired
	private DefSet  defSet;
	
	@RequestMapping("/admin/obtain_pi_service")
	public ModelAndView obtain_pi_service(HttpServletRequest request, 
										  HttpServletResponse response,
										  IfPiServiceTbl ifPiService,
										  PageParameter page,
										  String url){
		ModelAndView mv = null;
		if(StringUtils.isNotBlank(url)){
			mv = createBgView(url, request, response);
		}else{
			mv = createBgView("tools/interface/service_list", request, response);
		}
		try {
			PagedList<BaseEntity> pageList = ifPiService.getLikeExpressionList().eq("isDelete", 0).setFirstRow(page.getFirstRow())
											.setMaxRows(page.getMaxRows()).orderBy("id desc").findPagedList();
			pagePrint(request, mv, pageList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/admin/save_pi_service")
	public void save_pi_service(HttpServletRequest request, 
			  					HttpServletResponse response,
			  					IfPiServiceTbl ifPiService,
			  					@SessionUser SmUserTbl user){
		ResultJson rj = new ResultJson();
		
		try {
			
			if(ifPiService.getId()!=null&&ifPiService.getId()>0){
				IfPiServiceTbl dIfPiService = IfPiServiceTbl.find.byId(ifPiService.getId());
				if(ifPiService.getStype()==2&&!ifPiService.getEntityName().equals(dIfPiService.getEntityName())){
					Class classType = (Class) defSet.getClassType().get(ifPiService.getEntityName());					
					ifPiService.setParameter(JSON.toJSONString(ReflectionUtils.getFieldNames(classType)));
				}
				ClassUtil.copyObjectByPropertyValues(dIfPiService, ifPiService);
				dIfPiService.update();
				rj.setiCode(1);
				rj.setResult(true);
				rj.setResultMsg("修改成功");
				outJson(response, rj);
				//SystemLogService.SaveLogToDB(tableName, type, oPerator_id, remark)
				return ;
			}
			
			if(ifPiService.getStype()==2){
				int counter = IfPiServiceTbl.find.where().eq("name", ifPiService.getName())
													.eq("entityName", ifPiService.getEntityName())
													.findCount();
				if(counter>0){
					throw new ParameterException("名称和实体名称不能重复");
				}			
			}
			if(StringUtils.isBlank(ifPiService.getParameter())){
				if(ifPiService.getStype()==2){
					Class classType = (Class) defSet.getClassType().get(ifPiService.getEntityName());					
					ifPiService.setParameter(JSON.toJSONString(ReflectionUtils.getFieldNames(classType)));
				}
				
			}

			ifPiService.setWebSet((SmWebSetTbl) getSessionValue(Const.SESSION_WEB_SET));
			ifPiService.setUser(user);
			ifPiService.save();
			if(StringUtils.isBlank(ifPiService.getResultName())){
				ifPiService.setResultName("r"+ifPiService.getId());
				ifPiService.update();
			}
						
			rj.setiCode(1);
			rj.setResult(true);
			rj.setResultMsg("保存成功");
		}catch (Exception e) {
			// TODO: handle exception
			rj.setResult(false);
			rj.setiCode(2);
			rj.setResultMsg(e.getMessage());
			logger.error("exception throws :", e);
		}
		outJson(response, rj);
	}
	
	@RequestMapping("/admin/obtain_pi_interface")
	public ModelAndView obtain_pi_interface(HttpServletRequest request, 
										  HttpServletResponse response,
										  IfPiInterfaceTbl ifPiInterfaceTbl,
										  PageParameter page,
										  String url){
		ModelAndView mv = null;
		if(StringUtils.isNotBlank(url)){
			mv = createBgView(url, request, response);
		}else{
			mv = createBgView("tools/interface/interface_list", request, response);
		}
		try {
			PagedList<BaseEntity> pageList = ifPiInterfaceTbl.getLikeExpressionList().eq("isDelete", 0).setFirstRow(page.getFirstRow())
											.setMaxRows(page.getMaxRows()).orderBy("id desc").findPagedList();
			pagePrint(request, mv, pageList);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/admin/obtain_interface_by_keyword")
	public void obtain_interface_by_keyword(HttpServletRequest request, 
										  HttpServletResponse response,
										  String  keyword){
		ResultJson rj = new ResultJson();
		try {
			if(StringUtils.isBlank(keyword)){
				throw new ParameterException("关键字不能为空");
			}
		List<IfPiServiceTbl>	piServices = IfPiServiceTbl.find.where().or().like("name", "%"+keyword+"%")
												.like("entityName",  "%"+keyword+"%")
												.like("sql", "%"+keyword+"%")
												.like("user.username", "%"+keyword+"%")
												.endJunction().orderBy("id desc").setMaxRows(Const.PAGE_SIZE).findList();
		for (IfPiServiceTbl ps : piServices) {
			if(ps.getStype()==2){
				ps.setSql(ps.getEntityName());
			}
		}
		rj.setResult(true);
//		rj.setResultData(JSON.toJSONString(piServices,new FastjsonPropertyPreFilter()));
//		rj.setData(JSON.toJSONString(piServices,new FastjsonPropertyPreFilter()));
		rj.setData(JSON.toJSONString(piServices,new FastjsonPropertyPreFilter()));
		} catch (Exception e) {
			logger.error("抛出异常：", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	
	
	@RequestMapping("/admin/save_pi_interface")
	public void save_pi_interface(HttpServletRequest request, 
								  HttpServletResponse response,
								  IfPiInterfaceTbl ifPiInterfaceTbl,
								  @RequestParam(required=false,name="serviceList[]") List<Integer> serviceList,
				  					@SessionUser SmUserTbl user){
		ResultJson rj = new ResultJson();
		try {
			//更新
			if(ifPiInterfaceTbl.getId()>0){
				IfPiInterfaceTbl dIfPiInterface = IfPiInterfaceTbl.find.byId(ifPiInterfaceTbl.getId());
				ClassUtil.copyObjectByPropertyValues(dIfPiInterface, ifPiInterfaceTbl);
				System.err.println(serviceList);
				if(serviceList!=null){
					List<IfPiServiceTbl> dServices = Lists.newArrayList();
					for (Integer service : serviceList) {
						dServices.add(IfPiServiceTbl.find.byId(service));
					}
					dIfPiInterface.setServices(dServices);;
				}				
				dIfPiInterface.save();
				rj.setiCode(1);
				rj.setResult(true);
				rj.setResultMsg("修改成功");
				outJson(response, rj);
				return ;
			}
			if(StringUtils.isBlank(ifPiInterfaceTbl.getName())){
				throw new ParameterException("名称不能为空");
			}
			if(StringUtils.isBlank(ifPiInterfaceTbl.getIid())){
				throw new ParameterException("识别码不能为空");
			}
			if(serviceList==null||serviceList.size()<=0){
				throw new ParameterException("至少添加一个服务");
			}
			for (Integer service : serviceList) {
				ifPiInterfaceTbl.getServices().add(IfPiServiceTbl.find.byId(service));
			}
			if(IfPiInterfaceTbl.find.where().eq("name", ifPiInterfaceTbl.getName()).findCount()>0){
				throw new ParameterException("名称已存在");
			}
			if(ifPiInterfaceTbl.getStatus()==0){
				ifPiInterfaceTbl.setStatus(9);
			}
			ifPiInterfaceTbl.setWebset((SmWebSetTbl) getSessionValue(Const.SESSION_WEB_SET));
			ifPiInterfaceTbl.setUser(user);
			ifPiInterfaceTbl.save();
			rj.setiCode(1);
			rj.setResult(true);
			rj.setResultMsg("保存成功");
		} catch (Exception e) {
			logger.error("抛出异常：", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
}
