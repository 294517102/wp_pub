

package com.tjsj.base.constant;


/**
 * cookie枚举类
 */
public enum CookieConst{
	/**
	 * 用户的cookie
	 */
	PEOPLE_COOKIE("people_cookie"),
	 
	/** 
	 * 分页cookie
	 */
	PAGENO_COOKIE("pageno_cookie"),
	
	/**
	 *上次访问地址
	 */
	BACK_COOKIE("back_cookie"),


	/**
	 * 保存用户QQ登录cookie</br>
	 * cookie为openId
	 */
	API_LOGIN_QQ_COOKIE("api_login_qq_cookie"),
	
	/**
	 * 课表cookie，保存用户所在的班级
	 */
	CURRICULUM_COOKIE("curriculum_cookie"),
	
	/**
	 * QQ登录保存当前用户点击地址的session
	 */
	API_LOGIN_QQ_URL("api_login_qq_url");
	
	
	/**
	 * 设置CookieConst的常量
	 * @param attr 常量
	 */
	CookieConst(String attr) {
		this.attr = attr;
	}
	
	private String attr;

	/**
	 * 返回该CookieConst常量的字符串表示
	 * @return 字符串
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return attr;
	}
}