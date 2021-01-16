
package com.tjsj.fwk.mvc.interceptors;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tjsj.base.constant.Const;
import com.tjsj.base.constant.DefSet;
import com.tjsj.m_util.http.DeviceUtils;
import com.tjsj.m_util.spring.SpringUtil;
import com.tjsj.wp.mvc.view.label.At;
import com.tjsj.wp.mvc.view.label.Cl;
import com.tjsj.wp.mvc.view.label.Cm;
import com.tjsj.wp.mvc.view.label.Fl;
import com.tjsj.wp.mvc.view.label.Sc;
import com.tjsj.wp.orm.entity.CmColumnTbl;
import com.tjsj.wp.orm.entity.SmWebSetTbl;

/**
 * 所有action的拦截器，主要是设置base与basepath
 */
public class ObjectInterceptor extends HandlerInterceptorAdapter {

	protected Logger logger = Logger.getLogger(this.getClass());

	public static boolean IS_WINDOWS = false;

	public DefSet defSet = (DefSet) SpringUtil.getBean("defSet");

	static {
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") > 0) {
			IS_WINDOWS = true;
		}
	}

	/**
	 * 所有action的拦截,主要拦截base与basepath
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @param handler
	 *            处理器
	 * @throws Exception
	 *             异常处理
	 * @return 处理后返回true
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if(handlerMethod.getMethod().getName().equals("go")|| handlerMethod.getMethod().getName().equals("general_service")) {
		
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();

			String[] values = request.getParameterValues(name);
			for (String value : values) {
				if(name.equals("k")){
					int c=CmColumnTbl.find.where().eq("keyword", value).findCount();
					if(c==0) {
						response.sendRedirect("/index.htm");
						return false;					
					}
				}
				if(name.equals("id")&&!StringUtils.isNumeric(value)){
					response.sendRedirect("/index.htm");
					return false;	
				}
				// 跨站xss清理
				value = xssEncode(value);
				if (judgeSQLInject(value.toLowerCase())) {
					logger.error("error:" + value);
					response.sendRedirect("/index.htm");
					return false;
				}
			}
			}
		}

			}
		// 去掉http头，屏蔽端口号
		String instanceDomain = null;

		if (StringUtils.isNotBlank(defSet.getDomain())) {
			instanceDomain = defSet.getDomain();
		} else {
			instanceDomain = request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + Const.BASE;
			instanceDomain = instanceDomain.replace("/", "_");
			if (instanceDomain.indexOf(":") > 0) {
				instanceDomain = instanceDomain.replace(":", "_");
			}
		}
		logger.info("domain : " + instanceDomain);

		try {

			HttpSession session = request.getSession(true);

			// 设置language
			String language = request.getParameter("language");
			Object oLanguage = session.getAttribute(Const.SYSTEM_LANGUAGE);

			if (StringUtils.isNotBlank(language)) {
				session.setAttribute(Const.SYSTEM_LANGUAGE, language);
			} else {
				if (session.getAttribute(Const.SYSTEM_LANGUAGE) == null) {
					language = "zh_CN";
					session.setAttribute(Const.SYSTEM_LANGUAGE, "zh_CN");
				}

			}

			// 站点 管理
			Object languages = session.getAttribute(Const.SYSTEM_LANGUAGE);

			Object domain = session.getAttribute(Const.SESSION_DOMAIN);

			// 终端信息初始化
			String terminal = request.getParameter("terminal");

			if (domain == null) {
				session.setAttribute(Const.SESSION_DOMAIN, instanceDomain);
				SmWebSetTbl webSet = SmWebSetTbl.find.where().eq("domain", instanceDomain).eq("language", languages)
						.findUnique();
				session.setAttribute(Const.SESSION_WEB_SET, webSet);
				terminal = changeTerminal(webSet, request);
			} else {
				// 说明用户做了域名切换，销毁session,阻止不同域名间信息共享
				if (!domain.equals(instanceDomain)) {
					session.invalidate();
					session = request.getSession(true);
					// 重新加载
					session.setAttribute(Const.SESSION_DOMAIN, instanceDomain);
					SmWebSetTbl webSet = SmWebSetTbl.find.where().eq("domain", instanceDomain).eq("language", languages)
							.findUnique();
					session.setAttribute(Const.SESSION_WEB_SET, webSet);
					terminal = changeTerminal(webSet, request);
				} else {
					// 如果切换语言版本，重新setSession数据
					if (StringUtils.isNotBlank(language) && oLanguage != null && !language.equals(oLanguage)) {
						SmWebSetTbl webSet = SmWebSetTbl.find.where().eq("domain", instanceDomain)
								.eq("language", languages).findUnique();
						session.setAttribute(Const.SESSION_WEB_SET, webSet);
						terminal = changeTerminal(webSet, request);
					}
				}
			}

			if (StringUtils.isNotBlank(terminal)) {
				request.getSession(true).setAttribute("terminal", terminal);
			}
			System.err.println("拦截器：" + request.getSession().getAttribute("terminal"));
		} catch (Exception e) {
			logger.error("Exception throws:", e);
		}
		return true;
	}

	/**
	 * 判断网站的类型；如果是单独pc或者响应式并且终端是非pc；就强制将终端
	 * 
	 * @param webSet
	 * @param request
	 * @return
	 */
	private String changeTerminal(SmWebSetTbl webSet, HttpServletRequest request) {
		String terminal = request.getParameter("terminal");
		if(DeviceUtils.getDeviceType(request)!=1) {
			terminal = "wx";
		}
		System.out.println("terminal:"+terminal);
		if (StringUtils.isBlank(terminal) && webSet.getType() != null  && DeviceUtils.getDeviceType(request) != 1) {
			System.out.println("强制设置pc ");
			terminal = "pc";
		}
		System.out.println("terminal后台:"+terminal);
		return terminal;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);

		if (modelAndView != null && StringUtils.isNoneBlank(modelAndView.getViewName())
				&& !StringUtils.contains(modelAndView.getViewName(), "error/")) {
			Map<String, Object> model = modelAndView.getModel();
			// 用户信息处理
			if (model.get("user") != null) {
				session.setAttribute(Const.SESSION_USER, model.get("user"));
			} else {
				if (session.getAttribute(Const.SESSION_USER) != null) {
					model.put("user", session.getAttribute(Const.SESSION_USER));
				}
			}
			// 站点信息处理
			if (model.get(Const.WEB_SET) != null) {
				session.setAttribute(Const.SESSION_WEB_SET, model.get(Const.WEB_SET));
			} else {
				if (session.getAttribute(Const.SESSION_WEB_SET) != null) {
					model.put(Const.WEB_SET, session.getAttribute(Const.SESSION_WEB_SET));
				}
			}

			SmWebSetTbl webset = (SmWebSetTbl) session.getAttribute(Const.SESSION_WEB_SET);
			// 网站关闭拦截
			if (StringUtils.contains(request.getRequestURL(), "/error/close.htm") && webset != null
					&& webset.getState() == 1) {
				// 进入首页
				response.sendRedirect(request.getContextPath() + "/index.htm");
			} else if (webset != null && webset.getState() != 1
					&& !StringUtils.contains(request.getRequestURL(), "/admin")
					&& !StringUtils.contains(request.getRequestURL(), "/general_service")
					&& !StringUtils.contains(request.getRequestURL(), "/error/close.htm")) {
				response.sendRedirect(request.getContextPath() + "/error/close.htm");
			}

			// 增加标签
			model.put("ds", defSet);
			model.put("at", new At(webset));
			model.put("cl", new Cl(webset));
			model.put("fl", new Fl(webset));
			model.put("sc", new Sc(webset));
			model.put("req", request);
			model.put("cm", new Cm(Const.BASE, webset));

		}
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 *
	 * @param s
	 * @return
	 */
	private static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
			case '\'':
				sb.append('‘');// 全角单引号
				break;
			case '\"':
				sb.append('“');// 全角双引号
				break;
			case '&':
				sb.append('＆');// 全角
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '#':
				sb.append('＃');// 全角井号
				break;
			case '%': // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c
				processUrlEncoder(sb, s, i);
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	public static void processUrlEncoder(StringBuilder sb, String s, int index) {
		if (s.length() >= index + 2) {
			if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'c' || s.charAt(index + 2) == 'C')) { // %3c,
																											// %3C
				sb.append('＜');
				return;
			}
			if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '0') { // %3c
																			// (0x3c=60)
				sb.append('＜');
				return;
			}
			if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'e' || s.charAt(index + 2) == 'E')) { // %3e,
																											// %3E
				sb.append('＞');
				return;
			}
			if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '2') { // %3e
																			// (0x3e=62)
				sb.append('＞');
				return;
			}
		}
		sb.append(s.charAt(index));
	}

	/**
	 * 判断参数是否含有攻击串
	 * 
	 * @param value
	 * @return
	 */
	public static boolean judgeSQLInject(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		String xssStr = "alert|delete|truncate|script|prompt|http"
				+ "oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete"
				+ "|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|"
				+ "ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|"
				+ "onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture"
				+ "|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|"
				+ "onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|"
				+ "onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|"
				+ "onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|"
				+ "onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize"
				+ "|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|confirm"
				+ "|htmlspecialchars|htmlentities|strip_tags|header(|urlencode(|intval("
				+ "|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload|window|location|document|base64,|prompt";
		String[] xssArr = xssStr.split("\\|");
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1) {
				return true;
			}
		}
		return false;
	}

}