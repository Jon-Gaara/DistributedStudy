package com.yumaolin.deepunderstand.rpc.dubbo;

import java.util.concurrent.Future;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;


/**
 * 客户端泛化调用
 * 
 * @author yuml
 * @since 2019年8月27日
 */
public class ServiceConsumerGenericImpl {

	public static void main(String[] args) {
		// 应用配置
		ApplicationConfig config = new ApplicationConfig();
		config.setName("consumer");

		// 注册中心配置
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(
				"zookeeper01-center.lycheepay.org:19555,zookeeper02-center.lycheepay.org:19555,zookeeper03-center.lycheepay.org:19555");
		registryConfig.setProtocol("zookeeper");
		registryConfig.setVersion("1.0.0");
		registryConfig.setTimeout(30000);

		// 服务调用者配置
		ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<GenericService>();
		referenceConfig.setApplication(config);
		// referenceConfig.setRegistry(registryConfig);
		referenceConfig.setProtocol("dubbo");
		referenceConfig.setUrl("dubbo://127.0.0.1:12021/com.yumaolin.deepunderstand.rpc.dubbo.bean.Robot");
		referenceConfig.setInterface("com.yumaolin.deepunderstand.rpc.dubbo.bean.Robot");
		referenceConfig.setVersion("1.0.0");
		referenceConfig.setCluster("failfast");
		referenceConfig.setTimeout(15000);
		// 泛化调用
		referenceConfig.setGeneric(true);
		referenceConfig.setAsync(true);
		try {
			GenericService genericService = referenceConfig.get();
			Future<Object> robotFuture = RpcContext.getContext()
					.asyncCall(() -> genericService.$invoke("returnHello", null, null));
			Object result = robotFuture.get();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
