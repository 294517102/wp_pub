
package com.tjsj.wp.mvc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.CookieConst;
import com.tjsj.m_util.codec.Base64;
import com.tjsj.m_util.string.StringUtil;


/**
 * 基础控制类
 * @author gongdzh
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseController {
     
	/*
	 * log4j日志记录
	 */
	protected final Logger LOG = Logger.getLogger(this.getClass());

	/**
	 * 默认页码参数
	 */
	private final String PAGE_NO = "pageNo";


	/**
	 * 获取当前页码,默认参数名称pageNo
	 * 
	 * @param request HttpServletRequest对象
	 * @return 默认1,如果存在pageNo参数就返回相应的值
	 */
	protected Integer getPageNo(HttpServletRequest request) {
		return this.getInt(request, PAGE_NO, 1);
	}

	/**
	 * 获取整型值
	 * 
	 * @param request HttpServletRequest对象
	 * @param param 参数名称
	 * @return 返回整型值，没找到返回null
	 */
	protected Integer getInt(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		if (StringUtil.isInteger(value)) {
			return Integer.parseInt(value);
		} else {
			return null;
		}
	}

	/**
	 * 获取整型值
	 * 
	 * @param request HttpServletRequest对象
	 * @param param
	 *            参数名称
	 * @param def
	 *            默认值，如果参数不存在或不符合规则就用默认值替代
	 * @return 返回整型值
	 */
	protected Integer getInt(HttpServletRequest request, String param, int def) {
		String value = request.getParameter(param);
		if (StringUtil.isInteger(value)) {
			return Integer.parseInt(value);
		} else {
			return def;
		}
	}

	/**
	 * 获取base64机密的整型值
	 * 
	 * @param request HttpServletRequest对象
	 * @param param
	 *            参数名称
	 * @return 返回base64的整型值,没找到返回null
	 */
	protected Integer getIntBase64(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		if (StringUtil.isInteger(value)) {
			return Integer.parseInt(new String(Base64.decode(value)));
		} else {
			return null;
		}
	}

	/**
	 * 获取布尔值
	 * 
	 * @param request HttpServletRequest对象
	 * @param param
	 *            参数名称
	 * @return 返回布尔值，没找到返回null
	 */
	protected Boolean getBoolean(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * 获取Cookie的值
	 * @param request HttpServletRequest对象
	 * @param key
	 *            枚举类中的值
	 * @return Cookie中获取的对象
	 */
	protected String getCookie(HttpServletRequest request, CookieConst key) {
		if (request.getCookies() != null) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals(key.name())) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 设置Cookie值
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 * @param key
	 *            枚举类中的值
	 * @param value
	 *            存储对象
	 */
	protected void setCookie(HttpServletRequest request, HttpServletResponse response, CookieConst key, Object value) {
		request.getSession().setAttribute(key.toString(), value);
		Cookie cookie = new Cookie(key.name(), (String) value);
		cookie.setPath("/");
		cookie.setValue((String) value);
		response.addCookie(cookie);
	}

	/**
	 * 设置Cookie值
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 * @param key
	 *            枚举类中的值
	 * @param value
	 *            存储对象
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	protected void setCookie(HttpServletRequest request, HttpServletResponse response, CookieConst key, Object value, int maxAge) {
		request.getSession().setAttribute(key.toString(), value);
		Cookie cookie = new Cookie(key.name(), value.toString());
		cookie.setPath("/");
		cookie.setValue(value.toString());
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	/**
	 * 输出json数据字符串
	 * @param response HttpServletResponse对象
	 * @param jsonDataStr
	 *            字符串
	 */
	protected void outJson(HttpServletResponse response, String jsonDataStr) {
		try {
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(jsonDataStr);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}

	/**
	 * 将BaseEntity以json字符串格式输出
	 * @param response
	 * @param entity 实体对象
	 */
	protected void outJson(HttpServletResponse response, Object entity) {
		this.outJson(response, JSONObject.toJSONString(entity));
	}
	
	/**
	 * 将list以json字符串格式输出
	 * @param response HttpServletResponse对象
	 * @param list 记录集合
	 */
	protected void outJson(HttpServletResponse response, List list) {
		this.outJson(response, JSONObject.toJSONString(list));
	}
	
	
	
	
	/**
	 * 输出String数据字符串
	 * @param response HttpServletResponse对象
	 * @param dataStr
	 *            字符串
	 */
	protected void outString(HttpServletResponse response, Object json) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(e);
		}
	}

	/**
	 * 获取项目路径
	 * @param request HttpServletRequest对象
	 * @return 返回项目路径，例如：http://www.ip.com/projectName 后面没有反斜杠，后面接地址或参数必须加/
	 */
	protected String getUrl(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() == 80) {
			basePath += path;
		} else {
			basePath += ":" + request.getServerPort() + path;
		}
		return basePath + "/";
	}

	/**
	 * 获取请求域名，域名不包括http请求协议头
	 * 
	 * @param request HttpServletRequest对象
	 * @return 返回域名地址
	 */
	protected String getDomain(HttpServletRequest request) {
		String path = request.getContextPath();
		String domain = request.getServerName();
		if (request.getServerPort() == 80) {
			domain += path;
		} else {
			domain += ":" + request.getServerPort() + path;
		}
		return domain;
	}

	/**
	 * 读取服务器主机信息
	 * 
	 * @param request HttpServletRequest对象
	 * @return 返回主机信息
	 */
	protected String getHost(HttpServletRequest request) {
		String basePath = request.getServerName();
		if (request.getServerPort() != 80) {
			basePath += ":" + request.getServerPort();
		}
		return basePath;
	}

	/**
	 * 读取服务器主机ip信息
	 * @return 返回主机IP，异常将会获取不到ip
	 */
	protected String getHostIp() {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();// 获得本机IP
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 读取国际化资源文件
	 * 
	 * @param key 键值
	 * @return 返回获取到的字符串
	 */
	protected String getResString(String key) {
		return Const.RESOURCES.getString(key);
	}

	/**
	 * 读取国际化资源文件，优先模块对应的资源文件，如果模块资源文件找不到就会优先基础层
	 * 
	 * @param key 键值
	 * @param rb 模块对应资源文件
	 * @return 返回获取到的字符串
	 */
	protected String getResString(String key, ResourceBundle rb) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException e) {
			return Const.RESOURCES.getString(key);
		}
	}

	/**
	 * 读取国际化资源文件
	 * 
	 * @param key
	 *            键值
	 * @param fullStrs
	 *            需填充的值
	 * @return 返回获取到的字符串
	 */
	protected String getResString(String key, String... fullStrs) {
		String temp = this.getResString(key);
		for (int i = 0; i < fullStrs.length; i++) {
			temp = temp.replace("{" + i + "}", fullStrs[i]);
		}
		return temp;
	}

	/**
	 * 读取国际化资源文件，优先模块对应的资源文件，如果模块资源文件找不到就会优先基础层
	 * 
	 * @param key 键值
	 * @param rb
	 *            模块对应资源文件
	 * @return 返回获取到的字符串
	 */
	protected String getResString(String key, ResourceBundle rb, String... fullStrs) {
		String temp = "";
		try {
			temp = rb.getString(key);
		} catch (MissingResourceException e) {
			temp = getResString(key);
		}
		for (int i = 0; i < fullStrs.length; i++) {
			temp = temp.replace("{" + i + "}", fullStrs[i]);
		}
		return temp;
	}

	/**
	 * 通过spring的webapplicationcontext上下文对象读取bean对象
	 * 
	 * @param sc
	 *            上下文servletConext对象
	 * @param beanName
	 *            要读取的bean的名称
	 * @return 返回获取到的对象。获取不到返回null
	 */
	protected Object getBean(ServletContext sc, String beanName) {
		return WebApplicationContextUtils.getWebApplicationContext(sc).getBean(beanName);
	}
	
	/**
	 * 通过spring的webapplicationcontext上下文对象读取bean对象
	 * @param beanName
	 *            要读取的bean的名称
	 * @return 返回获取到的对象。获取不到返回nul
	 */
	protected Object getBean(String beanName) {
		return  Const.CONTEXT.getBean(beanName);
	}

	/**
	 * 返回重定向
	 * 
	 * @param request HttpServletRequest对象
	 * @param flag
	 *            true:提供给springMVC返回，false:只是获取地址
	 * @return 返回重定向后的地址
	 */
	protected String redirectBack(HttpServletRequest request, boolean flag) {
		if (flag) {
			return "redirect:" + this.getCookie(request, CookieConst.BACK_COOKIE);
		} else {
			return this.getCookie(request, CookieConst.BACK_COOKIE);
		}

	}


	/**
	 * 根据属性配置文件返回map
	 * 
	 * @return 返回Map
	 */
	protected Map<String, String> getMapByProperties(String filePath) {
		if (StringUtil.isBlank(filePath)) {
			return null;
		}
		ResourceBundle rb = ResourceBundle.getBundle(filePath);
		return this.getMapByProperties(rb);
	}
	
	protected Map<String, String> getMapByProperties(ResourceBundle rb) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> en = rb.getKeys();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			map.put(key, rb.getString(key));
		}
		return map;
	}


	/**
	 * 获取当期项目物理路径
	 * 
	 * @param request HttpServletRequest对象
	 * @param filePath
	 *            　相对路径文件夹
	 * @return 返回当期项目物理路径
	 */
	protected String getRealPath(HttpServletRequest request, String filePath) {
		return request.getServletContext().getRealPath("") + File.separator + filePath;
	}


	/**
	 * 根据cookie获取历史页码
	 * 
	 * @param request HttpServletRequest对象
	 * @return　返回历史页码，没找到返回０
	 */
	protected int getHistoryPageNoByCookie(HttpServletRequest request) {
		if (Integer.valueOf(this.getCookie(request, CookieConst.PAGENO_COOKIE)) >= 1) {
			return Integer.valueOf(this.getCookie(request, CookieConst.PAGENO_COOKIE));
		}
		return 0;
	}


	/**
	 * 解析ms标签内容
	 * 
	 * @param html
	 *            模板文件html内容
	 * @param req HttpServletRequest对象
	 * @return 返回解析好的内容
	 */
	
	/**
	 * 将请求的request的参数重新组装。主要是将空值的替换成null,因为requestMap空值是"",这样处理有利于外部判断,
	 * 同时将获取到的值映射到页面上
	 * 
	 * @param request HttpServletRequest对象
	 * @return　返回处理过后的数据
	 */
	protected Map<String, Object> assemblyRequestMap(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String[]> map = request.getParameterMap();
		Iterator<String> key = map.keySet().iterator();
		while (key.hasNext()) {
			String k = (String) key.next();
			String[] value = map.get(k);

			if (value.length == 1) {
				String temp = null;
				if (!StringUtil.isBlank(value[0])) {
					temp = value[0];
				}
				params.put(k, temp);
				request.setAttribute(k, temp);
			} else if (value.length == 0) {
				params.put(k, null);
				request.setAttribute(k, null);
			} else if (value.length > 1) {
				params.put(k, value);
				request.setAttribute(k, value);
			}
		}
		return params;
	}

	/**
	 * 获取请求的数据流，主要提供给微信平台接口使用
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 返回请求的数据流字符串，例如：微信平台会返回JSON或xml字符串
	 */
	protected String readStreamParameter(HttpServletRequest request) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	/**
	 * 获取请求客户端ip
	 * @param request
	 * @return ip地址
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getRequestHeader(HttpServletRequest request){
		String result = new String();
		Enumeration names = request.getHeaderNames();
		while(names.hasMoreElements()){
	        String name = (String) names.nextElement();
	        result += name + ":" + request.getHeader(name)+"\n";
	      }
		return result;
	}
	
	/**
	 * 获取对应ip地址的mac地址
	 * @param ip
	 * @return mac地址
	 */
	public String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}
	
	//参数type无效，为了兼容，先保留
	public String getWrapperView(String viewName , String prefix, HttpServletRequest request){
		String relatedPath = prefix + "/";	
		HttpSession  session = request.getSession();
		Object terminal = session.getAttribute("terminal");
		System.err.println("view终端："+terminal);
		//根据终端参数，决定是否修改type值
		//1-PC终端；2-移动终端；3-微信
		int type = 1; //默认为pc		
		if(terminal != null){
			switch (terminal.toString().toLowerCase()) {
				case "pc":
					type = 1;
					break;
				case "mb":
					type = 2;
					break;
				case "wx":
					type = 3;
					break;
				default:
					break;
			}
		}
		//根据type指向不同路径
		String partitioningPath = new String();
		switch(type){
			case 1:
				partitioningPath = "pc/";
				break;
			case 2:
				partitioningPath = "mb/";
				break;
			case 3:
				partitioningPath = "wx/";
				break;
			default:
				partitioningPath = "pc/";
				break;			
		}
		
		relatedPath += partitioningPath + viewName;	
		return relatedPath;
	}
	
}