package com.layduo.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.layduo.common.constant.Constants;
import com.layduo.common.constant.ShiroConstants;
import com.layduo.common.constant.UserConstants;
import com.layduo.common.enums.UserStatus;
import com.layduo.common.exception.user.CaptchaException;
import com.layduo.common.exception.user.UserBlockedException;
import com.layduo.common.exception.user.UserDeleteException;
import com.layduo.common.exception.user.UserNotExistsException;
import com.layduo.common.exception.user.UserPasswordNotMatchException;
import com.layduo.common.utils.DateUtils;
import com.layduo.common.utils.MessageUtils;
import com.layduo.common.utils.ServletUtils;
import com.layduo.framework.manager.AsyncManager;
import com.layduo.framework.manager.factory.AsyncFactory;
import com.layduo.framework.util.ShiroUtils;
import com.layduo.system.domain.SysUser;
import com.layduo.system.service.ISysUserService;

/**
 * 登录校验方法
* @author layduo
* @createTime 2019年11月11日 上午10:23:46
*/
@Component
public class SysLoginService {

	@Autowired
	private SysPasswordService passwordService;
	
	@Autowired
	private ISysUserService userService;
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public SysUser login(String username, String password) {
		
		//验证码校验
		String captacha = (String) ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA);
		if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
		//用户名或密码为空 错误
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
			throw new UserNotExistsException();
		}
		//用户名不在指定范围内 错误
		if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}
		//密码不在指定范围内 错误
		if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}
		//查询用户信息
		SysUser user = userService.selectUserByLoginName(username);
		
		//用手机号码登录
		if (user == null && maybeMobilePhoneNumber(username)) {
			user = userService.selectUserByPhoneNumber(username);
		}
		//用电子邮箱登录
		if (user == null && maybeEmail(username)) {
			user = userService.selectUserByEmail(username);
		}
		//校验用户不存在
		if (user == null) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
			throw new UserNotExistsException();
		}
		//校验用户删除状态
		if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
			throw new UserDeleteException();
		}
		//校验用户状态
		if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
			throw new UserBlockedException();
		}
		
		//校验用户密码
		passwordService.validate(user, password);
		
		//校验成功，添加登录记录
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
		//记录用户登录IP和时间
		recordLoginInfo(user);
		return user;
		
	}
	
	private boolean maybeEmail(String username) {
		if (!username.matches(UserConstants.EMAIL_PATTERN)) {
			return false;
		}
		return true;
	}
	
	private boolean maybeMobilePhoneNumber(String username) {
		if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 记录登录信息
	 * @param user
	 */
	public void recordLoginInfo(SysUser user) {
		user.setLoginIp(ShiroUtils.getIp());
		user.setLoginDate(DateUtils.getNowDate());
		userService.updateUserInfo(user);
	}
}
