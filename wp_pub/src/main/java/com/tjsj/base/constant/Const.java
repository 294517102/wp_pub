

package com.tjsj.base.constant;

import java.util.ResourceBundle;
import org.springframework.context.ApplicationContext;


/** 
 * 基础枚举类
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public final class Const {

	/**
	 * 项目名称,BaseFilter赋值
	 */
	public static String BASE;

	/**
	 * 项目物理路径,BaseFilter赋值
	 */
	public static String PROJECT_PATH;

	/**
	 * spring资源文件加载上下文对象
	 */
	public static ApplicationContext CONTEXT;

	/**
	 * action层对应的国际化资源文件
	 */
	public final static ResourceBundle RESOURCES = ResourceBundle.getBundle("zh_CN");

	/**
	 * 默认系统管理员所对应的角色ID为1
	 */
	public final static int DEFAULT_SYSTEM_MANGER_ROLE_ID = 1;

	/**
	 * 默认站点管理员所对应的角色ID为2
	 */
	public final static int DEFAULT_WEBSITE_MANGER_ROLE_ID = 2;

	/**
	 * 默认CMS所对应的模块ID为1
	 */
	public final static int DEFAULT_CMS_MODEL_ID = 1;

	/** 
	 * 顶级栏目的父栏目ID为0
	 */
	public final static int COLUMN_TOP_CATEGORY_ID = 0;


	/**
	 * 默认编码格式
	 */
	public final static String UTF8 = "utf-8";
	
	/**
	 * 文件路径符
	 */
	public final static String SEPARATOR ="/";
		
	public final static String SESSION_RIGHT = "RIGHT";
	
	public final static String SESSION_USER = "USER";
	
	public final static int PAGE_SIZE = 8;
	
	
	public final static String WEB_SET = "webSet";
	
	public final static String SESSION_WEB_SET = "WEB_SET";
	
	public final static String SYSTEM_LANGUAGE = "LANGUAGE";
	
	public final static String ERROR = "error";
	
	public final static String RESULT = "result";
	
	public final static String MSG = "msg";
	
	public final static String SESSION_WEATHER = "WEATHER";
	
	public final static String SESSION_DOMAIN = "DOMAIN ";
	
	public final static String SESSION_DOMAIN_INSTANCE = "DOMAIN_INSTANCE";
	
	public final static String PAYEE_TYPE_LOGOIN_ID = "ALIPAY_LOGONID";
	
	public final static String WITHDRAWALS_URL = "https://openapi.alipay.com/gateway.do";
	
	public final static String TO_REFUND_URL = "https://openapi.alipay.com/gateway.do";
	
	public final static String SERVICE = "create_direct_pay_by_user";
	
	public final static String INPUT_CHARSET = "utf-8";
	
	public final static String SIGN_TYPE = "RSA";
	
	public final static String ADMIN = "SYSTEM";
	
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	public final static String ORDER_REQUEST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//微信公众号successToken
	public final static String SESSION_WC_SUCCESS_TOKEN="wexinToken";
	
	public final static String REGEX_OF_MOBILE = "(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$";
	
	public final static String SESSION_SECURITY_CODE_BY_MOBLE = "SESSION_SECURITY_CODE_BY_MOBLE";
	
	public final static String FORMAT_CHANGE_MOBILE = "您的验证码是%s。如非本人操作，请忽略本短信";
	
	public final static String FORMAT_CHANGE_USPS="您好，您在惠生活注册的账号是:%s，密码是:%s。请记住账号密码，勿泄露给他人";
	
}