package com.layduo.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.layduo.common.config.Global;
import com.layduo.common.constant.Constants;
import com.layduo.framework.interceptor.RepeatSubmitInterceptor;

/**
 * 通用配置
 * 
 * @author layduo
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
	/**
	 * 首页地址
	 */
	@Value("${shiro.user.indexUrl}")
	private String indexUrl;

	@Autowired
	private RepeatSubmitInterceptor repeatSubmitInterceptor;

	/**
	 * 默认首页的设置，当输入域名是可以自动跳转到默认指定的网页
	 * 
	 * 以前写SpringMVC的时候，如果需要访问一个页面，必须要写Controller类，然后再写一个方法跳转到页面，
	 * 感觉好麻烦，其实重写WebMvcConfigurerAdapter中的addViewControllers方法即可达到效果了
	 * 比如访问http://localhost:8090/toLogin就可以到login.html
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:" + indexUrl);
	}

	/**
	 * 自定义资源映射
	 * addResoureHandler指的是对外暴露的访问路径
	 * addResourceLocations指的是文件放置的目录
	 * learn to : https://www.cnblogs.com/java-synchronized/p/7091723.html
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上传路径 */
		registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
				.addResourceLocations("file:" + Global.getProfile() + "/");

		/** swagger配置 */
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		/** 静态资源的映射*/
		/*registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
										.addResourceLocations("classpath:/resources/")
										.addResourceLocations("classpath:/static/")
										.addResourceLocations("classpath:/public/");*/
	}

	/**
	 * 自定义拦截规则
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
	}
	
	/**
	 * 允许跨域请求
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置允许跨域的路径
		registry.addMapping("/**")
				// 设置允许跨域请求的域名
				.allowedOrigins("*")
				// 是否允许证书 是否发送cookie
				.allowCredentials(true)
				// 设置允许的方法
				.allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
				// 设置允许的header属性
				.allowedHeaders("*")
				// 跨域允许时间
				.maxAge(3600);
	}
}