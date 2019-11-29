package com.layduo.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.layduo.common.enums.BusinessType;
import com.layduo.common.enums.OperatorType;

/**
 * 自定义操作日志记录注解
 * 
 * @author layduo
 * @createTime 2019年11月25日 上午11:42:29
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	/**
	 * 模块
	 */
	public String title() default "";

	/**
	 * 功能
	 */
	public BusinessType businessType() default BusinessType.OTHER;

	/**
	 * 操作人类别
	 */
	public OperatorType operatorType() default OperatorType.MANAGE;

	/**
	 * 是否保存请求的参数
	 */
	public boolean isSaveRequestData() default true;
}
