package com.tjsj.fwk.mvc.shiro;

import java.util.concurrent.TimeUnit;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tjsj.wp.orm.entity.SmUserTbl;

public class DefaultHashedCredentialsMatcher extends SimpleCredentialsMatcher {

	private final int retryCount = 4;

	private final String RETRY = "shiro-retry";

	private Cache<String, Integer> shiroCache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES)
			.maximumSize(1).build();

	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		Integer retry = shiroCache.getIfPresent(RETRY);
		if (retry != null) {
			if (retry > this.getRetryCount()) {
				SmUserTbl user = (SmUserTbl) SmUserTbl.find.where()
						.eq("username", info.getPrincipals().getPrimaryPrincipal()).findUnique();
				user.setIsLocked(2);// 锁定用户
				user.update();
			}
		}
		boolean matcher = false;
		matcher = super.doCredentialsMatch(token, info);
		if (!matcher) {
			if (retry == null) {
				retry = 1;
			}
			// 增加错误次数
			shiroCache.put(RETRY, retry + 1);
			String errMsg = this.retryCount > retry ? "密码错误，还有" + (this.retryCount - retry) + "次机会" : "密码错误，再次错误将会锁定账号";
			throw new IncorrectCredentialsException(errMsg);
		} else {
			if (retry != null) {
				shiroCache.invalidate(RETRY);
			}
		}
		return matcher;
	}

	public int getRetryCount() {
		return retryCount;
	}

}
