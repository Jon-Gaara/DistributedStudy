package com.yumaolin.deepunderstand.gc;
/** 
 *  测试ygc之后，如果old区大小小于每次ygc的平均存活对象大小 就会触发old gc
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 
 * -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @author yuml
 * @since 2019年8月15日
 */
public class OldGcOverAvgeUpSizeForCMSTest {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] byte1 = new byte[3 * 1024 * 1024];
		byte1 = null;
		byte[] byte2 = new byte[4 * 1024 * 1024];
		byte2 = null;
		byte[] byte3 = new byte[2 * 1024 * 1024];
		byte3 = null;
		byte[] byte4 = new byte[2 * 1024 * 1024];
		byte4 = null;
		byte[] byte5 = new byte[2 * 1024 * 1024];
		byte5 = null;
		byte[] byte6 = new byte[1024 * 1024];
		byte3 = new byte[2 * 1024 * 1024];
		byte3 = null;
		byte4 = new byte[2 * 1024 * 1024];
		byte4 = null;
		byte5 = new byte[2 * 1024 * 1024];
		byte5 = null;
		byte[] byte7 = new byte[1024 * 1024];
		byte3 = new byte[2 * 1024 * 1024];
		byte3 = null;
		byte4 = new byte[2 * 1024 * 1024];
		byte4 = null;
		byte5 = new byte[2 * 1024 * 1024];
		byte5 = null;
		//byte[] byte8 = new byte[1024 * 1024];//打开这个会出现 concurrent mode failure
		byte[] byte8 = new byte[258 * 1024];
	}
}
