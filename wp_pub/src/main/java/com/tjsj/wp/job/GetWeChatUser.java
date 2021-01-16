package com.tjsj.wp.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.orm.entity.IfWechatUserTbl;

import weChatUtil.weiXinUserUtil;

@Component
public class GetWeChatUser {

	private static Logger  logger = LoggerFactory.getLogger(GetWeChatUser.class);
	
	
	//@Scheduled(cron="0 0/2 8-20 * * ?")  //每隔两分钟
	
	@Scheduled(cron="0 0 12 * * ?") //每天中午12点触发
	public void getWeChatUserList(){
		System.err.println("启动");
		try {
			//获取所有拥有公众号配置的webset
			List< SmWebSetTbl> webSetList=SmWebSetTbl.find.where().isNotNull("we_chat_id").findList();
			for (SmWebSetTbl WebSet : webSetList) {
				//获取appid和appSecret
				String appid=WebSet.getWeChat().getAppid();
				String appSecret=WebSet.getWeChat().getAppSecret();
				if(!StringUtil.isBlank(appid)&&!StringUtil.isBlank(appSecret)){
					//获取accessToken
					String accessToken=weiXinUserUtil.getAccess_token(appid, appSecret);
					if(StringUtil.isBlank(accessToken)){
						return;
					}
					//获取所有粉丝的Openid
					List<String > openidList=weiXinUserUtil.getAllWeiXinUserOpenids(accessToken);
					for (String openid : openidList) {
						//获取微信用户信息
						String info=weiXinUserUtil.getWechatUserInfo(openid, accessToken);
						IfWechatUserTbl wexinUserInfo=JSONObject.parseObject(info
								,IfWechatUserTbl.class
								);
						//查询当前粉丝
						IfWechatUserTbl weixinOut=IfWechatUserTbl.find.where().eq("openid", openid).findUnique();
						//如果没有该用户则新增
						if(StringUtil.isBlank(weixinOut)){
							String nicName=wexinUserInfo.getNickname();
							   while (true) {
							        Integer pos = nicName.indexOf("\uD83D");
							        if (pos == -1) {
							            pos = nicName.indexOf("\uD83C");
							        }
							        if (pos != -1) {
							        	nicName = nicName.substring(0, pos) + nicName.substring(pos + 2);
							        } else {
							            break;
							        }
							    }
							wexinUserInfo.setNickname(nicName);
							wexinUserInfo.setWebSet(WebSet);
							wexinUserInfo.save();
							//如果有该用户
						}else{
							String nicName=wexinUserInfo.getNickname();
							   while (true) {
							        Integer pos = nicName.indexOf("\uD83D");
							        if (pos == -1) {
							            pos = nicName.indexOf("\uD83C");
							        }
							        if (pos != -1) {
							        	nicName = nicName.substring(0, pos) + nicName.substring(pos + 2);
							        } else {
							            break;
							        }
							    }
							//判断
							wexinUserInfo.setNickname(nicName);
							if(!weixinOut.getNickname().equals(wexinUserInfo.getNickname())
									||!weixinOut.getHeadimgurl().equals(wexinUserInfo.getHeadimgurl())){
								ClassUtil.copyObjectByPropertyValues(weixinOut, wexinUserInfo);
								weixinOut.update();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("获取粉丝失败");
		}
		
	}
	
	public static boolean getWeChatUserListMain(){
		try {
			//获取所有拥有公众号配置的webset
			List< SmWebSetTbl> webSetList=SmWebSetTbl.find.where().isNotNull("we_chat_id").findList();
			for (SmWebSetTbl WebSet : webSetList) {
				//获取appid和appSecret
				String appid=WebSet.getWeChat().getAppid();
				String appSecret=WebSet.getWeChat().getAppSecret();
				if(!StringUtil.isBlank(appid)&&!StringUtil.isBlank(appSecret)){
					//获取accessToken
					String accessToken=weiXinUserUtil.getAccess_token(appid, appSecret);
					if(StringUtil.isBlank(accessToken)){
						return false;
					}
					//获取所有粉丝的Openid
					List<String > openidList=weiXinUserUtil.getAllWeiXinUserOpenids(accessToken);
					for (String openid : openidList) {
						//获取微信用户信息
						String info=weiXinUserUtil.getWechatUserInfo(openid, accessToken);
						IfWechatUserTbl wexinUserInfo=JSONObject.parseObject(info
								,IfWechatUserTbl.class
								);
						//查询当前粉丝
						IfWechatUserTbl weixinOut=IfWechatUserTbl.find.where().eq("openid", openid).findUnique();
						//如果没有该用户则新增
						if(StringUtil.isBlank(weixinOut)){
							String nicName=wexinUserInfo.getNickname();
							   while (true) {
							        Integer pos = nicName.indexOf("\uD83D");
							        if (pos == -1) {
							            pos = nicName.indexOf("\uD83C");
							        }
							        if (pos != -1) {
							        	nicName = nicName.substring(0, pos) + nicName.substring(pos + 2);
							        } else {
							            break;
							        }
							    }
							wexinUserInfo.setNickname(nicName);
							wexinUserInfo.setWebSet(WebSet);
							wexinUserInfo.save();
							//如果有该用户
						}else{
							String nicName=wexinUserInfo.getNickname();
							   while (true) {
							        Integer pos = nicName.indexOf("\uD83D");
							        if (pos == -1) {
							            pos = nicName.indexOf("\uD83C");
							        }
							        if (pos != -1) {
							        	nicName = nicName.substring(0, pos) + nicName.substring(pos + 2);
							        } else {
							            break;
							        }
							    }
							//判断
							wexinUserInfo.setNickname(nicName);
							if(!weixinOut.getNickname().equals(wexinUserInfo.getNickname())
									||!weixinOut.getHeadimgurl().equals(wexinUserInfo.getHeadimgurl())){
								ClassUtil.copyObjectByPropertyValues(weixinOut, wexinUserInfo);
								weixinOut.update();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("获取粉丝失败");
			return false;
		}
		return true;
	}
}
