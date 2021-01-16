package com.tjsj.wp.mvc.controller.intf;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.mvc.controller.portal.LoginMaintenanceController;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.tools.SignUtil;

import cn.hutool.http.HttpUtil;

@Controller
@RequestMapping("/wechat") 
public class WechatPlatformApiController extends WpBaseController {
//	合江工会
	private static String appid = "wx4f57aae9476fa809";
	private static String secret = "1bb90baa6fc1b48e8cb40af17a3f8ace";
	
	private final String redirectUri ="http://t.qsng.tjsjnet.com/wechat/redirect_url.htm";
	private static Logger logger = LoggerFactory.getLogger(WechatPlatformApiController.class);

	@RequestMapping("/get_wechat_openid")
	public void getWechatOpenid(HttpServletRequest request, HttpServletResponse response,String url) {
		ResultJson rj = new ResultJson();
		try {
			logger.info("redirectUri:" + redirectUri);
			StringBuffer tur=new StringBuffer(redirectUri);
			if(StringUtils.isNotBlank(url)) {
				tur.append("?url="+url);
			}
			String ruri = URLEncoder.encode(tur.toString(), "utf-8");
			String redurl = String.format(
					"https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
					appid, ruri, "snsapi_userinfo", "1450122012520112");
			logger.info("url:" + redurl);
			response.sendRedirect(redurl);
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
			outJson(response, rj);
		}
	}

	@RequestMapping({ "/redirect_url" })
	public ModelAndView redirectUrl(HttpServletRequest request, HttpServletResponse response, String code,String url) {
		if (request.getSession().getAttribute("terminal") == null) {
			request.getSession().setAttribute("terminal", "wx");
		}
		String turl="gerenzhongxin";
		if(StringUtils.isNotBlank(url)) {
			turl=url;
		}
		logger.info("turl=========:" + turl);
		ModelAndView mv = createView(turl, request, response);
		try {
			logger.info("code:" + code);
			String requrl = String.format(
					"https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
					appid, secret, code);
			
			
			String result = HttpUtil.get(requrl);
			JSONObject job = JSONObject.parseObject(result);
			logger.info("result:" + result);
			logger.info("job:" + job);
			String openid = job.getString("openid");
			String access_token = job.getString("access_token");
			String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid
					+ "&lang=zh_CN";
		
			
//		另一种获取方式
			
			/*
			 * String tokenUrl = String.format(
			 * "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"
			 * ,appid, secret); String tokenresult = HttpUtil.get(tokenUrl); JSONObject
			 * tokenjob = JSONObject.parseObject(tokenresult); logger.info("tokenresult:" +
			 * tokenresult); logger.info("tokenjob:" + tokenjob); String jobaccess_token =
			 * tokenjob.getString("access_token");
			 * 
			 * String
			 * infoMoreUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+
			 * jobaccess_token+"&openid="+openid+"&lang=zh_CN"; String userInfoMoreResult =
			 * HttpUtil.get(infoMoreUrl); JSONObject userInfoMoreObj =
			 * JSONObject.parseObject(userInfoMoreResult); logger.info("userInfoMoreObj:" +
			 * userInfoMoreObj); request.getSession().setAttribute("userInfoMoreObj",
			 * userInfoMoreObj);
			 */
			
//		另一种获取方式	end	
		
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
		}
		return mv;
	}
	public static String getaccess_token() {
		String tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appid
				+ "&secret=" + secret;
		String resultStr = "";
		try {
			resultStr =HttpUtil.get(tokenurl);
			JSONObject objec = JSONObject.parseObject(resultStr);
			if (!StringUtils.isEmpty(objec.getString("access_token"))) {
				resultStr = objec.getString("access_token");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取token异常");
		}
		logger.error("获取token值为>>>>>>>>:" + resultStr);
		return resultStr;
	}

	public static String getjsapiTicket(String accesstoken) {
		String urljson = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accesstoken
				+ "&type=jsapi&offset_type=1";
		String resultStr = HttpUtil.get(urljson);
		JSONObject jsonObject = JSONObject.parseObject(resultStr);
		return jsonObject.getString("ticket");
	}
	
	@RequestMapping(value = "wechat/share")
	public void share(HttpServletRequest request, HttpServletResponse response, String url) {
		ResultJson rj = new ResultJson();
		try {
			if (StringUtils.isBlank(url)) {
				rj.setResult(false);
				rj.setResultMsg("参数错误");
				outJson(response, rj);
				return;
			}
			Map<String, String> map = new HashMap<String, String>();
			String jsapi_ticket = getjsapiTicket(getaccess_token());// jsapi_ticket
			String timestamp = Long.toString(System.currentTimeMillis() / 1000);// 时间戳
			String nonceStr = UUID.randomUUID().toString();
			String signature = SignUtil.getSignature(jsapi_ticket, nonceStr, timestamp, url);// 验证签名
			map.put("url", url);
			map.put("jsapi_ticket", jsapi_ticket);
			map.put("nonceStr", nonceStr);
			map.put("timestamp", timestamp);
			map.put("signature", signature);
			map.put("appid", appid);
			logger.error("得到放回的数据：" + map);
			rj.setResult(true);
			rj.setResultMsg("成功");
			rj.setResultData(map);
			outJson(response, rj);
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
			outJson(response, rj);
		}
		return;
	}
	
}
