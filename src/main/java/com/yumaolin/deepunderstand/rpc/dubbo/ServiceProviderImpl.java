package com.yumaolin.deepunderstand.rpc.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumaolin.deepunderstand.rpc.dubbo.bean.Bumblebee;
import com.yumaolin.deepunderstand.rpc.dubbo.bean.Robot;


/**
 *
 * @author yuml
 * @since 2019年2月15日
 */
public class ServiceProviderImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);
	
	public static void main(String[] args) {
		System.setProperty("dubbo.application.logger", "slf4j");
		logger.info("[ServiceProviderImpl] start");
		// 当前应用配置
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("provider");
		// 注册中心配置
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("zookeeper01-center.lycheepay.org:19555,zookeeper02-center.lycheepay.org:19555,zookeeper03-center.lycheepay.org:19555");
		registryConfig.setProtocol("zookeeper");
		registryConfig.setVersion("1.0.0");
		registryConfig.setTimeout(30000);
		// 服务提供者配置
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName("dubbo");
		protocolConfig.setPort(12021);
		protocolConfig.setThreads(200);
		protocolConfig.setThreadpool("cached");// 线程池类型，可选：fixed/cached 缺省fixed

		// 服务提供者暴露服务配置
		ServiceConfig<Robot> serviceConfig = new ServiceConfig<>();
		serviceConfig.setApplication(applicationConfig);
		serviceConfig.setRegistry(registryConfig);
		serviceConfig.setProtocol(protocolConfig);
		serviceConfig.setInterface(Robot.class.getName());
		serviceConfig.setRef(new Bumblebee());
		serviceConfig.setVersion("1.0.0");
		serviceConfig.setLayer("service");
		serviceConfig.setCluster("failfast");
		serviceConfig.setAccesslog(true);
		//serviceConfig.setAsync(true);
		// 暴露及注册服务
		serviceConfig.export();
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
