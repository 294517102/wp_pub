package com.tjsj.wp.mvc.controller.portal;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.tjsj.base.constant.Const;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.codec.MD5;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.m_util.json.FastjsonPropertyPreFilter;
import com.tjsj.m_util.string.StringUtil;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.SmUserTbl;
import com.tjsj.wp.tools.RSAUtils;

/**
 * @author gongdzh
 *
 */
@Controller
public class LoginMaintenanceController extends WpBaseController {
	// 日志文件
	private static Logger logger = Logger.getLogger(LoginMaintenanceController.class);

	@RequestMapping("/admin/obtain_login")
	public ModelAndView obtain_admin_login(HttpServletRequest request, HttpServletResponse response) {
		String retUrl = request.getHeader("Referer");

		ModelAndView mv = createBgView("login", request, response);

		return mv;
	}

	@RequestMapping("/Cz-webguanli")
	public ModelAndView webguanli(HttpServletRequest request, HttpServletResponse response) {
		String retUrl = request.getHeader("Referer");
		ModelAndView mv = createBgView("login", request, response);

		return mv;
	}

	// 公共检查是否登陆，临时
	@RequestMapping("checkLogin")
	public void checklogin(HttpServletRequest request, HttpServletResponse response) {
		ResultJson rj = new ResultJson();
		SmUserTbl user = getLoginUser();
		if (user == null) {
			rj.setResult(false);
			rj.setResultMsg("请登录");
			outJson(response, rj);
		}
		rj.setResult(true);
		rj.setResultData(user);
		outJson(response, rj);
	}

	@RequestMapping("/user/obtain_login")
	public ModelAndView obtain_user_login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = createView("login", request, response);
		return mv;
	}

	// 加载系统首页
	@RequestMapping("/admin/login_success")
	public String login_success(HttpServletRequest request, HttpServletResponse response) {
		// 加载用户信息
		return "redirect:/admin/obtain_home_view.htm";
	}

	// 加载系统首页
	@RequestMapping("/user/login_success")
	public String user_success(HttpServletRequest request, HttpServletResponse response) {
		// 加载用户信息

		return "redirect:/index.htm";
	}

	@RequestMapping("/user/changePassword")
	public void changePassword(HttpServletRequest request, HttpServletResponse response, String oldPassword,
			String newPassword, String newAckPassword) {
		ResultJson rj = new ResultJson();
		try {
			if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
				throw new ParameterException("参数错误");
			}
			if (!newPassword.equals(newAckPassword)) {
				throw new ParameterException("两次密码输入不一致");
			}
			if (!MD5.md5(oldPassword).equalsIgnoreCase(this.getLoginUser().getPassword())) {
				throw new ParameterException("旧密码错误");
			}
			this.getLoginUser().setPassword(MD5.md5(newPassword));
			this.getLoginUser().save();

			rj.setResultMsg("修改成功");
			rj.setResult(true);
		} catch (Exception e) {
			rj.setResultMsg(e.getMessage());
			rj.setResult(false);
		}

		outJson(response, rj);
	}

	/**
	 * 找回密码
	 * 
	 * @param request
	 * @param response
	 * @param password
	 */
	@RequestMapping("/user/fetchPassword_error")
	public void testUpdateUser(HttpServletRequest request, HttpServletResponse response, String password) {
		/*
		 * ResultJson rj = new ResultJson(); //1.检验参数 String phone = (String)
		 * SecurityUtils.getSubject().getSession().getAttribute(Const.
		 * PHONE_NUMBER); try { if
		 * (StringUtil.isBlank(password)||!password.matches("\\w{6,12}")) {
		 * throw new RuntimeException("请输入6~12位的密码"); } if
		 * (StringUtil.isBlank(phone)) { throw new RuntimeException("找回失败"); }
		 * SmUserTbl user = SmUserTbl.find.where().eq("username",
		 * phone).findUnique(); if (user==null) { rj.setInfo("找回失败");
		 * rj.setResult(false); outJson(response, rj); return ; }
		 * user.setPassword(MD5.md5(password)); user.save();
		 * System.out.println("更新成功"); rj.setInfo("找回成功"); rj.setResult(true);
		 * outJson(response, rj); } catch (Exception e) {
		 * rj.setInfo(e.getMessage()); rj.setResult(false); outJson(response,
		 * rj); }
		 */
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param password
	 */
	@RequestMapping("/fetchPassword")
	public void fetchPassword(HttpServletRequest request, HttpServletResponse response, String password,
			String checkCode, String phone) {
		/*
		 * ResultJson rj = new ResultJson(); //1.检验参数 try {
		 * if(StringUtils.isBlank(password)||StringUtils.isBlank(checkCode)||
		 * StringUtils.isBlank(phone)){ throw new ParameterException("参数错误"); }
		 * Map<String,Object> cMap = (Map<String, Object>)
		 * request.getSession().getAttribute(Const.SMS_VERIFY_CODE);
		 * if(!checkCode.equals(cMap.get("code"))||
		 * !phone.equals(cMap.get("phone"))){ throw new
		 * ParameterException("短信验证码错误"); }
		 * 
		 * String reg = "^[0-9A-Za-z]{6,20}$"; if (!password.matches(reg)) {
		 * throw new RuntimeException("请输入6~20位的密码"); }
		 * 
		 * SmUserTbl user = SmUserTbl.find.where().eq("username",
		 * phone).findUnique(); if (user==null) { throw new
		 * RuntimeException("用户不存在"); } user.setPassword(MD5.md5(password));
		 * user.update(); rj.setInfo("找回成功"); rj.setResult(true);
		 * outJson(response, rj); } catch (Exception e) {
		 * rj.setInfo(e.getMessage()); rj.setResult(false); outJson(response,
		 * rj); }
		 */
	}

	public void auto_login(HttpServletRequest request, HttpServletResponse response, String devId, String userName,
			String password) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return;
		}
		Subject subject = SecurityUtils.getSubject();
		Session session = getSubjectSession();
		// 追加domain
		String instanceDomain = null;
		Object domain = session.getAttribute(Const.SESSION_DOMAIN);
		if (domain != null) {
			instanceDomain = (String) domain;
		} else {
			instanceDomain = request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + Const.BASE;
			instanceDomain = instanceDomain.replace("/", "_");
			if (instanceDomain.indexOf(":") > 0) {
				instanceDomain = instanceDomain.replace(":", "_");
			}
		}
		// 由于需要兼容老版本的用户，这里试用另外一种md5加密方法
		UsernamePasswordToken token = null;
		String enPassword = null;
		try {
			enPassword = MD5.md5(password);
			token = new UsernamePasswordToken(userName + "|" + instanceDomain, enPassword);
			token.setRememberMe(false);

			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			subject.login(token);
		} catch (Exception e) {
			logger.error("抛出异常：", e);
		}
		// 验证是否登录成功
		if (subject.isAuthenticated()) {

			// SmUserTbl user = SmUserTbl.find.where().eq("devId",
			// devId).eq("token", token).findUnique();
			SmUserTbl user = SmUserTbl.find.where().eq("username", userName).eq("password", enPassword).findUnique();

			// 记录用户登录信息
			user.setLast_ip_address(getRemoteAddress(request));

			if (StringUtils.isNoneBlank(devId)) {
				String strToken = user.getId() + "-" + UUID.randomUUID().toString();
				user.setDevId(devId);
				user.setToken(strToken);
				response.setHeader("TOKEN", strToken);
			}

			user.setLastLogin(new Date());
			user.update();

			session.setAttribute(Const.SESSION_USER, user);

		} else {
			token.clear();
		}

	}

	public static SmUserTbl auto_login_2(HttpServletRequest request, HttpServletResponse response, String devId,
			String token, String domain) {

		if (StringUtils.isBlank(devId) || StringUtils.isBlank(token)) {
			return null;
		}

		Subject subject = SecurityUtils.getSubject();
		Session session = SecurityUtils.getSubject().getSession();
		// 追加domain
		String instanceDomain = null;

		// 由于需要兼容老版本的用户，这里试用另外一种md5加密方法
		UsernamePasswordToken uToken = null;
		try {
			logger.error("自动登录-" + devId);

			uToken = new UsernamePasswordToken(token + "|" + domain + "|token", devId);
			uToken.setRememberMe(false);
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			subject.login(uToken);
		} catch (Exception e) {
			logger.error("抛出异常：", e);
			return null;
		}
		// 验证是否登录成功
		if (subject.isAuthenticated()) {
			logger.debug("登录成功");
			// 登录成功后，更新用户登录信息
			SmUserTbl user = SmUserTbl.find.where().eq("devId", devId).eq("token", token).findUnique();

			// 记录用户登录信息
			user.setLast_ip_address(getRemoteAddress(request));

			if (StringUtils.isNoneBlank(devId)) {
				String strToken = user.getId() + "-" + UUID.randomUUID().toString();
				user.setDevId(devId);
				user.setToken(strToken);
				response.setHeader("TOKEN", strToken);
			}

			user.setLastLogin(new Date());
			user.update();

			session.setAttribute(Const.SESSION_USER, user);

			return user;
		} else {
			uToken.clear();
		}
		return null;
	}

	/**
	 * @param request
	 * @param response
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param verifyCode
	 *            校验码
	 * @author gongdzh 用户登录，当终端提交dev_id服服务端会生产token， token可作为下次一键登录使用
	 *         2016年12月26日 下午1:52:06
	 */
	@RequestMapping("/admin/login")
	public void admin_login(HttpServletRequest request, HttpServletResponse response, String userName, String password,
			String verifyCode, String weather, String modulus) {
		Session session = getSubjectSession();
		// 校验
		ResultJson rj = new ResultJson();
		String devId = request.getHeader("DEV_ID");
		/* 开发过程中先取消 */
		logger.info("login: username-" + userName + ",verifyCode-" + verifyCode);
		if (session.getAttribute("verify_code") == null
				|| StringUtils.isBlank(session.getAttribute("verify_code").toString())
				|| !session.getAttribute("verify_code").toString().equalsIgnoreCase(verifyCode)) {
			rj.setResult(false);
			rj.setResultMsg("验证码错误");
			outJson(response, rj);
			session.removeAttribute("verify_code");
			return;
		}

		if (StringUtils.isBlank(userName)) {
			rj.setResult(false);
			rj.setResultMsg("用户名不能为空");
			outJson(response, rj);
			return;
		}

		if (StringUtils.isBlank(password)) {
			rj.setResult(false);
			rj.setResultMsg("密码不能为空");
			outJson(response, rj);
			return;
		}
		if (StringUtils.isNotBlank(modulus)) {
			// 解密
			userName = RSAUtils.decryptStringByJs(userName);
			password = RSAUtils.decryptStringByJs(password);
			System.out.println("解密数据：userName：" + userName + "===password:" + password);

		}

		// 清理验证码
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		Subject subject = SecurityUtils.getSubject();

		// 追加domain
		String instanceDomain = null;
		Object domain = session.getAttribute(Const.SESSION_DOMAIN);
		if (domain != null) {
			instanceDomain = (String) domain;
		} else {
			instanceDomain = request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + Const.BASE;
			instanceDomain = instanceDomain.replace("/", "_");
			if (instanceDomain.indexOf(":") > 0) {
				instanceDomain = instanceDomain.replace(":", "_");
			}
		}

		// UsernamePasswordToken token = new
		// UsernamePasswordToken(userName+"|"+instanceDomain,
		// MD5.md5(password));
		// 由于需要兼容老版本的用户，这里试用另外一种md5加密方法
		UsernamePasswordToken token = null;
		try {

			token = new UsernamePasswordToken(userName + "|" + instanceDomain, MD5.md5(password));
			token.setRememberMe(false);

			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			subject.login(token);
		} catch (UnknownAccountException uae) {
			rj.setResult(false);
			rj.setResultMsg("用户名或密码错误");
		} catch (IncorrectCredentialsException ice) {
			rj.setResult(false);
			rj.setResultMsg(ice.getMessage());
		} catch (LockedAccountException lae) {
			rj.setResult(false);
			rj.setResultMsg("账号已锁定");
		} catch (ExcessiveAttemptsException eae) {
			rj.setResult(false);
			rj.setResultMsg("错误次数超限");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			rj.setResult(false);
			rj.setResultMsg("用户名或密码错误");
		} catch (Exception e) {
			rj.setResult(false);
			rj.setResultMsg("错误");
		}
		// 验证是否登录成功
		if (subject.isAuthenticated()) {
			rj.setResult(true);
			rj.setResultMsg("登录成功");
			logger.debug("登录成功");
			// 登录成功后，更新用户登录信息
			SmUserTbl user = SmUserTbl.find.where().eq("username", userName).findUnique();
			if (user.getUsername().equalsIgnoreCase(Const.ADMIN)) {
				user.setWebSet(this.getWebSet(request));
			}
			FastjsonPropertyPreFilter fp = new FastjsonPropertyPreFilter();

			rj.setInfo(JSON.toJSONString(user, fp));
			// 记录用户登录信息
			user.setLast_ip_address(getRemoteAddress(request));
			user.setLastLogin(new Date());
			// 针对提交devid的终端，生成token
			if (StringUtils.isNoneBlank(devId)) {
				String strToken = user.getId() + "-" + UUID.randomUUID().toString();
				user.setDevId(devId);
				user.setToken(strToken);
				response.setHeader("TOKEN", strToken);
			}

			user.update();

			session.setAttribute(Const.SESSION_USER, user);
			// 设置天气
			if (StringUtils.isBlank(weather)) {
				weather = "夜";
			}
			switch (weather) {
			case "阴":
				session.setAttribute(Const.SESSION_WEATHER, "cloudy");
				break;
			case "晴":
				session.setAttribute(Const.SESSION_WEATHER, "sun");
				break;
			case "雨":
				session.setAttribute(Const.SESSION_WEATHER, "rain");
				break;
			case "雪":
				session.setAttribute(Const.SESSION_WEATHER, "snow");
				break;
			case "夜":
				session.setAttribute(Const.SESSION_WEATHER, "night");
				break;
			default:
				break;
			}

		} else {
			token.clear();
		}

		outJson(response, rj);
		return;
	}

	@RequestMapping(value = "/getPublicKey.htm", method = { RequestMethod.POST })
	public void encryptionWithPublicKey(HttpServletRequest request, HttpServletResponse response) {
		ResultJson rj = new ResultJson();
		try {
			RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
			// 公钥-系数(n)
			System.out.println("public key modulus===:" + new String(Hex.encode(publicKey.getModulus().toByteArray())));
			// 公钥-指数(e1)
			System.out.println(
					"public key exponent===:" + new String(Hex.encode(publicKey.getPublicExponent().toByteArray())));
			Map<String, String> map = new HashMap<String, String>();
			map.put("modulus", new String(Hex.encode(publicKey.getModulus().toByteArray())));
			map.put("exponent", new String(Hex.encode(publicKey.getPublicExponent().toByteArray())));
			rj.setResult(true);
			rj.setResultData(map);
			outJson(response, rj);
		} catch (Exception e) {
			e.printStackTrace();
			rj.setResult(false);
			rj.setResultData(e.getMessage());
			outJson(response, rj);
		}
	}

	/**
	 * pc登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("vip/user_login")
	public ModelAndView pc_user_login(HttpServletRequest request, HttpServletResponse response, String userName,
			String password, String verifyCode) {
		Session session = getSubjectSession();
		ModelAndView mv = createView("", request, response);
		if (StringUtils.isBlank(userName)) {
			addResult(mv, false, "用户名不能为空");
			return mv;
		}

		if (StringUtils.isBlank(password)) {
			addResult(mv, false, "密码不能为空");
			return mv;
		}
		if (StringUtils.isNotBlank(verifyCode)) {
			if (session.getAttribute("verify_code") == null
					|| StringUtils.isBlank(session.getAttribute("verify_code").toString())
					|| !session.getAttribute("verify_code").toString().equalsIgnoreCase(verifyCode)) {
				addResult(mv, false, "验证码错误");
				return mv;
			}
		}
		// 清理验证码
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		Subject subject = SecurityUtils.getSubject();

		// 追加domain
		String instanceDomain = null;
		Object domain = session.getAttribute(Const.SESSION_DOMAIN);
		if (domain != null) {
			instanceDomain = (String) domain;
		} else {
			instanceDomain = request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + Const.BASE;
			instanceDomain = instanceDomain.replace("/", "_");
			if (instanceDomain.indexOf(":") > 0) {
				instanceDomain = instanceDomain.replace(":", "_");
			}
		}

		// UsernamePasswordToken token = new
		// UsernamePasswordToken(userName+"|"+instanceDomain,
		// MD5.md5(password));
		// 由于需要兼容老版本的用户，这里试用另外一种md5加密方法
		UsernamePasswordToken token = null;
		try {

			token = new UsernamePasswordToken(userName + "|" + instanceDomain, MD5.md5(password));
			token.setRememberMe(false);
			SmUserTbl usera = SmUserTbl.find.where().eq("username", userName).findUnique();
			if (StringUtil.isBlank(usera)) {
				throw new UnknownAccountException();
			}
			if (usera.getIsLocked() == -1) {
				addResult(mv, false, "您的账号还未通过审核，暂不能登录");
				return mv;
			} else {
				// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
				// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
				// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
				subject.login(token);
				// 验证是否登录成功
				if (subject.isAuthenticated()) {
					// 登录成功后，更新用户登录信息
					SmUserTbl user = SmUserTbl.find.where().eq("username", userName).findUnique();
					if (user.getUsername().equalsIgnoreCase(Const.ADMIN)) {
						user.setWebSet(this.getWebSet(request));
					}
					logger.debug("登录成功");
					addResult(mv, true, "登录成功");
					// 记录用户登录信息
					user.setLast_ip_address(getRemoteAddress(request));
					user.setLastLogin(new Date());
					user.update();
					session.setAttribute("usere", user);

				} else {
					addResult(mv, false, "登录失败，请稍后再试");
					token.clear();
				}
			}
		} catch (UnknownAccountException uae) {
			addResult(mv, false, "用户不存在");
			return mv;
		} catch (IncorrectCredentialsException ice) {
			addResult(mv, false, "用户名或密码错误");
			return mv;
		} catch (LockedAccountException lae) {
			addResult(mv, false, "账号已锁定");
			return mv;
		} catch (ExcessiveAttemptsException eae) {
			addResult(mv, false, "错误次数超限");
			return mv;
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			addResult(mv, false, "用户名或密码错误");
			return mv;
		} catch (Exception e) {
			addResult(mv, false, "错误");
			return mv;
		}
		return mv;
	}

	@RequestMapping("/user/logout")
	public ModelAndView pc_user_login(HttpServletRequest request, HttpServletResponse response, String url) {
		System.out.println("退出登录");
		if (StringUtils.isBlank(url)) {
			url = "index";
		}
		ModelAndView mv = createView(url, request, response);
		try {
			if (SecurityUtils.getSubject().getPrincipal() != null) {
				// SecurityUtils.getSubject().logout();
				request.getSession().removeAttribute("usere");
				System.out.println("退出登录");

			}
			addResult(mv, true, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			addResult(mv, false, e.getMessage());
		}
		return mv;
	}

	/**
	 * 用户登出
	 */
	@RequestMapping("/admin/logout")
	public String logout(HttpServletRequest request) {
		SecurityUtils.getSubject().logout();
		logger.debug("退出了登录！");
		return "redirect:/admin/obtain_login.htm";
	}

}
