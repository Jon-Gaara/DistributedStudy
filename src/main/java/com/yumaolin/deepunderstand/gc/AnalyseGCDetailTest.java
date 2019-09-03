package com.yumaolin.deepunderstand.gc;
/** 
 * 分析GC详情
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 
 * -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * -XX:+PrintFlagsFinal
 * @author yuml
 * @since 2019年8月23日
 */
public class AnalyseGCDetailTest {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(3000);
		while(true) {
			loadData();
		}
	}
	
	@SuppressWarnings("unused")
	public static void loadData() throws InterruptedException {
		byte[] data = null;
		for(int i=0;i<50;i++) {
			data = new byte[100 * 1024];
		}
		data = null;
		Thread.sleep(1000);
	}
}		
