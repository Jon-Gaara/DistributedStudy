package com.yumaolin.deepunderstand.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectJDemo {
	
	@Pointcut("execution(* *.test(..))")
	public void test(){
		
	}
	
	@Before("test()")
	public void beforeTest(){
		System.out.println("BeforeTest()");
	}
	
	@After("test()")
	public void afterTest(){
		System.out.println("AfterTest()");
	}
	
	@AfterReturning("test()")
	public void afterReturningTest(){
		System.out.println("AfterReturningTest()");
	}
	
	@Around("test()")
	public Object aroundTest(ProceedingJoinPoint p){
		System.out.println("before1");
		Object o = null;
		try{
			o = p.proceed();
		}catch(Throwable e){
			e.printStackTrace();	
		}
		System.out.println("after1");
		return o;
	}
}
