package com.layduo.framework.util;
/**
 * shiro工具类
* @author layduo
* @createTime 2019年11月8日 上午9:27:57
*/

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.layduo.common.utils.StringUtils;
import com.layduo.common.utils.bean.BeanUtils;
import com.layduo.framework.shiro.realm.UserRealm;
import com.layduo.system.domain.SysUser;

public class ShiroUtils {

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static void logout() {
		getSubject().logout();
	}

	public static SysUser getSysUser() {
		SysUser user = null;
		Object obj = getSubject().getPrincipal();
		if (StringUtils.isNotNull(obj)) {
			user = new SysUser();
			BeanUtils.copyBeanProp(user, obj);
		}
		return user;
	}

	public static void setSysUser(SysUser user) {
		Subject subject = getSubject();
		PrincipalCollection principalCollection = subject.getPrincipals();
		String realmName = principalCollection.getRealmNames().iterator().next();
		PrincipalCollection newprincipalCollection = new SimplePrincipalCollection(user, realmName);
		// 重新加载Principal
		subject.runAs(newprincipalCollection);
	}

	public static void clearCachedAuthorizationInfo() {
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
		realm.clearCachedAuthorizationInfo();
	}

	public static Long gerUserId() {
		return getSysUser().getUserId().longValue();
	}

	public static String getLoginName() {
		return getSysUser().getLoginName();
	}

	public static String getIp() {
		return getSubject().getSession().getHost();
	}

	public static String getSessionId() {
		return String.valueOf(getSubject().getSession().getId());
	}

	/**
	 * 生成随机盐
	 * 
	 * @return
	 */
	public static String randomSalt() {
		// 一个Byte占两个字节，此处生成的3字节，字符串长度为6
		SecureRandomNumberGenerator secureRaandom = new SecureRandomNumberGenerator();
		String hex = secureRaandom.nextBytes(3).toHex();
		return hex;
	}

}
