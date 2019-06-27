package com.monitor.monitor.aspect;

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


@Component
@Aspect
public class TestAspect {

	private final static Logger logger = LoggerFactory.getLogger(TestAspect.class);
	
	@Pointcut("execution(* com.monitor.monitor.controller.*Controller.*(..))")
	public void Pointcut() {
	}

	// 前置通知
	@Before("Pointcut()")
	public void beforeMethod(JoinPoint joinPoint) {
		System.out.println("调用了前置通知");

	}

	// @After: 后置通知
	@After("Pointcut()")
	public void afterMethod(JoinPoint joinPoint) {
		System.out.println("调用了后置通知");
	}

	// @AfterRunning: 返回通知 rsult为返回内容
	@AfterReturning(value = "Pointcut()", returning = "result")
	public void afterReturningMethod(JoinPoint joinPoint, Object result) {
		System.out.println("调用了返回通知");
	}

	// @AfterThrowing: 异常通知
	@AfterThrowing(value = "Pointcut()", throwing = "e")
	public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
		System.out.println("调用了异常通知");
	}

	// @Around：环绕通知
	@Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around执行方法之前");
        Object object = pjp.proceed();
        logger.info("around执行方法之后--返回值：" +object);
        return object;

    }
}
