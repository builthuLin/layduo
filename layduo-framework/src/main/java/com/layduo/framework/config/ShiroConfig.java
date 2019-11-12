package com.layduo.framework.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.layduo.common.utils.StringUtils;
import com.layduo.framework.shiro.realm.UserRealm;
import com.layduo.framework.shiro.web.filter.LogoutFilter;
import com.layduo.framework.shiro.web.filter.captcha.CaptchaValidateFilter;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro相关配置
 * 
 * @author layduo
 * @createTime 2019年11月7日 下午6:26:22
 */
@Configuration
public class ShiroConfig {

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	// Session超时时间，单位为毫秒（默认30分钟）
	@Value("${shiro.session.expireTime}")
	private int expireTime;

	// 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
	@Value("${shiro.session.validationInterval}")
	private int validationInterval;

	// 同一个用户最大会话数
	@Value("${shiro.session.maxSession}")
	private int maxSession;

	// 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
	@Value("${shiro.session.kickoutAfter}")
	private boolean kickoutAfter;

	// 验证码开关
	@Value("${shiro.user.captchaEnabled}")
	private boolean captchaEnabled;

	// 验证码类型
	@Value("${shiro.user.captchaType}")
	private String captchaType;

	// 设置Cookie的域名
	@Value("${shiro.cookie.domain}")
	private String domain;

	// 设置cookie的有效访问路径
	@Value("${shiro.cookie.path}")
	private String path;

	// 设置HttpOnly属性
	@Value("${shiro.cookie.httpOnly}")
	private boolean httpOnly;

	// 设置Cookie的过期时间，秒为单位
	@Value("${shiro.cookie.maxAge}")
	private int maxAge;

	// 登录地址
	@Value("${shiro.user.loginUrl}")
	private String loginUrl;

	// 权限认证失败地址
	@Value("${shiro.user.unauthorizedUrl}")
	private String unauthorizedUrl;

	@Bean
	public EhCacheManager getEhCacheManager() {
		// 获取ehcache-shiro.xml里对应的缓存管理器
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("layduo");
		EhCacheManager ehCacheManager = new EhCacheManager();
		if (StringUtils.isNull(cacheManager)) {
			ehCacheManager.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
		} else {
			ehCacheManager.setCacheManager(cacheManager);
		}
		return ehCacheManager;
	}

	/**
	 * 返回配置文件流 避免ehcache配置文件一直被占用，无法完全销毁项目重新部署
	 * 
	 * @return
	 */
	protected InputStream getCacheManagerConfigFileInputStream() {
		String configFile = "classpath:ehcache/ehcache-shiro.xml";
		InputStream inputStream = null;

		try {
			inputStream = ResourceUtils.getInputStreamForPath(configFile);
			byte[] b = IOUtils.toByteArray(inputStream);
			InputStream in = new ByteArrayInputStream(b);
			return in;
		} catch (IOException e) {
			// 无法获取cacheManagerConfigFile的输入流
			throw new ConfigurationException(
					"Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	/**
	 * 自定义Realm 认证授权逻辑实现
	 * @param cacheManager
	 * @return
	 */
	@Bean
	public UserRealm userRealm(EhCacheManager cacheManager) {
		UserRealm userRealm = new UserRealm();
		userRealm.setCacheManager(cacheManager);
		return userRealm;
	}
	
	/**
	 * 安全管理器
	 * @param userRealm
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//设置realm
		securityManager.setRealm(userRealm);
		//注入缓存管理器
		securityManager.setCacheManager(getEhCacheManager());
		return securityManager;
	}
	
	/**
	 * Shiro过滤器配置 、配置权限拦截规则指定跳转页面
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//Shiro的核心安全接口，这个属性是必须的
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//身份认证失败，则跳转到登录页面的配置
		shiroFilterFactoryBean.setLoginUrl(loginUrl);
		//权限认证失败，则跳转到指定页面
		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
		//Shiro连接约束配置，即过滤链的定义
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//对静态资源设置匿名访问
		filterChainDefinitionMap.put("/favicon.ico**", "anon");
		filterChainDefinitionMap.put("/ruoyi.png**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/ruoyi/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        //退出logout地址，shiro清除session
        filterChainDefinitionMap.put("/logout", "logout");
        //不需要拦截的访问
        filterChainDefinitionMap.put("/login", "anon,captchaValidate");
        
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("captchaValidate", captchaValidateFilter());
        
        //注销成功，则跳转到指定页面
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //所有请求需要认证
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	    return shiroFilterFactoryBean;
	}
	
	/**
	 * 退出过滤器
	 * @return
	 */
	public LogoutFilter logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setCacheManager(getEhCacheManager());
		logoutFilter.setLoginUrl(loginUrl);
		return logoutFilter;
	}
	
	/**
	 * 自定义验证码过滤器
	 * @return
	 */
	@Bean
	public CaptchaValidateFilter captchaValidateFilter() {
		CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
		captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
		captchaValidateFilter.setCaptchaType(captchaType);
		return captchaValidateFilter;
	}
	
	/**
	 * cookie 属性设置 配置文件默认30天
	 * @return
	 */
	public SimpleCookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge * 24 * 60 * 60);
		return cookie;
	}
	
	/**
	 * 记住我
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
		return cookieRememberMeManager;
	}
	
	/**
	 * thymeleaf模板引擎和shiro框架整合
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
	
	/**
	 * 开启Shiro注解通知器
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
