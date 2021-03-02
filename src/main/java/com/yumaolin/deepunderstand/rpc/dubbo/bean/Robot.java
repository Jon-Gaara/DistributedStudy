package com.yumaolin.deepunderstand.rpc.dubbo.bean;

import org.apache.dubbo.common.extension.SPI;

@SPI("optimusPrime")
public interface Robot {
	public void sayHello();
	public String returnHello();
}
