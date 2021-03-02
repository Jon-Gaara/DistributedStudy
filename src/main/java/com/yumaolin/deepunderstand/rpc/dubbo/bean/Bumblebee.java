package com.yumaolin.deepunderstand.rpc.dubbo.bean;

public class Bumblebee implements Robot{
	public void sayHello() {
		System.out.println("Hello Bumblebee");
	}
	
	public String returnHello() {
		return "hello bumblebee";
	}
}
