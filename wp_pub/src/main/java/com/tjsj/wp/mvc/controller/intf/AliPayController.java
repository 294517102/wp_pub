package com.tjsj.wp.mvc.controller.intf;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnAliPayConfigTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.tools.SystemLogService;
import com.tjsj.wp.tools.SystemLogService;
import com.tjsj.wp.orm.entity.SmUserTbl;
@Controller
public class AliPayController extends WpBaseController{
	protected final Logger log=Logger.getLogger(AliPayController.class);
	
	/**
	 * 跳转支付宝配置列表页
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pageSize
	 * @param url
	 * @return
	 */
	@RequestMapping("admin/to_alipay_list")
	public ModelAndView toAliPayList(HttpServletRequest request,HttpServletResponse response,PageParameter page,String web){
		ModelAndView mv=createBgView("system_setting/alipay/system_alipay_list", request, response);
		PagedList<BnAliPayConfigTbl> alipayConfigList=null;
		//如果登录的是system
		if(this.getLoginUser().getUsername().equals("SYSTEM")){
			if(!StringUtil.isBlank(web)){
				mv.addObject("web", web);
				alipayConfigList=BnAliPayConfigTbl.find.where().contains("webSet.comName", web).setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			}else{
				alipayConfigList=BnAliPayConfigTbl.find.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			}
		}else{
			if(!StringUtil.isBlank(web)){
				mv.addObject("web", web);
				alipayConfigList=BnAliPayConfigTbl.find.where().contains("webSet.comName", this.getLoginUser().getWebSet().getDomain()).setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			}else{
				alipayConfigList=BnAliPayConfigTbl.find.where().contains("webSet.comName", this.getLoginUser().getWebSet().getDomain()).setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			}
		}
		 pagePrint(request, mv, alipayConfigList);
		return mv;
		
	}
	/**
	 * 跳转新增支付宝配置页面
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pageSize
	 * @param url
	 * @return
	 */
	@RequestMapping("admin/toAddAliPay")
	public ModelAndView toAddAliPay(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("system_setting/alipay/system_alipay_add", request, response);
		List<SmWebSetTbl> resultList=SmWebSetTbl.find.where().isNull("ali_config_id").findList();
		mv.addObject("resultList",resultList);
		return mv;
	}
	/**
	 * 新增或修改支付宝设置
	 * @param request
	 * @param response
	 * @param app_id  支付宝appid
	 * @param rsa_private	rsa私钥
	 * @param rsa2_private		rsa2私钥(可以为空)
	 * @param partner		签约账号2088开头
	 * @param public_key	MD5密钥
	 * @param public_key_md5	公钥MD5加密
	 * @param web_set_id	关联webset
	 * @return
	 */
	@RequestMapping("admin/saveAlipayConfig")
	public ModelAndView saveAlipayConfig(HttpServletRequest request,HttpServletResponse response,String app_id,
			String rsa_private,String rsa2_private,String partner,String public_key,String public_key_md5,
			String deadTime,Integer id,Integer web_set_id,String notify_url){
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(app_id)
				||StringUtil.isBlank(rsa_private)
				||StringUtil.isBlank(partner)
				||StringUtil.isBlank(public_key)
				||StringUtil.isBlank(deadTime)){
			addResult(mv, false, "必填参数不能为空");
			return mv;
		}
		BnAliPayConfigTbl alipayConfigTbl=new BnAliPayConfigTbl();
		alipayConfigTbl.setAppId(app_id);
		alipayConfigTbl.setPartner(partner);
		alipayConfigTbl.setPublicKey(public_key);
		alipayConfigTbl.setInputCharset(Const.INPUT_CHARSET);
		alipayConfigTbl.setService(Const.SERVICE);
		alipayConfigTbl.setPayeeTypeLogoinId(Const.PAYEE_TYPE_LOGOIN_ID);
		alipayConfigTbl.setPaymentType(1);
		alipayConfigTbl.setIsValid(1);
		alipayConfigTbl.setSignType(Const.SIGN_TYPE);
		alipayConfigTbl.setToRefundUrl(Const.TO_REFUND_URL);
		alipayConfigTbl.setWithdrawalsUrl(Const.WITHDRAWALS_URL);
		if(!StringUtil.isBlank(public_key_md5)){
			alipayConfigTbl.setPublicKeyMd5(public_key_md5);
		}
		alipayConfigTbl.setRsaPrivate(rsa_private);
		if(!StringUtil.isBlank(rsa2_private)){
			
			alipayConfigTbl.setRsa2Private(rsa2_private);
		}
		if(!StringUtil.isBlank(notify_url)){
			
			alipayConfigTbl.setNotifyUrl(notify_url);;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			java.util.Date date = null;
			try {
				date = sdf.parse(deadTime);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		alipayConfigTbl.setDeadTime(date);
		if(StringUtil.isBlank(id)){
			BnAliPayConfigTbl alipayConfig=BnAliPayConfigTbl.find.where().eq("app_id", app_id).findUnique();
			if(!StringUtil.isBlank(alipayConfig)){
				addResult(mv, false, "已经添加过了");
				return mv;
			}
			alipayConfigTbl.save();
			SmWebSetTbl webset=SmWebSetTbl.find.byId(web_set_id);
			webset.setAliConfig(alipayConfigTbl);
			webset.update();
			addResult(mv, true, "保存成功");
			//记录日志
			SystemLogService.SaveLogToDB("bn_ali_pay_config_tbl", 2, getLoginUser().getId(), "新增支付宝支付配置");
			return mv;
		}else{
			BnAliPayConfigTbl newAliPay=BnAliPayConfigTbl.find.byId(id);
			try {
				ClassUtil.copyObjectByPropertyValues(newAliPay,alipayConfigTbl);
				newAliPay.update();
			} catch (Exception e) {
				e.printStackTrace();
				addResult(mv, false, "修改失败");
				return mv;
			}
			addResult(mv, true, "修改成功");
			//记录日志
			SystemLogService.SaveLogToDB("bn_ali_pay_config_tbl", 1, getLoginUser().getId(), "修改支付宝配置");
		}
		return mv;
	}
	
	
	/**
	 * 修改支付宝设置是否可用
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("admin/modify_alipay_config_is_valid")
	public ModelAndView ModifyAliPayConfig(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv=createView("", request, response);
		try {
			if(getLoginUser().getUsername().equals(Const.ADMIN)){
				BnAliPayConfigTbl aliPayConfig=BnAliPayConfigTbl.find.byId(id);
				if(aliPayConfig.getIsValid()==2){
					aliPayConfig.setIsValid(1);
					aliPayConfig.update();
					addResult(mv, true, "修改成功");
					SystemLogService.SaveLogToDB("bn_ali_pay_config_tbl", 1, getLoginUser().getId(), "修改支付宝配置状态为有效");
				}else{
					aliPayConfig.setIsValid(2);
					aliPayConfig.update();
					addResult(mv, true, "修改成功");
					SystemLogService.SaveLogToDB("bn_ali_pay_config_tbl", 1, getLoginUser().getId(), "修改支付宝配置状态为无效");
				}
			}
			else{
				BnAliPayConfigTbl aliPayConfig=BnAliPayConfigTbl.find.byId(getLoginUser().getWebSet().getAliConfig().getId());
				if(aliPayConfig.getIsValid()==2){
					aliPayConfig.setIsValid(1);
					aliPayConfig.update();
					addResult(mv, true, "修改成功");
					SystemLogService.SaveLogToDB("bn_ali_pay_config_tbl", 1, getLoginUser().getId(), "修改支付宝配置状态为有效");
				}else{
					aliPayConfig.setIsValid(2);
					aliPayConfig.update();
					addResult(mv, true, "修改成功");
					SystemLogService.SaveLogToDB("bn_ali_pay_config_tbl", 1, getLoginUser().getId(), "修改支付宝配置状态为无效");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			addResult(mv, false, "您没有权限操作他人的账号信息!");
		}
		return mv;
	}
	
	/**
	 * 跳转至微信配置页面
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("admin/to_modify_alipay_config")
	public ModelAndView toModifyAlipayConfig(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv=createBgView("system_setting/alipay/system_alipay_update", request, response);
		if(!StringUtil.isBlank(id)){
			BnAliPayConfigTbl aliPay=BnAliPayConfigTbl.find.byId(id);
			mv.addObject("data", aliPay);
		}
		return mv;
 	}
}
