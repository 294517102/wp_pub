package com.tjsj.wp.mvc.controller.intf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.job.GetWeChatUser;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.orm.entity.IfWechatTbl;
import com.tjsj.wp.orm.entity.IfWechatUserTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

import weChatMseeage.sendMessage;
import weChatUtil.WeiXinUserEntity;
import weChatUtil.weiXinUserUtil;

@Controller
public class WeChatController extends WpBaseController {
	private static Logger  logger = LoggerFactory.getLogger(WeChatController.class);
	
	/**
	 * 跳转微信粉丝列表
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("admin/to_we_chat_user_list")
	public ModelAndView toWeChatUserList(HttpServletRequest request,HttpServletResponse response,
			PageParameter page,String name){
		ModelAndView mv=createBgView("tools/weixin/we_chat_list", request, response);
		PagedList<IfWechatUserTbl> weChatUserList=null;
		ExpressionList<IfWechatUserTbl> search=IfWechatUserTbl.find.where().eq("webSet.id", getLoginUser().getWebSet().getId());
		if(this.isLogin()==false){
			addResult(mv, false, "未登录");
			return mv;
		}
		if(StringUtil.isBlank(getLoginUser().getWebSet())){
			addResult(mv, false, "您还没有开通网站");
			return mv;
		}
		if(!StringUtil.isBlank(name)){
			search.contains("nickname", name);
			mv.addObject("nickname", name);
		}
		if(page.getPageSize()==null||page.getPageSize()==0)page.setPageSize(6);
		weChatUserList=search.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
		List<IfWechatUserTbl> wechatList=IfWechatUserTbl.find.findList();
		pagePrint(request, mv, weChatUserList);
		mv.addObject("userCount", wechatList.size());
		addResult(mv, true, "查询成功");
		return mv;
	}
	
	/**
	 * 获取微信所有粉丝
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/getWeChatList")
	public ModelAndView getWeChatList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createView("", request, response);
		if(this.isLogin()==false){
			addResult(mv, false, "未登录");
			return mv;
		}
		try {
			//获取登录用户appid，和商户号
			SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
			if(StringUtil.isBlank(webset)){
				addResult(mv, false, "您还没有开通网站，请联系管理员开通");
				return mv;
			}
			IfWechatTbl weChat=webset.getWeChat();
			if(StringUtil.isBlank(weChat)){
				addResult(mv, false, "您的网站还没有创建微信公众号");
				return mv;
			}
			//获取网站所有用户的openid
			String appid=weChat.getAppid();
			String appSecret=weChat.getAppSecret();
			if(!StringUtil.isBlank(appid)&&!StringUtil.isBlank(appSecret)){
				//获取用户openid
				String accessToken=weiXinUserUtil.getAccess_token(appid, appSecret);
				List<String> openids=weiXinUserUtil.getAllWeiXinUserOpenids(accessToken);
				for (String openid : openids) {
					IfWechatUserTbl weChatUserTbl=JSONObject.parseObject(weiXinUserUtil.getWechatUserInfo(openid, accessToken),IfWechatUserTbl.class);
					IfWechatUserTbl oldWeChatUser=IfWechatUserTbl.find.where().eq("openid", openid).findUnique();
					if(StringUtil.isBlank(oldWeChatUser)){
						String nicName=weChatUserTbl.getNickname();
						String newNicName=null;
						if(nicName.contains("?")||nicName.contains("？")){
							newNicName=nicName.replace("?", "_");
							weChatUserTbl.setNickname(newNicName);
						}
						System.out.println("昵称:"+weChatUserTbl.getNickname());
						weChatUserTbl.setWebSet(webset);
						weChatUserTbl.save();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("pao:",e);
		}
		
		addResult(mv, true, "查询并保存成功");
		return mv;
	}
	
	/**
	 * 向粉丝发送消息
	 * @param request
	 * @param response
	 * @param id
	 * @param content
	 * @return
	 */
	@RequestMapping("admin/sendMsg")
	public ModelAndView sendMsg(HttpServletRequest request,HttpServletResponse response,Integer id,String content){
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(id)||StringUtil.isBlank(content)){
			addResult(mv, false, "参数错误");
			return mv;
		}
		if(this.isLogin()==false){
			addResult(mv, false, "请登录");
			return mv;
		}
		IfWechatUserTbl weChatUser=IfWechatUserTbl.find.byId(id);
		String token=getLogingSuccessToken(request);
		System.err.println("token="+token);
		if(StringUtil.isBlank(token)){
			SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
			String appid=webset.getWeChat().getAppid();
			String appSecret=webset.getWeChat().getAppSecret();
			//获取token
			token=weiXinUserUtil.getAccess_token(appid, appSecret);
			//查询当前粉丝的openid
		}
		sendMessage send=new sendMessage();
		String result=send.sendTextMessageToUser(content, weChatUser.getOpenid(), token);
		String state=JSONObject.parseObject(result).getString("errcode");
		if(state.equals("0")){
			addResult(mv, true, "发送成功");	
		}else if(state.equals("45015")){
			addResult(mv, false, "发送失败，用户长时间未活跃");
		}else{
			addResult(mv, false, "未知原因");
		}
		return mv;
	}
	
	/**
	 * 跳转发送图文消息页面
	 * @param request
	 * @param response
	 * @param data
	 * @param name
	 * @return
	 */
	@RequestMapping("admin/toSendPicMsg")
	public ModelAndView toSendPicMsg(HttpServletRequest request,HttpServletResponse response,String data,String name){
		ModelAndView mv=createBgView("tools/weixin/send_MSG_pic&text", request, response);
		mv.addObject("data", data);
		mv.addObject("name", name);
		return mv;
	}
	
	/**
	 * 发送图文消息给指定用户
	 * @param request
	 * @param response
	 * @param url	跳转的页面
	 * @param picUrl   封面图路径
	 * @param id	粉丝id
	 * @param description  描述
	 * @param title	 标题
	 * @return
	 */
	@RequestMapping("admin/sendPicAndTextToUser")
	public ModelAndView sendPicAndTextToUser(HttpServletRequest request,HttpServletResponse response,String url,
			String picUrl,Integer id,String description,String title){
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(title)||
				StringUtil.isBlank(url)||
				StringUtil.isBlank(picUrl)||
				StringUtil.isBlank(id)||
				StringUtil.isBlank(description)){
			addResult(mv, false, "参数错误");
			return mv;
		}
		//获取微信token
		String token=getLogingSuccessToken(request);
		if(StringUtil.isBlank(token)){
			SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
			String appid=webset.getWeChat().getAppid();
			String appSecret=webset.getWeChat().getAppSecret();
			//获取token
			token=weiXinUserUtil.getAccess_token(appid, appSecret);
			//查询当前粉丝的openid
		}
		IfWechatUserTbl weChat=IfWechatUserTbl.find.byId(id);
		sendMessage sendMSG=new sendMessage();
		String result=sendMSG.sendNewsToUser(weChat.getOpenid(), token, picUrl, description, title, url);
		String state=JSONObject.parseObject(result).getString("errcode");
		if(state.equals("0")){
			addResult(mv, true, "发送成功");	
		}else if(state.equals("45015")){
			addResult(mv, false, "发送失败，用户长时间未活跃");
		}else{
			addResult(mv, false, "未知原因");
		}
		return mv;
	}
	
	/**
	 * 实时更新所有用户粉丝列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/uploadWeChatUserList")
	public ModelAndView uploadWeChatUserList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createView("", request, response);
		if(GetWeChatUser.getWeChatUserListMain()==true){
			addResult(mv, true, "更新成功");
		}
		else{
			addResult(mv, false, "更新失败");
		}
		return mv;
	}
}
