package com.yumaolin.deepunderstand.rpc.dubbo;


import java.util.concurrent.Future;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

import com.yumaolin.deepunderstand.rpc.dubbo.bean.Robot;

/** 
 * 客户端异步调用
 * @author yuml
 * @since 2019年2月15日
 */
public class ServiceConsumerAsyncImpl {
	
	public static void main(String[] args) {
		//应用配置
		ApplicationConfig config = new ApplicationConfig();
		config.setName("consumer");
		
		//注册中心配置
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("zookeeper01-center.lycheepay.org:19555,zookeeper02-center.lycheepay.org:19555,zookeeper03-center.lycheepay.org:19555");
		registryConfig.setProtocol("zookeeper");
		registryConfig.setVersion("1.0.0");
		//registryConfig.setTimeout(30000);
		 
		//服务调用者配置
		ReferenceConfig<Robot> referenceConfig = new ReferenceConfig<Robot>();
		referenceConfig.setApplication(config);
		//referenceConfig.setRegistry(registryConfig);
		referenceConfig.setProtocol("dubbo");
		referenceConfig.setUrl("dubbo://127.0.0.1:12021/com.yumaolin.deepunderstand.rpc.dubbo.bean.Robot");
		referenceConfig.setInterface(Robot.class);
		referenceConfig.setVersion("1.0.0");
		referenceConfig.setCluster("failfast");
		referenceConfig.setTimeout(15000);
		referenceConfig.setAsync(true);
		Robot robot = referenceConfig.get();
		try {
			/**
			 * @see org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker#doInvoke
			 */
			Future<String> robotFuture = RpcContext.getContext().asyncCall(()->robot.returnHello());
			long start = System.currentTimeMillis();
			System.out.println("异步调用");
			System.out.println(robot.returnHello());
			System.out.println(robotFuture.get());
			System.out.println(System.currentTimeMillis()-start);
			/*long start2 = System.currentTimeMillis();
			System.out.println("异步调用");
			System.out.println(robot.returnHello());
			System.out.println(System.currentTimeMillis()-start2);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
