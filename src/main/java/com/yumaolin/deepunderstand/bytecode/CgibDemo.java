package com.yumaolin.deepunderstand.bytecode;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/** 
 * CGLIB demo	
 * @author yuml
 * @since 2019年1月3日
 */
public class CgibDemo {
	public static void main(String[] args) {
		Enhancer  enhancer = new Enhancer();
		enhancer.setSuperclass(CgibDemo.class);
		enhancer.setCallback(new CglibTestCallBack());
		CgibDemo cgibTest = (CgibDemo) enhancer.create();
		cgibTest.test();
		System.out.println(cgibTest);
	}
	
	public void test(){
		System.out.println("CgibTest DEMO");
	}
	
	private static class CglibTestCallBack implements MethodInterceptor{
		@Override
		public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
			System.out.println("Before invoke" + arg1);
			Object object = arg3.invokeSuper(arg0,arg2);
			System.out.println("After invoke" + arg1);
			return object;
		}
	}
}
