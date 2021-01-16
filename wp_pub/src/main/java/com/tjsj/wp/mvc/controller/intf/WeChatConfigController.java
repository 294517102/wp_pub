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

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.m_util.classz.ClassUtil;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnAliPayConfigTbl;
import com.tjsj.wp.orm.entity.BnWechatConfigTbl;
import com.tjsj.wp.orm.entity.CmSysLogTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;
import com.tjsj.wp.tools.SystemLogService;
import com.tjsj.wp.orm.entity.IfWechatTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

@Controller
public class WeChatConfigController extends WpBaseController{

		protected final Logger log=Logger.getLogger(WeChatConfigController.class);
		
		/**
		 * 跳转至微信页面
		 * @param request
		 * @param response
		 * @param pageIndex
		 * @param pageSize
		 * @return
		 */
	@RequestMapping("admin/obtain_wechat_config")
		public ModelAndView to_weChatConfig(HttpServletRequest request,HttpServletResponse response,PageParameter page,String web){
			ModelAndView mv=createBgView("system_setting/interface/system_wx_list", request, response);
			ExpressionList<BnWechatConfigTbl> weChatList=BnWechatConfigTbl.find.where().eq("webSet", getWebSet(request));
			if(!StringUtil.isBlank(web)){
				mv.addObject("type", web);
				weChatList.contains("webSet.comName", web);
			}else{
				weChatList.setFirstRow(page.getFirstRow());
			}
			PagedList<BnWechatConfigTbl> weChatLists=weChatList.setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
			pagePrint(request, mv, weChatLists);
			addResult(mv, true, "查询成功");
			return mv;
		}
	
	/**
	 * 跳转新增微信宝配置页面
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pageSize
	 * @param url
	 * @return
	 */
	@RequestMapping("admin/toAddWx")
	public ModelAndView toAddWx(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("system_setting/interface/system_wx_add", request, response);
		List<SmWebSetTbl> resultList=SmWebSetTbl.find.where().isNull("we_chart_config_id").findList();
		mv.addObject("resultList",resultList);
		return mv;
	}
		
	/**
	 * 添加或修改微信配置
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	
	@RequestMapping("admin/addOrModifyWeChat")
	public ModelAndView addOrModifyWeChat(HttpServletRequest request,HttpServletResponse response,
			Integer id,String appid,String deadTime,String key,String  mchId,Integer web_set_id){
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(appid)
				||StringUtil.isBlank(key)
				||StringUtil.isBlank(mchId)
				||StringUtil.isBlank(deadTime)){
			addResult(mv, false, "必填参数不能为空");
			return mv;
		}
		BnWechatConfigTbl weChatConfig=new BnWechatConfigTbl();
		weChatConfig.setAppid(appid);
		
		weChatConfig.setKeyStr(key);
		weChatConfig.setMchId(mchId);
		weChatConfig.setRefundUrl(Const.REFUND_URL);
		weChatConfig.setOrderRequestUrl(Const.ORDER_REQUEST_URL);
		weChatConfig.setIsValid(1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		java.util.Date date = null;
		try {
			date = sdf.parse(deadTime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		weChatConfig.setDeadTime(date);
		if(StringUtil.isBlank(id)){
			BnWechatConfigTbl WeChatConfigTbl=BnWechatConfigTbl.find.where().eq("appid", appid).findUnique();
			if(!StringUtil.isBlank(WeChatConfigTbl)){
				addResult(mv, false, "已经添加过了");
				return mv;
			}
			Ebean.beginTransaction();
			Ebean.commitTransaction();
			weChatConfig.save();
			if(!StringUtil.isBlank(web_set_id)){
				SmWebSetTbl webset=SmWebSetTbl.find.byId(web_set_id);
				webset.setWeChatConfig(weChatConfig);
				webset.update();
			}
			addResult(mv, true, "添加成功");
			//记录日志
			SystemLogService.SaveLogToDB("bn_we_chat_config_tbl", 2, getLoginUser().getId(), "添加微信配置");
			return mv;
		}
		else{
			BnWechatConfigTbl we=BnWechatConfigTbl.find.byId(id);
			try {
				ClassUtil.copyObjectByPropertyValues(we, weChatConfig);
				we.update();
				addResult(mv, true, "修改成功");
				//记录日志
				SystemLogService.SaveLogToDB("bn_we_chat_config_tbl", 1, getLoginUser().getId(), "修改微信配置");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				addResult(mv, false, "修改异常");
			}
		}
		return mv;
	}
	
	/**
	 * 修改状态
	 * @param request
	 * @param response
	 * @param weixinSetId
	 * @return
	 */
	@RequestMapping("admin/setIsValid")
	public ModelAndView setIsValid(HttpServletRequest request,HttpServletResponse response,Integer weixinSetId){
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(weixinSetId)){
			addResult(mv, false, "参数错误");
			return mv;
		}
		BnWechatConfigTbl weChat=BnWechatConfigTbl.find.byId(weixinSetId);
		if(weChat.getIsValid()==1){
			weChat.setIsValid(2);
			weChat.update();
		}else{
			weChat.setIsValid(1);
			weChat.update();
		}
		addResult(mv, true, "修改成功");
		return mv;
	}
	
	/**
	 * 跳转到微信设置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/to_weixin_set")
	public ModelAndView to_weixin_set(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=createBgView("system_setting/interface/weixin_set", request, response);
		try {
			IfWechatTbl weChat=IfWechatTbl.find.where().eq("webset.id", getLoginUser().getWebSet().getId()).findUnique();
			mv.addObject("weixin", weChat);
		} catch (Exception e) {
			// TODO: handle exception
			addResult(mv, false, "您还没有网站或您还没有公众号设置权限，请联系管理员开通");
		}
		
		return mv;
	}
	
	/**
	 * 保存公众号设置
	 * @param request
	 * @param response
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	@RequestMapping("admin/save_weixin_set")
	public ModelAndView saveWeiXinSet(HttpServletRequest request,HttpServletResponse response,String appid,String appSecret){
		ModelAndView mv=createView("", request, response);
		if(this.isLogin()==false){
			addResult(mv, false, "请登录后操作");
		}
		if(StringUtil.isBlank(appid)||StringUtil.isBlank(appSecret)){
			addResult(mv, false, "参数错误");
			return mv;
		}
		SmWebSetTbl webset=SmUserTbl.find.byId(getLoginUser().getId()).getWebSet();
		if(StringUtil.isBlank(webset)){
			addResult(mv, false, "请先联系管理员添加网站");
			return mv;
		}
		IfWechatTbl wechat=new IfWechatTbl();
		wechat.setAppid(appid);
		wechat.setAppSecret(appSecret);
		wechat.save();
		webset.setWeChat(wechat);
		webset.update();
		addResult(mv, true, "保存成功");
		return mv;
	}
	
	/**
	 * 修改公众号设置
	 * @param request
	 * @param response
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	@RequestMapping("admin/modifyToweixin")
	public ModelAndView modifyToweixin(HttpServletRequest request,HttpServletResponse response,Integer id,String appid,String appSecret){
		ModelAndView mv=createView("", request, response);
		if(StringUtil.isBlank(id)){
			addResult(mv, false, "id不能为空");
			return mv;
		}
		if(StringUtil.isBlank(appid)||StringUtil.isBlank(appSecret)){
			addResult(mv, false, "不能修改为空");
			return mv;
		}
		IfWechatTbl wechat=IfWechatTbl.find.byId(id);
		wechat.setAppid(appid);
		wechat.setAppSecret(appSecret);
		wechat.update();
		addResult(mv, true, "修改成功");
		return mv;
	}
	
	/**
	 * 跳转到微信设置
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("admin/to_weixin_pay_set")
	public ModelAndView to_weixinPaySet(HttpServletRequest request,HttpServletResponse response,Integer id){
		ModelAndView mv=createBgView("system_setting/interface/system_wx_update", request, response);
		BnWechatConfigTbl weChat=BnWechatConfigTbl.find.byId(id);
		mv.addObject("data", weChat);
		return mv;
	}
}
