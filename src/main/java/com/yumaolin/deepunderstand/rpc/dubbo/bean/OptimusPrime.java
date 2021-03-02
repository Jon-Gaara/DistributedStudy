package com.yumaolin.deepunderstand.rpc.dubbo.bean;

public class OptimusPrime implements Robot{
	public void sayHello() {
		System.out.println("Hello OptimusPrime");
	}
	
	public String returnHello() {
		return "hello optimusPrime";
	}
}

