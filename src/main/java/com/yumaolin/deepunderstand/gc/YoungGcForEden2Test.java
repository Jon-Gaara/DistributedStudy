package com.yumaolin.deepunderstand.gc;
/** 
 *  测试ygc以后 如果垃圾对象的年龄达到15，就会被分配到old区
 * -XX:NewSize=20971520 -XX:MaxNewSize=20971520 -XX:InitialHeapSize=41943040 -XX:MaxHeapSize=41943040 
 * -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=20971520 
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @author yuml
 * @since 2019年8月15日
 */
public class YoungGcForEden2Test {
	/**
	 * eden区-8M S0，S1区-1M old区-20M
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
	  byte[] byte3 = new byte[128 * 1024];
	  for(int i=0;i<16;i++) {
		  byte[] byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null;
		  byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null;
		  byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null;
		  byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null; 
		  byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null; 
		  byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null; 
		  byte1 = new byte[2 * 1024 * 1024];
		  byte1 = null; 
	  }
	}
}
