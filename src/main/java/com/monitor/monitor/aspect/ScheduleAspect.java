package com.monitor.monitor.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
public class ScheduleAspect {
	private final static Logger logger = LoggerFactory.getLogger(ScheduleAspect.class);
	
	@Pointcut("execution(* com.monitor.monitor.controller.GeneralController.task(..))")
	public void Pointcut() {
	}

	// 前置通知
	@Before("Pointcut()")
	public void beforeMethod(JoinPoint joinPoint) {
		
		logger.info("aop doBefore..");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		
        //url
		logger.info("url={}",request.getRequestURI());
		
		//method
		logger.info("method={}", request.getMethod());
		
		//ip
		logger.info("ip={}", request.getRemoteAddr());
		
		//类方法
		logger.info("classMethod={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		
		//参数
		Enumeration<String> paramter = request.getParameterNames();
		while (paramter.hasMoreElements()) {
			String str = (String) paramter.nextElement();
			logger.info(str + "={}", request.getParameter(str));
		}
		System.out.println("ScheduleAspect调用了前置通知");

	}

	// @After: 后置通知
	@After("Pointcut()")
	public void afterMethod(JoinPoint joinPoint) {
		System.out.println("ScheduleAspect调用了后置通知");
	}

	// @AfterRunning: 返回通知 rsult为返回内容
	@AfterReturning(value = "Pointcut()", returning = "result")
	public void afterReturningMethod(JoinPoint joinPoint, Object result) {
		System.out.println("ScheduleAspect调用了返回通知");
	}

	// @AfterThrowing: 异常通知
	@AfterThrowing(value = "Pointcut()", throwing = "e")
	public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
		System.out.println("ScheduleAspect调用了异常通知");
	}

	// @Around：环绕通知
	@Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("ScheduleAspectaround执行方法之前");
        Object object = pjp.proceed();
        System.out.println("ScheduleAspectaround执行方法之后--返回值：" +object);
        return object;
    }
}
