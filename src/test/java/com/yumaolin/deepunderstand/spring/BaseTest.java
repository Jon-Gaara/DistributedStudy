package com.yumaolin.deepunderstand.spring;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/** 
 *
 * @author yuml
 * @since 2019年1月29日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
@Transactional(transactionManager = "myTxManager")
@DisplayName("测试基础服务类")
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private SpringAsync springAsync;
	
	@Test
	public void testBaseTest() {
		System.out.println("=============================================*********【容器启动正常!】*********============================================");
	}
	
	@Test
	public void testAsyncTest() throws InterruptedException {
		System.out.println("该方法异步处理");
		springAsync.SpringAsyncMethod();
		System.out.println("执行完毕");
		Thread.sleep(1500);
	}
}
