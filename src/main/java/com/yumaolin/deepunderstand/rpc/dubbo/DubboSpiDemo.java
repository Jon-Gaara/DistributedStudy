package com.yumaolin.deepunderstand.rpc.dubbo;

import java.io.IOException;

import org.apache.dubbo.common.extension.ExtensionLoader;

import com.yumaolin.deepunderstand.rpc.dubbo.bean.Robot;


/** 
 * dubbo spi demo
 * @author yuml
 * @since 2019年1月28日
 */
public class DubboSpiDemo {
	
	public static void main(String[] args) throws IOException {
		ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
		Robot defalutExtension =  extensionLoader.getDefaultExtension();
		defalutExtension.sayHello();
		Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
		optimusPrime.sayHello();
		Robot bumblebee = extensionLoader.getExtension("bumblebee");
		bumblebee.sayHello();
	}
}
