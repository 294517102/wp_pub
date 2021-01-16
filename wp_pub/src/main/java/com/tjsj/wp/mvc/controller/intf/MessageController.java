package com.tjsj.wp.mvc.controller.intf;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tjsj.base.constant.Const;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.entity.SmsSearchSuccessEntity;
import com.tjsj.wp.entity.SmsSendSuccessEntity;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.mvc.controller.service.SmsService;
import com.tjsj.wp.orm.entity.CmSmsConfigTbl;

@Controller
public class MessageController extends WpBaseController {

	@Autowired
	public SmsService smsService;
	
	/**
	 * 跳转到短信设置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/toMessageView")
	public ModelAndView toMessageView(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=createBgView("system_setting/interface/sms_set", request, response);
		if(StringUtil.isBlank(this.getWebSet(request))) {
			addResult(mv, false, "请先设置站点");
			return mv;
		}
		smsService.searchMessageCount(request);
		CmSmsConfigTbl smsConfig=CmSmsConfigTbl.find.where().eq("webSet", this.getWebSet(request)).findUnique();
		mv.addObject("smsConfig",smsConfig);
		return mv;
	}
	/**
	 * 发送短信验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("sendMessage")
	public ModelAndView sendMessage(HttpServletRequest request,HttpServletResponse response,String mobile) throws IOException {
		ModelAndView mv=createView("", request, response);
		Pattern identPattern = Pattern.compile(Const.REGEX_OF_MOBILE); 
		Matcher idNumMatcher  = identPattern.matcher(mobile);
		 if(!idNumMatcher.matches()){
			 	addResult(mv, false, "手机号错误");
			 	return mv;
		 }
		 String checkCode =  com.tjsj.m_util.codec.RandomUtil.generateNumber(6);
		 request.getSession(false).setAttribute(Const.SESSION_SECURITY_CODE_BY_MOBLE, checkCode);
		 String emsContent = String.format(Const.FORMAT_CHANGE_MOBILE,checkCode);
		 if(smsService.sendMessage(emsContent, mobile,request)) {
			 addResult(mv, true, "发送成功");
		 }else {
			 addResult(mv, false, "发送失败");
		 }
		return mv;
	}
	
	/**
	 * 保存或修改短信设置
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("admin/saveMessageSet")
	public ModelAndView saveMessageSet(HttpServletRequest request,HttpServletResponse response,CmSmsConfigTbl smsConfig) throws Exception {
		ModelAndView mv=createView("", request, response);
		System.err.println(smsConfig.toString());
		if(StringUtil.isBlank(smsConfig.getAccount())) {
			addResult(mv, false, "序列号不能为空");
			return mv;
		}
		if(StringUtil.isBlank(smsConfig.getPassword())) {
			addResult(mv, false, "密码不能为空");
			return mv;
		}
		if(StringUtil.isBlank(smsConfig.getSignature())) {
			addResult(mv, false, "签名不能为空");
			return mv;
		}
		if(StringUtil.isBlank(smsConfig.getUserid())) {
			addResult(mv, false, "特服号不能为空");
			return mv;
		}
		if(StringUtil.isBlank(smsConfig.getId())) {
			smsConfig.save();
			addResult(mv, true, "保存成功");
		}else {
			CmSmsConfigTbl smsconfig=CmSmsConfigTbl.find.byId(smsConfig.getId());
			ClassUtil.copyObjectByPropertyValues(smsconfig, smsConfig);
			smsconfig.update();
			addResult(mv, true, "修改成功");
		}
		return mv;
	}
	
	/**
	 * 设置子账号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/SetChildUser")
	public ModelAndView SetChildUser(HttpServletRequest request ,HttpServletResponse response) {
		ModelAndView mv=createView("", request, response);
		return mv;
	}

}
