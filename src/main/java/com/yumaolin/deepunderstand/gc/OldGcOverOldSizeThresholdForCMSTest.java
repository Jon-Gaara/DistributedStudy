package com.yumaolin.deepunderstand.gc;
/** 
 *  测试如果old区大小超过阈值就会触发old gc
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 
 * -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps 
 * -XX:+PrintFlagsFinal
 * -Xloggc:gc.log
 * @author yuml
 * @since 2019年8月16日
 */
public class OldGcOverOldSizeThresholdForCMSTest {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] byte1 = new byte[5 * 1024 * 1024 + 758 * 1024];
		byte1 = null;
		byte[] byte2 = new byte[4 * 1024 * 1024];
		byte2 = null;
		byte[] byte3 = new byte[2 * 1024 * 1024];
		byte3 = null;
		byte[] byte4 = new byte[2 * 1024 * 1024];
		byte4 = null;
		byte[] byte5 = new byte[2 * 1024 * 1024];
		byte5 = null;
		byte[] byte6 = new byte[512 * 1024];
		byte6 = null;
	}
}
