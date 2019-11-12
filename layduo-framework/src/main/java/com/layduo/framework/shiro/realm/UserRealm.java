package com.layduo.framework.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.layduo.common.exception.user.CaptchaException;
import com.layduo.common.exception.user.RoleBlockedException;
import com.layduo.common.exception.user.UserBlockedException;
import com.layduo.common.exception.user.UserNotExistsException;
import com.layduo.common.exception.user.UserPasswordNotMatchException;
import com.layduo.common.exception.user.UserPasswordRetryLimitExceedException;
import com.layduo.common.utils.StringUtils;
import com.layduo.framework.shiro.service.SysLoginService;
import com.layduo.system.domain.SysUser;

/**
 * 自定义Realm处理登录认证授权
 * 
 * @author layduo
 * @createTime 2019年11月8日 上午10:18:12
 */
public class UserRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private SysLoginService loginService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = StringUtils.EMPTY;
		if (upToken.getPassword() != null) {
			password = new String(upToken.getPassword());
		}
		SysUser user = null;
		try {
			user = loginService.login(username, password);
		} catch (CaptchaException e) {
			throw new AuthenticationException(e.getMessage(), e);
		} catch (UserNotExistsException e) {
			throw new UnknownAccountException(e.getMessage(), e);
		} catch (UserPasswordNotMatchException e) {
			throw new IncorrectCredentialsException(e.getMessage(), e);
		} catch (UserPasswordRetryLimitExceedException e) {
			throw new ExcessiveAttemptsException(e.getMessage(), e);
		} catch (UserBlockedException e) {
			throw new LockedAccountException(e.getMessage(), e);
		} catch (RoleBlockedException e) {
			throw new LockedAccountException(e.getMessage(), e);
		} catch (Exception e) {
			log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
			throw new AuthenticationException(e.getMessage(), e);
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

	/**
	 * 清理缓存权限
	 */
	public void clearCachedAuthorizationInfo() {
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

}
