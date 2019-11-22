package com.layduo.framework.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.layduo.framework.shiro.web.session.SpringSessionValidationScheduler;

import javax.annotation.PreDestroy;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author layduo
 */
@Component
public class ShutdownManager {
	private static final Logger logger = LoggerFactory.getLogger("sys-user");

	/**
	 * @Autowired(required = false)表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错。
	 * 默认@Autowired(required = true)表示注入的bean必须存在，不存在会报错
	 */
	@Autowired(required = false)
	private SpringSessionValidationScheduler springSessionValidationScheduler;

	/**
	 * 从Java EE 5规范开始，Servlet中增加了两个影响Servlet生命周期的注解（Annotion）；
	 * @PostConstruct和 @PreDestroy。这两个注解被用来修饰一个非静态的void()方法 
	 * @PostConstruct和 @PreDestroy 方法 实现bean初始化前和销毁之前进行的操作
	 * 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次、不得有任何参数、不得抛出已检查异常。
	 * PostConstruct在构造函数之后执行,init()方法之前执行,主要是加载一些缓存数据。PreDestroy（）方法在destroy()方法执行执行之后执行
	 * 被注解的Servlet生命周期如下：
	 * 服务器加载servlet -> servlet构造函数 -> PostConstruct -> init -> servlet -> destory -> PreDestroy ->服务器卸载servlet完毕
	 */
	@PreDestroy
	public void destroy() {
		shutdownSpringSessionValidationScheduler();
		shutdownAsyncManager();
	}

	/**
	 * 停止Seesion会话检查
	 */
	private void shutdownSpringSessionValidationScheduler() {
		if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled()) {
			try {
				logger.info("====关闭会话验证任务====");
				springSessionValidationScheduler.disableSessionValidation();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 停止异步执行任务
	 */
	private void shutdownAsyncManager() {
		try {
			logger.info("====关闭后台任务任务线程池====");
			AsyncManager.me().shutdown();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
