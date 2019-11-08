package com.layduo.framework.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.layduo.common.utils.StringUtils;
import com.layduo.system.domain.SysUser;

/**
 * 自定义Realm处理登录认证授权
 * 
 * @author layduo
 * @createTime 2019年11月8日 上午10:18:12
 */
public class UserRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();
		String passWord = StringUtils.EMPTY;
		if (upToken.getPassword() != null) {
			passWord = new String(upToken.getPassword());
		}
		SysUser user = null;
		
		return null;
	}
	
	/**
	 * 清理缓存权限
	 */
	public void clearCachedAuthorizationInfo() {
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

}
