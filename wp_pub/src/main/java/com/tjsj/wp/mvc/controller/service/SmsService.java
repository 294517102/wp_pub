package com.tjsj.wp.mvc.controller.service;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.entity.SmsSearchSuccessEntity;
import com.tjsj.wp.entity.SmsSendSuccessEntity;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.mvc.controller.intf.SmsClientAccessTool;
import com.tjsj.wp.orm.entity.CmSmsConfigTbl;
import com.tjsj.wp.orm.entity.IfSmsLogTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;


@Service
public class SmsService extends WpBaseController {
	private String url="http://client.movek.net:8888/sms.aspx";
	
	protected final  Logger log=Logger.getLogger(SmsService.class);
	
	/**
	 * 查询短信余额
	 * @param request
	 * @return
	 */
	public SmsSearchSuccessEntity searchMessageCount(HttpServletRequest request) {
		SmWebSetTbl webset=getWebSet(request);
		CmSmsConfigTbl smsconfig=webset.getMessageConfig();
		if(StringUtil.isBlank(smsconfig)) {
			return null;
		}
		String result=searchAmount(smsconfig.getUserid(), smsconfig.getAccount(), smsconfig.getPassword());
		SmsSearchSuccessEntity sms=JSON.parseObject(result,SmsSearchSuccessEntity.class);
		if(!sms.getCode().equals("Success")) {
			return null;
		}
		//刷新短信条数
		smsconfig.setTotal(sms.getData().getOverage());
		smsconfig.setToCount(sms.getData().getSendTotal()-sms.getData().getOverage());
		smsconfig.update();
		return sms;
	}
	/**
	 * 发送短信
	 * @param account	序列号
	 * @param password	密码
	 * @param userid		ID
	 * @param content	内容
	 * @return
	 * @throws IOException 
	 */
	public boolean sendMessage(String content,String mobile,HttpServletRequest request)  {
		if(StringUtil.isBlank(content)||
				StringUtil.isBlank(mobile)) {
			log.error("参数错误");
			return false;
		}
		SmWebSetTbl webset=getWebSet(request);
		CmSmsConfigTbl smsconfig=webset.getMessageConfig();
		if(!StringUtil.isBlank(smsconfig.getSignature())) {
			content+=smsconfig.getSignature();
		}else {
			content+="【天健世纪】";
		}
		 log.info("手机："+mobile+",消息："+content);
		String result=sendSms(smsconfig.getUserid(),smsconfig.getAccount(),smsconfig.getPassword(),mobile,content);
		SmsSendSuccessEntity sms=JSON.parseObject(result,SmsSendSuccessEntity.class);
		IfSmsLogTbl smsLog = new IfSmsLogTbl();
		smsLog.setResultDesc(sms.getData().getMessage());
		smsLog.setReturn_(sms.getCode());
		if(!sms.getCode().equals("Success")) {
			log.info(sms.getData().getMessage());
			return false;
		}
			smsLog.setContent(content);
			smsLog.setMobile(mobile);
			smsLog.save();
		return true;
	}
	
	
	
	/**
	 * 查询短信余额
	 * @param userid
	 * @param account
	 * @param password
	 * @param mobile
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unused")
	private String searchAmount( String userid, String account,
				String password) {
			String codingType=null;
			String backEncodType=null;
			String action="overage";
			String sendType=null;
			String result="";
	try {
		if (codingType == null || codingType.equals("")) {
			codingType = "UTF-8";
		}
		if (backEncodType == null || backEncodType.equals("")) {
			backEncodType = "UTF-8";
		}
		StringBuffer send = new StringBuffer();
		if (action != null && !action.equals("")) {
			send.append("action=").append(action);
		} else {
			send.append("action=send");
		}

		send.append("&userid=").append(userid);
		send.append("&account=").append(
				URLEncoder.encode(account, codingType));
		send.append("&password=").append(
				URLEncoder.encode(password, codingType));
			send.append("&json=").append(1);
		if (sendType != null && (sendType.toLowerCase()).equals("get")) {
			result=SmsClientAccessTool.getInstance().doAccessHTTPGet(
					url + "?" + send.toString(), backEncodType);
			return result;
		} else {
			result=SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
					send.toString(), backEncodType);
			return result;
		}
	} catch (Exception e) {
		e.printStackTrace();
		return "未发送，编码异常";
	}
}
	/**
	 * <p>
	 * <date>2012-03-01</date><br/>
	 * <span>发送信息方法--暂时私有化，这里仅仅是提供用户接口而已。其实用不了那么复杂</span><br/>
	 * <span>发送信息最终的组合形如：http://118.145.30.35/sms.aspx?action=send</span>
	 * </p>
	 * 
	 * @param url
	 *            ：必填--发送连接地址URL--比如>http://118.145.30.35/sms.aspx
	 * 
	 * @param userid
	 *            ：必填--用户ID，为数字
	 * @param account
	 *            ：必填--用户帐号
	 * @param password
	 *            ：必填--用户密码
	 * @param mobile
	 *            ：必填--发送的手机号码，多个可以用逗号隔比如>13512345678,13612345678
	 * @param content
	 *            ：必填--实际发送内容，
	 * @param action
	 *            ：选填--访问的事件，默认为send
	 * @param sendTime
	 *            ：选填--定时发送时间，不填则为立即发送，时间格式如>2011-11-11 11:11:11
	 * @param checkContent
	 *            ：选填--检查是否包含非法关键字，1--表示需要检查，0--表示不检查
	 * @param taskName
	 *            ：选填--任务名称，本次任务描述，100字内
	 * @param countNumber
	 *            ：选填--提交号码总数
	 * @param mobileNumber
	 *            ：选填--手机号码总数
	 * @param telephoneNumber
	 *            ：选填--小灵通（和）或座机总数
	 * @param sendType
	 *            ：选填--发送方式，默认为POST
	 * @param codingType
	 *            ：选填--发送内容编码方式，默认为UTF-8
	 * @param backEncodType
	 *            ：选填--返回内容编码方式，默认为UTF-8
	 * @return 返回发送之后收到的信息
	 */
	@SuppressWarnings("unused")
	private String sendSms( String userid, String account,
			String password, String mobile, String content) {
				String codingType=null;
				String backEncodType=null;
				String action=null;
				String sendType=null;
				String result="";
		try {
			if (codingType == null || codingType.equals("")) {
				codingType = "UTF-8";
			}
			if (backEncodType == null || backEncodType.equals("")) {
				backEncodType = "UTF-8";
			}
			StringBuffer send = new StringBuffer();
			if (action != null && !action.equals("")) {
				send.append("action=").append(action);
			} else {
				send.append("action=send");
			}

			send.append("&userid=").append(userid);
			send.append("&account=").append(
					URLEncoder.encode(account, codingType));
			send.append("&password=").append(
					URLEncoder.encode(password, codingType));
			send.append("&mobile=").append(mobile);
			send.append("&content=").append(
					URLEncoder.encode(content, codingType));
				send.append("&json=").append(1);
			if (sendType != null && (sendType.toLowerCase()).equals("get")) {
				result=SmsClientAccessTool.getInstance().doAccessHTTPGet(
						url + "?" + send.toString(), backEncodType);
				return result;
			} else {
				result=SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
						send.toString(), backEncodType);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "未发送，编码异常";
		}
	}
}
