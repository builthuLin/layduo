package com.layduo.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义日志注解
 * 
 * @author layduo
 * @createTime 2019年11月5日 下午6:33:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WebLog {

	/**
	 * 日志描述信息
	 * 
	 * @return
	 */
	String description() default "";
}
