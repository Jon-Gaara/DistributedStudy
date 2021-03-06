package com.yumaolin.deepunderstand.gc;
/** 
 *  测试ygc 动态年龄规则
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 
 * -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @author yuml
 * @since 2019年8月15日
 */
public class YoungGcForSurvivor2Test {
	
	/**
	 * eden区-8M S0，S1区-1M old区-20M
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
	  byte[] byte1 = new byte[2 * 1024 * 1024];
	  byte1 = new byte[2 * 1024 * 1024];
	  byte1 = new byte[2 * 1024 * 1024];
	  byte1 = null;
	  byte[] byte2 = new byte[128 * 1024];
	  
	  byte[] byte3 = new byte[2 * 1024 * 1024];
	  byte3 = new byte[2 * 1024 * 1024];
	  byte3 = new byte[2 * 1024 * 1024];
	  byte3 = new byte[2 * 1024 * 1024];
	}
}
