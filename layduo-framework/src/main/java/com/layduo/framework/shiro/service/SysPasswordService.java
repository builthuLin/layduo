package com.layduo.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.layduo.common.constant.Constants;
import com.layduo.common.constant.ShiroConstants;
import com.layduo.common.exception.user.UserPasswordNotMatchException;
import com.layduo.common.exception.user.UserPasswordRetryLimitExceedException;
import com.layduo.common.utils.MessageUtils;
import com.layduo.framework.manager.AsyncManager;
import com.layduo.framework.manager.factory.AsyncFactory;
import com.layduo.system.domain.SysUser;

/**
 * 登录密码处理方法
 * 
 * @author layduo
 * @createTime 2019年11月8日 上午10:36:19
 */
@Component
public class SysPasswordService {

	@Autowired
	private CacheManager cacheManager;

	private Cache<String, AtomicInteger> loginRecordCache;

	@Value(value = "${user.password.maxRetryCount}")
	private String maxRetryCount;

	@PostConstruct
	public void init() {
		loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
	}

	public void validate(SysUser user, String password) {
		String loginName = user.getLoginName();

		// 使用AtomicInteger类提供原子操作，线程安全
		AtomicInteger retryCount = loginRecordCache.get(loginName);

		// 第一次查询缓存不存在，把信息缓存
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			loginRecordCache.put(loginName, retryCount);
		}

		// 输入密码次数超过最大值
		if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
			throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
		}

		// 用户输入密码不匹配
		if (!matches(user, password)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
			loginRecordCache.put(loginName, retryCount);
			throw new UserPasswordNotMatchException();
		} 
		else {
			clearLoginRecordCache(loginName);
		}
	}

	/**
	 * 校验用户密码
	 * 
	 * @param user
	 * @param newPassword
	 * @return
	 */
	public boolean matches(SysUser user, String newPassword) {
		return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
	}

	/**
	 * 清除用户信息缓存
	 * 
	 * @param username
	 */
	public void clearLoginRecordCache(String username) {
		loginRecordCache.remove(username);
	}

	/**
	 * 对用户信息加密
	 * 
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 */
	public String encryptPassword(String username, String password, String salt) {
		return new Md5Hash(username + password + salt).toHex().toString();
	}

	public void unlock(String loginName) {
		loginRecordCache.remove(loginName);
	}
	
}
