
package com.tjsj.fwk.mvc.shiro;

import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.google.common.collect.Sets;
import com.tjsj.base.constant.Const;
import com.tjsj.wp.orm.entity.SmModelTbl;
import com.tjsj.wp.orm.entity.SmRoleTbl;
import com.tjsj.wp.orm.entity.SmUserTbl;

/**
 * 权限
 * 
 * @author killfenQQ:78750478
 * @version 版本号：<br/>
 *          创建日期：2015年9月9日<br/>
 *          历史修订：<br/>
 */
public class ShiroRealm extends AuthorizingRealm {

	/**
	 * 构造
	 */
	public ShiroRealm() {
		// TODO Auto-generated constructor stub
		super();
		// 设置认证token的实现类
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		// 设置加密算法
		setCredentialsMatcher(new DefaultHashedCredentialsMatcher());
	}

	/**
	 * 功能操作授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) principalCollection.getPrimaryPrincipal();
		SmUserTbl user = null;
		String[] usernames = username.split("\\|");
		if (usernames.length < 2) {
			throw new AccountException();
		}

		if (usernames.length >= 3 && usernames[2].equals("token")) {
			// 因为token是唯一的，直接通过token检索用户；
			user = SmUserTbl.find.where().eq("token", usernames[0]).eq("webSet.domain", usernames[1]).findUnique();
		} else {
			// 用户名密码登录
			if (usernames[0].equalsIgnoreCase(Const.ADMIN)) {
				user = SmUserTbl.find.where().eq("username", usernames[0]).findUnique();
			} else {
				user = SmUserTbl.find.where().eq("username", usernames[0]).eq("webSet.domain", usernames[1])
						.findUnique();
			}
		}

		SimpleAuthorizationInfo autInfo = new SimpleAuthorizationInfo();
		// 角色名的集合
		Set<String> roles = Sets.newHashSet();
		// 权限名的集合
		Set<String> permissions = Sets.newHashSet();

		Iterator<SmRoleTbl> it = user.getRoles().iterator();
		while (it.hasNext()) {
			roles.add(it.next().getKeyword());
			for (SmModelTbl model : it.next().getModels()) {
				permissions.add(model.getUrl());
			}
		}
		autInfo.addRoles(roles);
		autInfo.addStringPermissions(permissions);
		return autInfo;

	}

	/**
	 * 新登用户验证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		SmUserTbl user = null;
		String[] usernames = username.split("\\|");
		if (usernames.length < 2) {
			throw new AccountException();
		}

		if (usernames.length >= 3 && usernames[2].equals("token")) {
			// 因为token是唯一的，直接通过token检索用户；
			user = SmUserTbl.find.where().eq("token", usernames[0]).eq("webSet.domain", usernames[1]).findUnique();
		} else {
			// 用户名密码登录
			if (usernames[0].equalsIgnoreCase(Const.ADMIN)) {
				user = SmUserTbl.find.where().eq("username", usernames[0]).findUnique();
			} else {
				user = SmUserTbl.find.where().eq("username", usernames[0]).eq("webSet.domain", usernames[1])
						.findUnique();
			}
		}

		if (user == null) {
			// 木有找到用户
			throw new UnknownAccountException("没有此用户");
		}
		// 1 未锁定，2-已锁定
		if (user.getIsLocked() == 2) {
			throw new LockedAccountException();
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
		return info;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}