package com.layduo.framework.manager.factory;
/**
 * 异步工厂 （产生任务用）
* @author layduo
* @createTime 2019年11月8日 下午2:20:04
*/

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.layduo.common.constant.Constants;
import com.layduo.common.utils.AddressUtils;
import com.layduo.common.utils.ServletUtils;
import com.layduo.common.utils.spring.SpringUtils;
import com.layduo.framework.shiro.session.OnlineSession;
import com.layduo.framework.util.LogUtils;
import com.layduo.framework.util.ShiroUtils;
import com.layduo.system.domain.SysLogininfor;
import com.layduo.system.domain.SysOperLog;
import com.layduo.system.domain.SysUserOnline;
import com.layduo.system.service.ISysOperLogService;
import com.layduo.system.service.ISysUserOnlineService;
import com.layduo.system.service.impl.SysLogininforServiceImpl;

import eu.bitwalker.useragentutils.UserAgent;

public class AsyncFactory {

	private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");
	
	/**
	 * 同步session到数据库
	 * @param session
	 * @return
	 */
	public static TimerTask syncSessionToDb(final OnlineSession session) {
		return new TimerTask() {
			
			@Override
			public void run() {
				SysUserOnline online = new SysUserOnline();
				online.setSessionId(String.valueOf(session.getId()));
				online.setDeptName(session.getDeptName());
				online.setLoginName(session.getLoginName());
				online.setIpaddr(session.getHost());
				online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
				online.setBrowser(session.getBrowser());
				online.setOs(session.getOs());
				online.setStatus(session.getStatus());
				online.setStartTimestamp(session.getStartTimestamp());
				online.setLastAccessTime(session.getLastAccessTime());
				online.setExpireTime(session.getTimeout());
				//插入用户在线信息
				SpringUtils.getBean(ISysUserOnlineService.class).saveOnline(online);
			}
		};		
	}
	
	/**
	 * 记录操作日志
	 * @param operLog
	 * @return
	 */
	public static TimerTask recordOper(final SysOperLog operLog) {
		return new TimerTask() {
			
			@Override
			public void run() {
				// 远程查询操作地点
				operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
				SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
			}
		};
	}
	
	/**
	 * 记录登录信息
	 * @param username 用户名
	 * @param status 状态
	 * @param message 消息
	 * @param args 列表
	 * @return 任务task
	 */
	public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = ShiroUtils.getIp();
		return new TimerTask() {
			
			@Override
			public void run() {
				String address = AddressUtils.getRealAddressByIP(ip);
				StringBuilder s = new StringBuilder();
				s.append(LogUtils.getBlock(ip));
				s.append(address);
				s.append(LogUtils.getBlock(username));
				s.append(LogUtils.getBlock(status));
				s.append(LogUtils.getBlock(message));
				//打印信息到日志
				sys_user_logger.info(s.toString(), args);
				//获取客户端操作系统
				String os = userAgent.getOperatingSystem().getName();
				//获取客户端浏览器
				String browser = userAgent.getBrowser().getName();
				//封装对象
				SysLogininfor logininfor = new SysLogininfor();
				logininfor.setLoginName(username);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(address);
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setMsg(message);
				//日志状态
				if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
					logininfor.setStatus(Constants.SUCCESS);
				}
				else if (Constants.LOGIN_FAIL.equals(status)) {
					logininfor.setStatus(Constants.FAIL);
				}
				//插入数据
				SpringUtils.getBean(SysLogininforServiceImpl.class).insertLogininfor(logininfor);
			}
		};
	}
}
