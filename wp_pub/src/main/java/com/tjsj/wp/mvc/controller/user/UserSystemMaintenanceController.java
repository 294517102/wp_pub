package com.tjsj.wp.mvc.controller.user;



import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.avaje.ebeaninternal.server.util.Md5;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.fwk.mvc.shiro.annotation.SessionUser;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.codec.MD5;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.mvc.controller.service.SmsService;
import com.tjsj.wp.orm.entity.SmAccessoryTbl;
import com.tjsj.wp.orm.entity.SmRoleTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.tools.BnRuleUtil;

/**
 * 管理员类，主要是对管理员的赠删改查
 * @author zp
 *
 */
@Controller
public class UserSystemMaintenanceController extends WpBaseController{
	//日志文件
	private static Logger logger = Logger.getLogger(UserSystemMaintenanceController.class);
	@Autowired
	private EbeanServer ebeanServer;
	@Autowired
	public SmsService smsService;
	
	/**
	 * 模糊查询用户信息
	 * @param request 请求
	 * @param response 响应
	 * @param username 名字
	 * @return 返回地址
	 */
	@RequestMapping("/admin/user/obtain_like_user_list")
	public ModelAndView obtainLikeUser(HttpServletRequest request,HttpServletResponse response,String username,String lastLoginStart,String lastLoginEnd,Integer pageIndex){
		ModelAndView mv=createBgView("bg/user/system_user_list", request, response);
		if(username!=null||lastLoginStart!=null&&lastLoginEnd!=null){
			if(pageIndex==null) pageIndex=0;
			ExpressionList<SmUserTbl> v = SmUserTbl.find.where().eq("parent.id", this.getLoginUser().getId());
			if(StringUtils.isNotBlank(username)){
				v.like("username", "%"+username+"%");
			}
			if(StringUtils.isNoneBlank(lastLoginStart)&&StringUtils.isNoneBlank(lastLoginEnd)){
				v.between("last_login", lastLoginStart, lastLoginEnd);
			}
		}
		
		return mv;
	}
	/**
	 * 新增用户
	 * @param request 请求
	 * @param response 响应
	 * @return 返回地址
	 */
	@RequestMapping("/admin/user/save_user")
	public ModelAndView  saveUser(HttpServletRequest request,
									HttpServletResponse response,
									SmUserTbl _user,
									String role_ids,
									String password,
									String thumbnails,
									@SessionUser SmUserTbl user){
		 
		ModelAndView mv = createView("", request, response);
		try{
			if(StringUtils.isNotBlank(thumbnails)){
				_user.setThumbnail(SmAccessoryTbl.find.byId(Integer.parseInt(thumbnails)));
			}
			// 校验密码
			if(StringUtils.isNotBlank(password)) {
				if (!BnRuleUtil.isStrongPassword(password)) {
					throw new ParameterException("密码必填字母数字及特殊字符,且以字母开头,8-16位");
				}
			}
			//对密码进行MD5加密
			//修改
			if(_user.getId()>0){
				
				SmUserTbl dUser = SmUserTbl.find.byId(_user.getId());
				/*if(dUser.getParent().getId()!=_user.getId()&&
						dUser.getId()!=_user.getId()){
					addResult(mv, false, "权限不够");
					logger.error("权限不够");
					return mv;
				}*/
				if(StringUtils.isBlank(password)){
					_user.setPassword(null);	
				}
				ClassUtil.copyObjectByPropertyValues(dUser, _user);
				if(StringUtils.isNotBlank(role_ids)){
					SmRoleTbl role=SmRoleTbl.find.byId(Integer.valueOf(role_ids));
					Set<SmRoleTbl> roles=new HashSet<>();
					roles.add(role);
					dUser.setRoles(roles);
				}				
				dUser.setUserDefinedProperty();
				
				if(StringUtils.isNotBlank(password)){
					dUser.setPassword(MD5.md5(password));
				}
				dUser.update();
				//修改当前用户要刷新session
				if(dUser.getId()==user.getId()){
					request.setAttribute(Const.SESSION_USER, dUser);					
				}
			}else{
				//新增
				if(StringUtils.isBlank(password)){
					throw new ParameterException("密码不能为空");
				}
				if(StringUtils.isBlank(_user.getUsername())){
					throw new ParameterException("用户名不能为空");
				}
				int counter = SmUserTbl.find.where().eq("username", _user.getUsername()).findCount();
				if(counter>0){
					throw new ParameterException("'"+_user.getUsername()+"'已存在");
					
				}
				if(StringUtils.isBlank(role_ids)){
					throw new ParameterException("角色不能为空");
				}
				_user.setPassword(MD5.md5(password));
				SmRoleTbl role=SmRoleTbl.find.byId(Integer.valueOf(role_ids));
				Set<SmRoleTbl> roles=new HashSet<>();
				roles.add(role);
				_user.setRoles(roles);
				_user.setParent(this.getLoginUser());
				_user.setWebSet(getWebSet(request));
				_user.setType(3);
				//1-未锁定，2-锁定
				_user.setIsLocked(1); 
				_user.save();
				
			}
			
		}catch(Exception e){
			addResult(mv, false, e.getMessage());
			logger.error("exception throws:",e);
			return mv;
			
		}
		addResult(mv, true, "操作成功");
		logger.debug("操作成功");
			return mv;
	}
	/**
	 * 去修改页面
	 * @param request 请求
	 * @param response 响应
	 * @return 返回修改页面
	 */
	@RequestMapping("/admin/user/obtain_edit_user")
	public ModelAndView goEditUser(HttpServletRequest request,HttpServletResponse response,int user_id){
		ModelAndView mv=createBgView("system_setting/security/system_user_add", request, response);
		SmUserTbl user_edit=SmUserTbl.find.byId(user_id);

		mv.addObject("user_edit", user_edit);
		return mv;
	}
	/*
	 * 根据id删除用户,主要是更改用户状态为删除状态,更具judeg状态 判断禁用还是删除
	 * @param user_id 用户id
	 */
	@RequestMapping("/admin/user/delete_user")
	@ResponseBody
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response,int id,int judge){
		ModelAndView mv = createView("", request, response);
		try{
			SmUserTbl delete_user=SmUserTbl.find.byId(id);
			if(delete_user==null){
				addResult(mv, false, "删除失败,用户不存在!");
				logger.error("用户不存在");
				return mv;
			}
			if(delete_user.getParent().getId()!=this.getLoginUser().getId()){
				addResult(mv, false, "删除失败,权限不够!");
				logger.error("权限不够!");
				return mv;
			}
			if(judge==1){
				delete_user.setIsDelete(1);
				delete_user.update();
				addResult(mv, true, "删除成功!");
			}else{
				if(delete_user.getIsLocked()==0)delete_user.setIsLocked(1);
				else delete_user.setIsLocked(0);
				delete_user.update();
				addResult(mv, true, "操作成功!");
			}
			
			logger.debug("删除用户成功");
		}catch(Exception e){
			addResult(mv, false, "删除失败!");
			logger.error("删除失败"+e.getMessage());
		}
		
		return mv;
	}

	/**
	 * 根据ID批量删除
	 * @author 李波
	 * @param request
	 * @param response
	 * @param spCodesTemp
	 */

	@RequestMapping("/admin/user/deleteAll_user")
	@ResponseBody
	public ModelAndView deleteAll(HttpServletRequest request,HttpServletResponse response,String spCodesTemp,int judge){
		System.out.println("进入方法并获取到参数"+spCodesTemp);
		ModelAndView mv = createView("", request, response);
		if(spCodesTemp==null){
			addResult(mv, false, "没有被选中的用户!");
			return mv;
		}
		try {
		//接收参数","分割数据
		String [] spCodesTemps =spCodesTemp.split(",");
		//遍历spCodesTemps
		ebeanServer.beginTransaction();
		for (int i = 0; i < spCodesTemps.length; i++) {
				//String[]转int[]
				int id = Integer.parseInt(spCodesTemps[i]);
					SmUserTbl delete_users = SmUserTbl.find.byId(id);
					//判断为空
					if(delete_users == null){
						addResult(mv, false, "删除失败,没有该用户!");
						logger.error("删除失败");	
					}
						//权限控制
					if(delete_users.getParent().getId()!=this.getLoginUser().getId()){
						addResult(mv, false, "删除失败,你没有权限!");
						logger.error("您没有该权限");
					}
					//调用持久层
					if(judge==1){
						delete_users.setIsDelete(1);
						delete_users.update();
						addResult(mv, true, "删除成功!");
					}else{
						if(delete_users.getIsLocked()==0)delete_users.setIsLocked(1);
						else delete_users.setIsLocked(0);
						delete_users.update();
						addResult(mv, true, "操作成功!");
					}	
			}
		ebeanServer.commitTransaction();
		} catch (Exception e) {
			addResult(mv, false, "删除失败!");
			logger.error("删除失败"+e.getMessage());
			return mv;
		}	
		return mv;
	}
	
	@RequestMapping("/admin/user/excel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,Integer id) throws Exception{
		System.out.println(id);
		List<SmUserTbl> list = SmUserTbl.find.where().eq("parent.id", this.getLoginUser().getId()).eq("isDelete", 0).findList();
		HSSFWorkbook wb = export(list);
		 response.setContentType("application/vnd.ms-excel");    
	     response.setHeader("Content-disposition", "attachment;filename=系统用户.xls");    
	     OutputStream ouputStream = response.getOutputStream();    
	     wb.write(ouputStream);    
	     ouputStream.flush();    
	     ouputStream.close();    
	}
	 String[] excelHeader = { "序号", "用户名", "姓名","角色","电话","邮箱","最近登录","上次登录IP","状态"};//标题
	 public HSSFWorkbook export(List<SmUserTbl> list) {    
	        HSSFWorkbook wb = new HSSFWorkbook();    
	        HSSFSheet sheet = wb.createSheet("Campaign");    
	        HSSFRow row = sheet.createRow((int) 0);
	        HSSFCellStyle style = wb.createCellStyle();    
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        //设置表头
	        for (int i = 0; i < excelHeader.length; i++) {
	            HSSFCell cell = row.createCell(i); 
	            cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style);
	            sheet.autoSizeColumn(i);
	        }   
	        //设置宽度
	        int[] excelHeaderWidth = {150, 120, 100, 100, 100, 100, 100, 100, 100,  
	        	    100, 100, 120, 120, 120, 120, 120, 120, 150, 150, 150, 120,  
	        	    120, 150, 150, 120, 150 };
	        for (int i = 0; i < excelHeaderWidth.length; i++) {  
	            sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]);  
	        } 
	        for (int i = 0; i < excelHeader.length; i++) {  
	            HSSFCell cell = row.createCell(i);  
	            cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style);  
	        }
	        row = sheet.createRow((int) 1);  
	        for (int i = 0; i < list.size(); i++) {    
	            row = sheet.createRow(i + 1);    
	            SmUserTbl ous = list.get(i);    
	            row.createCell(0).setCellValue(ous.getId());
	            
	            if(ous.getUsername() !=null){
	            	row.createCell(1).setCellValue(ous.getUsername());
	            }else{
	            	row.createCell(1).setCellValue("无");
	            }
	            if(ous.getRealName() !=null){
	            	 row.createCell(2).setCellValue(ous.getRealName());
	            }else{
	            	row.createCell(2).setCellValue("无");
	            }
	            if(ous.getRoles() !=null){
	            	row.createCell(3).setCellValue(ous.getRoles().toString());
	            }else{
	            	row.createCell(3).setCellValue("无");
	            }
	            if(ous.getPhone() !=null){
	            	 row.createCell(4).setCellValue(ous.getPhone());
	            }else{
	            	row.createCell(4).setCellValue("无");
	            }
	            if(ous.getEmail() !=null){
	            	 row.createCell(5).setCellValue(ous.getEmail());
	            }else{
	            	row.createCell(5).setCellValue("无");
	            }
	            if(ous.getLastLogin() !=null){
	            	 row.createCell(6).setCellValue(ous.getLastLogin());
	            }else{
	            	row.createCell(6).setCellValue("无");
	            }
	            if(ous.getLast_ip_address() !=null){
	            	 row.createCell(7).setCellValue(ous.getLast_ip_address());
	            }else{
	            	row.createCell(7).setCellValue("无");
	            }
	        }
	        return wb;    
	    }
	
		
	 /**
	  * 跳转到会员列表
	  * @param request
	  * @param response
	  * @param pageIndex
	  * @param pageSize
	  * @return
	  */
	 @RequestMapping("admin/to_vip_list")
	public ModelAndView to_vip_list(HttpServletRequest request,HttpServletResponse response,PageParameter page,String name){
		ModelAndView mv=createBgView("user/vip_user", request, response);
		PagedList<SmUserTbl> vipList=null;
		if(StringUtil.isBlank(name)){
			vipList=SmUserTbl.find.where().eq("webSet.id", getLoginUser().getWebSet().getId())
			.eq("is_delete", -1).in("type",1).setFirstRow(page.getFirstRow())
			.setMaxRows(page.getMaxRows()).findPagedList();
		}else{
			vipList=SmUserTbl.find.where().eq("webSet.id", getLoginUser().getWebSet().getId())
					.eq("is_delete", -1).contains("username", name).in("type",1).setFirstRow(page.getFirstRow())
					.setMaxRows(page.getMaxRows()).findPagedList();
		}
		pagePrint(request, mv, vipList);
		return mv;
	}
	 
	 /**
	  * 锁定或解锁会员
	  * @param request
	  * @param response
	  * @param id
	  * @param isLocked
	  * @return
	  */
	 @RequestMapping("admin/updateVipUserisLocked")
	 public ModelAndView updateVipUserIsSocket(HttpServletRequest request,HttpServletResponse response,Integer id,Integer isLocked,Integer webset){
		 ModelAndView mv=createView("", request, response);
		 if(StringUtil.isBlank(id)||StringUtil.isBlank(isLocked)){
			 addResult(mv, false, "参数错误");
			 return mv;
		 }
		 SmUserTbl user=SmUserTbl.find.byId(id);
		 SmWebSetTbl webSet = SmWebSetTbl.find.byId(webset);
		 try {
			 //锁定
			 if(isLocked==-1){
				 user.setIsLocked(1);
				 user.update();
				 addResult(mv, true, "操作成功，用户已通过审核!");
			 }
			 if(isLocked==1){
				 user.setIsLocked(-1);
				 user.update();
				 addResult(mv, true, "操作成功，用户需要重新审核!");
			 }
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, "系统错误，请稍后再试");
		}
		 return mv;
	 }
	 	/**
	 	 * 前台用户注册
	 	 * @param request
	 	 * @param response
	 	 * @param registerUser
	 	 * @param password
	 	 * @return
	 	 */
		@RequestMapping("register_user")
		public ModelAndView  register_user(HttpServletRequest request,HttpServletResponse response,SmUserTbl registerUser,Integer thumbnail_id,Integer webSet_id,Integer parent_id,Integer role_id){
			ModelAndView mv = createView("", request, response);
			try{
				if(registerUser.getId()!=0){
					throw new ParameterException("用户已存在");	
				}
				if(webSet_id==null || webSet_id==0){
				throw new ParameterException("注册用户所属站点错误");	
				}
				SmWebSetTbl cset=SmWebSetTbl.find.byId(webSet_id);
				if(cset==null){
					throw new ParameterException("注册用户所属站点错误");	
				}
				if(StringUtils.isBlank( registerUser.getPassword())){
					throw new ParameterException("注册用户密码不能为空");		
				}
				if(StringUtils.isBlank(registerUser.getUsername())){
					throw new ParameterException("用户名不能为空");
				}
				int counter = SmUserTbl.find.where().eq("webSet.id", webSet_id).eq("username", registerUser.getUsername()).findCount();
				if(counter>0){
					throw new ParameterException(registerUser.getUsername()+"已存在");
					
				}
				if(thumbnail_id!=null && thumbnail_id!=0){
					SmAccessoryTbl thumb=SmAccessoryTbl.find.byId(thumbnail_id);
					registerUser.setThumbnail(thumb);
				}
				if(role_id!=null && role_id!=0){
					SmRoleTbl role=SmRoleTbl.find.byId(role_id);
					if(role!=null){
						registerUser.getRoles().add(role);
					}
				}
				if(parent_id!=null && parent_id!=0){
				SmUserTbl par=SmUserTbl.find.byId(parent_id);
				registerUser.setParent(par);
				}
			registerUser.setWebSet(cset);
			registerUser.setPassword(MD5.md5(registerUser.getPassword()));
			registerUser.save();
			addResult(mv, true, "操作成功");
			logger.debug("操作成功");
			}catch(Exception e){
				e.printStackTrace();
				addResult(mv, false, e.getMessage());
				logger.error("exception throws:",e);
			}
		return mv;
		}	
		/**
		 * 忘记密码发送短信
		 * @param request
		 * @param response
		 * @param username
		 * @param piccode
		 * @return
		 */
		@RequestMapping("forget_password_validate")
		public ModelAndView  forget_password_validate(HttpServletRequest request,HttpServletResponse response,String username,String piccode){
			ModelAndView mv = createView("", request, response);
			try{
				if(StringUtils.isBlank(username)){
					throw new ParameterException("用户名错误");		
				}
				if(StringUtils.isBlank(piccode)){
					throw new ParameterException("验证码错误");		
				}
				if(StringUtils.isBlank(piccode)){
					throw new ParameterException("验证码错误");		
				}
				 HttpSession session = request.getSession(false);
//				 验证图片验证码
				 String scode=(String) session.getAttribute("verify_code");
				 if(scode==null){
					 throw new ParameterException("验证码错误");		 
				 }
				 if(!scode.equalsIgnoreCase(piccode)){
					 throw new ParameterException("验证码错误");		 
				 }
				int counter = SmUserTbl.find.where().eq("webSet", getWebSet(request)).eq("username", username).findCount();
				if(counter==0){
					throw new ParameterException("用户不存在");
				}
				SmUserTbl foruser=SmUserTbl.find.where().eq("webSet", getWebSet(request)).eq("username", username).findUnique();
				if(foruser==null){
					throw new ParameterException("用户不存在");
				}
				String checkCode =  com.tjsj.m_util.codec.RandomUtil.generateNumber(6);
					 if(smsService.sendMessage("您的验证码是%s。如非本人操作，请忽略本短信",foruser.getPhone(),request)) {
					request.getSession(false).setAttribute(username, checkCode);
						 addResult(mv, true, "发送成功");
					 }else {
						 addResult(mv, false, "发送失败");
					 }
			logger.debug("操作成功");
			}catch(Exception e){
				e.printStackTrace();
				addResult(mv, false, e.getMessage());
				logger.error("exception throws:",e);
			}
		return mv;
		}	
		
		
		
		
		@RequestMapping("reset_password")
		public ModelAndView  rorgot_password(HttpServletRequest request,HttpServletResponse response,String username,String msgcode,String newpassword){
			ModelAndView mv = createView("", request, response);
			try{
				if(StringUtils.isBlank( username)){
					throw new ParameterException("用户名错误");		
				}
				if(StringUtils.isBlank(msgcode)){
					throw new ParameterException("密码不能为空");
				}
				int counter = SmUserTbl.find.where().eq("webSet.id", getWebSet(request).getId()).eq("username",username).findCount();
				if(counter>0){
					throw new ParameterException("用户名错误");
				}
				SmUserTbl foruser=SmUserTbl.find.where().eq("webSet", getWebSet(request)).eq("username", username).findUnique();
				if(foruser==null){
					throw new ParameterException("用户不存在");
				}
				 HttpSession session = request.getSession(false);
//				 验证图片验证码
				 String scode=(String) session.getAttribute(username);	
				 if(!scode.equals(msgcode)){
					 throw new ParameterException("验证码错误"); 
				 }
			foruser.setPassword(MD5.md5(newpassword));
			foruser.update();
			addResult(mv, true, "操作成功");
			logger.debug("操作成功");
			}catch(Exception e){
			addResult(mv, false, e.getMessage());
				logger.error("exception throws:",e);
			}
		return mv;
		}
		
		@RequestMapping("user/update_password")
		public ModelAndView  update_password(HttpServletRequest request,HttpServletResponse response,Integer u_id,String username,String olderpassword,String newpassword){
			ModelAndView mv = createView("", request, response);
			try{
				if(u_id==null || u_id==0){
					throw new ParameterException("用户名信息不存在");		
				}
				SmUserTbl foruser=SmUserTbl.find.byId(u_id);
				if(foruser==null){
					throw new ParameterException("用户不存在");
				}
				if(!foruser.getPassword().equals(MD5.md5(olderpassword))){
					throw new ParameterException("用户名或密码错误");	
				}
				foruser.setPassword(MD5.md5(newpassword));
				foruser.update();
				addResult(mv, true, "操作成功");
				logger.debug("操作成功");
			}catch(Exception e){
				addResult(mv, false, e.getMessage());
				logger.error("exception throws:",e);
			}
			return mv;
		}	
	 
	 
}
