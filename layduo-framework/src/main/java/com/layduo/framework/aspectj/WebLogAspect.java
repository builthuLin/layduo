package com.layduo.framework.aspectj;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.layduo.common.annotation.WebLog;
import com.layduo.common.utils.IpUtils;

/**
* @author layduo
* @createTime 2019年11月5日 下午6:36:57
*/
@Aspect
@Component
public class WebLogAspect {

	 private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

     private static final String LINE_SEPARATOR = System.lineSeparator();
     
     /**
      * 以自定义注解为切点
      */
     @Pointcut("@annotation(com.layduo.common.annotation.WebLog)")
     public void webLog(){}

     /**
      * 环绕
      * @param proceedingJoinPoint
      * @return
      * @throws Throwable
      */
     @Around("webLog()")
     public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
         long startTime = System.currentTimeMillis();
         Object result = proceedingJoinPoint.proceed(); //执行切点
         //打印出参...
         logger.info("Response Args   : {}", new Gson().toJson(result));
         //打印耗时
         logger.info("Time-Consuming :  {} ms",  System.currentTimeMillis() - startTime);
         return result;
     }

     /**
      * 在切点前织入
      * @param joinPoint
      */
     @Before("webLog()")
     public void doBefore(JoinPoint joinPoint) throws Exception {
         //开始打印请求日志
         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         HttpServletRequest request = attributes.getRequest();

         //获取注解的描述信息
         String methodDescription = getAspectLogDescription(joinPoint);
         // 打印请求相关参数
         logger.info("========================================== Start ==========================================");
         // 打印请求 url
         logger.info("URL            : {}", request.getRequestURL().toString());
         // 打印描述信息
         logger.info("Description    : {}", methodDescription);
         // 打印 Http method
         logger.info("HTTP Method    : {}", request.getMethod());
         // 打印调用 controller 的全路径以及执行方法
         logger.info("CLass Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
         //打印请求的IP
         logger.info("IP             : {}", IpUtils.getIpAddr(request));
         //打印请求入参
         logger.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
     }

     /**
      * 在切点后织入
      */
     @After("webLog()")
     public void deAfter(){
         logger.info("==========================================  End  ==========================================" + LINE_SEPARATOR);
     }

     /**
      * 获取切面注解的描述
      * @param joinPoint 切点
      * @return 描述信息
      * @throws Exception
      */
     public String getAspectLogDescription(JoinPoint joinPoint)
             throws Exception {
         String targetName = joinPoint.getTarget().getClass().getName();
         String methodName = joinPoint.getSignature().getName();
         Object[] arguments = joinPoint.getArgs();
         Class<?> targetClass = Class.forName(targetName);
         Method[] methods = targetClass.getMethods();
         StringBuilder description = new StringBuilder("");
         for (Method method : methods) {
             if (method.getName().equals(methodName)) {
                 Class<?>[] clazzs = method.getParameterTypes();
                 if (clazzs.length == arguments.length) {
                     description.append(method.getAnnotation(WebLog.class).description());
                     break;
                 }
             }
         }
         return description.toString();
     }
}
