package com.tjsj.wp.mvc.controller.intf;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.http.NetworkUtil;
import com.tjsj.m_util.net.MailSenderInfo;
import com.tjsj.m_util.net.SimpleMailSender;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.CmMailConfigTbl;
import com.tjsj.wp.orm.entity.CmMailTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;
/**
 * 推送
 * @author 李波
 *
 */


@Controller
public class SendMailController extends WpBaseController{

	private static Logger logger = LoggerFactory.getLogger(SendMailController.class);
	
	/**
	 * 跳转到发送邮箱界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/to_send_mail")
	public ModelAndView toSetdMail(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=null;
		if(this.isLogin()==false){
			addResult(mv, false, "请登录后操作");
			return mv;
		}
		
		SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
		if(StringUtil.isBlank(webset)){
			addResult(mv, false, "您还没有建站！");
			return mv;
		}
		
		CmMailTbl mail=CmMailTbl.find.where().eq("webset_id", getWebSet(request).getId()).findUnique();
			mv=createBgView("system_setting/interface/mail_set", request, response);
			List<CmMailConfigTbl > mailList=CmMailConfigTbl.find.orderBy("supplier_name desc").findList();
		mv.addObject("mailList", mailList);
		mv.addObject("mail", mail);
		mv.addObject("webset", webset);
		return mv;
	}
	
	/**
	 * 跳转到发送邮件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/toSendMail")
	public ModelAndView toSendMail(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("system_setting/interface/send_mail", request, response);
		return mv;
	}
	
	/**
	 * 保存站点设置
	 * @param request
	 * @param response
	 * @param mail
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("admin/saveMailSet")
	public ModelAndView saveMailSet(HttpServletRequest request,HttpServletResponse response,CmMailTbl mail,Integer otype) throws Exception{
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(mail.getAddress())||
				StringUtil.isBlank(mail.getPassword())||
				StringUtil.isBlank(mail.getUsername())||
				StringUtil.isBlank(otype)){
			addResult(mv, false, "参数错误");
			return mv;
		}
		SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
		mail.setIsDelete(-1);
		mail.setWebset(webset);
		mail.setMailConfig(CmMailConfigTbl.find.byId(otype));
		if(StringUtil.isBlank(mail.getId())){
			CmMailTbl mails=CmMailTbl.find.where().eq("address", mail.getAddress()).findUnique();
			if(!StringUtil.isBlank(mails)){
				addResult(mv, false, "已经添加过了");
				return mv;
			}
			mail.save();
		}else{
			CmMailTbl mails=CmMailTbl.find.byId(mail.getId());
			ClassUtil.copyObjectByPropertyValues(mails,mail);
			mails.update();
			addResult(mv, true, "修改成功");
		}
		return mv;
	}
	  /**
	   * 发送邮件
	   * @param request
	   * @param response
	   * @return
	   */
	  @SuppressWarnings("static-access")
	@RequestMapping("/sendEmil")
	  public ModelAndView sendEmil(HttpServletRequest request,HttpServletResponse response,String title,String content,String url){
		  ModelAndView mv=createView("", request, response);
		  if(StringUtil.isBlank(content)||
				  StringUtil.isBlank(url)||
		  		  StringUtil.isBlank(title)){
			  addResult(mv, false, "参数错误");
			  return mv;
		  }
		  	CmMailTbl mail=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet().getMail();
		  	if(StringUtil.isBlank(mail)){
		  		addResult(mv, false, "系统找不到您的邮箱配置！");
		  		return mv;
		  	}
		  	MailSenderInfo mailinfo=new MailSenderInfo();
		  	mailinfo.setToAddress(url);
		  	mailinfo.setSubject(title);
		  	mailinfo.setContent(content);
		  	mailinfo.setPassword(mail.getPassword());
		  	mailinfo.setUserName(mail.getUsername());
		  	mailinfo.setFromAddress(mail.getAddress());
		  	mailinfo.setMailServerHost(mail.getMailConfig().getMailServerHost());
		  	mailinfo.setValidate(true);
		  	mailinfo.setMailServerPort(mail.getMailConfig().getMailServerPort());
			SimpleMailSender simpleMailSender = new SimpleMailSender();
			simpleMailSender.sendHtmlMail(mailinfo);
			addResult(mv, true, "发送 成功");
			return mv;
	  }
}