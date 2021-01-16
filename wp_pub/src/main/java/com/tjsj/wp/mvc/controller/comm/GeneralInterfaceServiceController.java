package com.tjsj.wp.mvc.controller.comm;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.alibaba.fastjson.JSON;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
import com.google.common.collect.Lists;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.fwk.mvc.shiro.annotation.SessionUser;
import com.tjsj.m_util.classz.BeanToMapUtil;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.classz.ReflectionUtils;
import com.tjsj.m_util.codec.MD5;
import com.tjsj.m_util.comm.CommUtil;
import com.tjsj.m_util.date.DateUtil;
import com.tjsj.m_util.entity.BaseEntity;
import com.tjsj.m_util.exception.DataErrorException;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.IfPiInterfaceTbl;
import com.tjsj.wp.orm.entity.IfPiServiceTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

@Controller
public class GeneralInterfaceServiceController extends WpBaseController {

	private static Logger logger = LoggerFactory.getLogger(GeneralInterfaceServiceController.class);
	
	@Autowired
	private DefSet  defSet;
	
	@Autowired
	private EbeanServer  ebeanServer;

	@RequestMapping("/general_service")
	public ModelAndView general_service(HttpServletRequest request, 
										HttpServletResponse response, 
										String _id, 
										String _url,
										PageParameter _page, 
										Integer _size, 
										Integer _ttype, 
										Integer _qtype,
										String _orderby,
										String _sKey,
										String _sObject,
										Integer _refresh,
										@SessionUser SmUserTbl user) {
		ModelAndView mv = null;
		if (_ttype == null || _ttype == 1) {
			mv = createView(_url, request, response);
		} else {
			mv = createBgView(_url, request, response);
		}
		try {
			if (StringUtils.isBlank(_id)) {
				throw new ParameterException("请求非法");
			}
			if(_size!=null&&_size>0){
				_page.setPageSize(_size); 			  
			}else{
				_size = Const.PAGE_SIZE;
			}
			/*if(_size==null||_size<=0){
			}*/
			IfPiInterfaceTbl piInterface = IfPiInterfaceTbl.find.setUseQueryCache(true).where().eq("iid", _id).findUnique();
			if (piInterface == null) {
				throw new DataErrorException("请求无效");
			}
			if (piInterface.getIsDelete() != 0) {
				throw new DataErrorException("目标已删除");
			}
			if (piInterface.getStatus() == 9) {
				throw new DataErrorException("目标已停用");
			}
			if (piInterface.getDeadline() != null && piInterface.getDeadline().getTime() < (new Date()).getTime()) {
				throw new DataErrorException("超出有效期");
			}
			// 实体查询
			SmWebSetTbl webSet = (SmWebSetTbl) getSessionValue(Const.SESSION_WEB_SET);
			
			if (piInterface.getDomainScope() == 2
					&& (webSet == null || piInterface.getWebset().getId() != webSet.getId())) {
				throw new DataErrorException("无权访问目标");
			}

			List<IfPiServiceTbl> services = piInterface.getServices();

			for (IfPiServiceTbl service : services) {
				// 实体
				@SuppressWarnings("unchecked")
				Map<String,Object>  parMap = CommUtil.getParameterMap(request);
				//校验session中对象key值
				if(StringUtils.isNotBlank(_sKey)){
					Object  sb = request.getSession().getAttribute(_sKey);

					if(sb!=null){
						Object id = ReflectionUtils.getFieldValue(sb, "id");
						if(id!=null){
							parMap.put("id", id);
						}
					}
				}
				
				if (service.getStype() == 2) {	
				    @SuppressWarnings("rawtypes")
				    Class classz = (Class)defSet.getClassType().get(service.getEntityName());
					BaseEntity  baseEntity =   (BaseEntity) BeanToMapUtil.convertMap(classz, parMap);
				    if(baseEntity==null){
				    	baseEntity =  (BaseEntity) classz.newInstance();
				    }
				   
					//以session对象作为查询条件
					if(StringUtils.isNotBlank(_sObject)&&request.getSession().getAttribute(_sObject)!=null){
						ReflectionUtils.setFieldValue(baseEntity, _sKey==null?_sObject:_sKey, request.getSession().getAttribute(_sObject));
					}
					ClassUtil.setSubobjectThreeLevel(parMap, (Object)baseEntity);
				   //查询
				    if(service.getOtype()==1){
				    	 query_by_page( 	
				    			 	request,
				    				mv,
				    				baseEntity,
				    				parMap,
				    				_page.getFirstRow(),
				    				_size,
				    				service,
				    				_qtype,
				    				_orderby);
				    	 String jf = (String)mv.getModel().get("_jf");
				    	 if(StringUtils.isBlank(jf)){
				    		 jf = service.getCascade();
				    	 }else{
				    		 jf = jf+"|"+service.getCascade();
				    	 }
				    	 mv.addObject("_jf", jf);
				    	 
				    	 continue;
					}
				    //修改,修改类操作，一个接口只能有一个服务
				   if(service.getOtype()==2){
					   //id大于0为修改
						int id = (int) ReflectionUtils.getFieldValue(baseEntity, "id");
						if(id > 0){
							
							BaseEntity dBe = (BaseEntity)baseEntity.db().find(classz, id); 
							ClassUtil.copyObjectByPropertyValues(dBe, baseEntity);
							dBe.setUserDefinedProperty();
							dBe.update();
							refreshSession(request,_refresh);
							refreshEntityByIid(_id);
							addResult(mv, true, "操作成功");
							return mv;
						}else{
							//新增数据，防止恶意调用产生垃圾数据，加密处理
							String sign = request.getParameter("_sign");
							if(StringUtils.isNotBlank(sign)&&sign.equalsIgnoreCase(MD5.md5(user.getUsername()+user.getId()))){
								baseEntity.setUserDefinedProperty();
								baseEntity.save();
								refreshSession(request,_refresh);
								refreshEntityByIid(_id);
								mv.addObject(service.getResultName(), baseEntity);
								addResult(mv, true, "保存成功");
							}else{
								addResult(mv, false, "无效请求");
							}							
							return mv;
						}
				   }
				    
				}
				if (service.getStype() == 1){
					//查询类
					List<String>  params = JSON.parseArray(service.getParameter(), String.class);
					if(service.getOtype()==1){
						SqlQuery  sqlQuery = ebeanServer.createSqlQuery(service.getSql());					
						for (String p : params) {
							sqlQuery.setParameter(p, parMap.get(p));
						}
						List<SqlRow>  sqlRows = sqlQuery.setFirstRow(_page.getFirstRow())
											.setMaxRows(_page.getMaxRows())
											.findList();
						mv.addObject(service.getResultName(), sqlRows);
					}
					if(service.getOtype()==2){
						SqlUpdate    sqlUpdate = ebeanServer.createSqlUpdate(service.getSql());
						for (String p : params) {
							sqlUpdate.setParameter(p, parMap.get(p));
						}
						int affectRows = sqlUpdate.execute();
						mv.addObject(service.getResultName(), affectRows);
					}
				}
			}
			addResult(mv, true, "成功");
		} catch (Exception e) {
			logger.error("抛出异常：", e);
			addResult(mv, false, e.getMessage());
		}
		return mv;
	}

	private void refreshSession(HttpServletRequest request,Integer refresh){
		
		if(refresh==null||refresh<0){
			return ;
		}
		switch (refresh) {
		//刷新webSet
			case 1:
				SmWebSetTbl webSet = (SmWebSetTbl)request.getSession().getAttribute(Const.SESSION_WEB_SET);
				if(webSet!=null){					
					 webSet = SmWebSetTbl.find.byId(webSet.getId());
					 request.getSession().setAttribute(Const.SESSION_WEB_SET,webSet);
				}
				break;
		//刷新用户信息user
			case 2:
				SmUserTbl user = (SmUserTbl)request.getSession().getAttribute(Const.SESSION_USER);
				if(user!=null){					
					 user = SmUserTbl.find.byId(user.getId());
					 request.getSession().setAttribute(Const.SESSION_USER,user);
				}
				break;	
			default:
				break;
		}
	}
	
	@SuppressWarnings("unused")
	private void query_by_page(	HttpServletRequest request, 
								ModelAndView mv, BaseEntity be, 
								Map<String,Object> parMap, 
								Integer firstRow,
								Integer size, 
								IfPiServiceTbl service, 
								Integer _qtype,
								String orderBy) throws Exception {
		
		if (firstRow == null){
			firstRow = 0;
		}
		
		//主键查询的处理方式
		//单条，参数中有id按单条处理
		if(parMap.get("id")!=null&&
				StringUtils.isNotBlank(parMap.get("id").toString())&&
				Integer.parseInt(parMap.get("id").toString()) >0){
			BaseEntity baseEntity = (BaseEntity) be.getFind().byId(parMap.get("id"));
			mv.addObject(service.getResultName(), baseEntity);

			mv.addObject("rowCount", 1);
			return;
		}
		
		
		
		if(_qtype==null){
			_qtype = 1;
		}
		ExpressionList<BaseEntity> eps = null;
		
		switch (_qtype) {
			case 1:			
			case 2:			
			case 3:
			case 4:
			    eps = be.getExpressionList(_qtype);
				break;
			default:
				eps = be.getEqExpressionList();
				break;
		}
		// 扩展性查询
		
		Set<Entry<String, Object>> entitis = parMap.entrySet();
		for (Entry<String, Object> e : entitis) {
			String key = e.getKey();
			if (StringUtils.isBlank(key) || e.getValue() == null) {
				continue;
			}
			// 处理时间范围查询,start_* 开始时间， end_* 结束时间
			if (key.startsWith("start_")) {
				String p = StringUtils.substringAfter(key, "start_");
				Object value = e.getValue();
				Class<?> classz = ReflectionUtils.getMethodReturnType(be.getClass(), p);
				Object sp = null;
				if (classz.equals(Date.class)) {
					sp = DateUtil.stringToDate(value.toString());
				} else {
					sp = value;
				}
				eps.ge(p, sp);
				continue;
			}
			if (key.startsWith("end_")) {
				String p = StringUtils.substringAfter(key, "end_");
				Object value = e.getValue();
				Class<?> classz = ReflectionUtils.getMethodReturnType(be.getClass(), p);
				Object sp = null;
				if (classz.equals(Date.class)) {
					sp = DateUtil.stringToDate(value.toString());
				} else {
					sp = value;
				}
				eps.le(p, sp);
				continue;
			}
			// 范围查询，in条件,in_* 范围列表
			if (key.startsWith("in_")) {
				String p = StringUtils.substringAfter(key, "in_");
				Class<?> classz = ReflectionUtils.getMethodReturnType(be.getClass(), p);
				Object value = e.getValue();
				String[] vs = value.toString().split(",");
				List<Object> vlist = Lists.newArrayList();
				for (String v : vs) {
					if (classz.equals(int.class) || classz.equals(Integer.class)) {
						vlist.add(Integer.parseInt(v));
					}
					if (classz.equals(String.class)) {
						vlist.add(v);
					}
				}
				eps.in(p, vlist);
				continue;
			}
		}


		if (StringUtils.isNotBlank(orderBy)) {
			eps.orderBy(orderBy);
		}
		
		//单条和多条的处理方式		
		//非主键查询都可能多条，按分页查询处理
		PagedList<BaseEntity> bes = eps.setFirstRow(firstRow)
				.setMaxRows(size).findPagedList();
		pagePrint(request, mv,service.getResultName() ,bes);
		return;
	}
	
	
	private void refreshEntityByIid(String iid){
		if(StringUtils.isBlank(iid)){
			return ;
		}
		Map<Class<?>, String> methodMap = defSet.getDynamicMethod(iid);
		if(methodMap==null||methodMap.entrySet().isEmpty()){
			return ;
		}
		Entry<Class<?>, String> methodEntry = methodMap.entrySet().iterator().next();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(methodEntry.getKey());
			MethodDescriptor[] methodDescriptor = beanInfo.getMethodDescriptors();
			for (MethodDescriptor m : methodDescriptor) {
				if(m.getName().equals(methodEntry.getValue())){
					m.getMethod().invoke(null, new Object[]{});
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("抛出异常：", e);
		}
	}
	
}
