package com.yumaolin.deepunderstand.spring;
/** 
 *
 * @author yuml
 * @since 2019年1月29日
 */

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SpringAsync {
	
	@Async
	public void SpringAsyncMethod() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("该方法异步处理2");
	}
}
