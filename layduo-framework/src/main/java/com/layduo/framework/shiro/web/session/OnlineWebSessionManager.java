package com.layduo.framework.shiro.web.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.layduo.common.constant.ShiroConstants;
import com.layduo.common.utils.StringUtils;
import com.layduo.common.utils.bean.BeanUtils;
import com.layduo.common.utils.spring.SpringUtils;
import com.layduo.framework.shiro.session.OnlineSession;
import com.layduo.system.domain.SysUserOnline;
import com.layduo.system.service.ISysUserOnlineService;

/**
 * 主要是在此如果会话的属性修改了 就标识下其修改了 然后方便 OnlineSessionDao同步
 * 
 * @author layduo
 */
public class OnlineWebSessionManager extends DefaultWebSessionManager {
	
	private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

	@Override
	public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
		super.setAttribute(sessionKey, attributeKey, value);
		if (value != null && needMarkAttributeChanged(attributeKey)) {
			OnlineSession session = getOnlineSession(sessionKey);
			session.markAttributeChanged();
		}
	}

	private boolean needMarkAttributeChanged(Object attributeKey) {
		if (attributeKey == null) {
			return false;
		}
		String attributeKeyStr = attributeKey.toString();
		// 优化 flash属性没必要持久化
		if (attributeKeyStr.startsWith("org.springframework")) {
			return false;
		}
		if (attributeKeyStr.startsWith("javax.servlet")) {
			return false;
		}
		if (attributeKeyStr.equals(ShiroConstants.CURRENT_USERNAME)) {
			return false;
		}
		return true;
	}

	@Override
	public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
		Object removed = super.removeAttribute(sessionKey, attributeKey);
		if (removed != null) {
			OnlineSession s = getOnlineSession(sessionKey);
			s.markAttributeChanged();
		}

		return removed;
	}

	public OnlineSession getOnlineSession(SessionKey sessionKey) {
		OnlineSession session = null;
		Object obj = doGetSession(sessionKey);
		if (StringUtils.isNotNull(obj)) {
			session = new OnlineSession();
			BeanUtils.copyBeanProp(session, obj);
		}
		return session;
	}

	/**
	 * 验证session是否有效 用于删除过期session
	 */
	@Override
	public void validateSessions() {
		//loglevel日志等级：OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL或者您定义的级别。Log4j建议只使用四个级别，优先级从高到低分别是 ERROR、WARN、INFO、DEBUG
		/**Debug,Info和Trace一般会打印比较详细的信息，而且打印的次数较多，如果我们不加log.isDebugEnabled()等
		进行预先判断，当系统loglevel设置高于Debug或Info或Trace时，虽然系统不会打印出这些级别的日志，但是每次还是会拼接
		参数字符串，影响系统的性能。*/
		if (log.isInfoEnabled()) {
			log.info("invalidation sessions...");
		}

		int invalidCount = 0;

		int timeout = (int) this.getGlobalSessionTimeout();
		Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
		ISysUserOnlineService userOnlineService = SpringUtils.getBean(ISysUserOnlineService.class);
		List<SysUserOnline> userOnlineList = userOnlineService.selectOnlineByExpired(expiredDate);
		// 批量过期删除
		List<String> needOfflineIdList = new ArrayList<String>();
		for (SysUserOnline userOnline : userOnlineList) {
			try {
				SessionKey key = new DefaultSessionKey(userOnline.getSessionId());
				Session session = retrieveSession(key);
				if (session != null) {
					throw new InvalidSessionException();
				}
			} catch (InvalidSessionException e) {
				if (log.isDebugEnabled()) {
					boolean expired = (e instanceof ExpiredSessionException);
					String msg = "Invalidated session with id [" + userOnline.getSessionId() + "]"
							+ (expired ? " (expired)" : " (stopped)");
					log.debug(msg);
				}
				invalidCount++;
				needOfflineIdList.add(userOnline.getSessionId());
			}

		}
		if (needOfflineIdList.size() > 0) {
			try {
				userOnlineService.batchDeleteOnline(needOfflineIdList);
			} catch (Exception e) {
				log.error("batch delete db session error.", e);
			}
		}

		if (log.isInfoEnabled()) {
			String msg = "Finished invalidation session.";
			if (invalidCount > 0) {
				msg += " [" + invalidCount + "] sessions were stopped.";
			} else {
				msg += " No sessions were stopped.";
			}
			log.info(msg);
		}

	}

	@Override
	protected Collection<Session> getActiveSessions() {
		throw new UnsupportedOperationException("getActiveSessions method not supported");
	}
}
