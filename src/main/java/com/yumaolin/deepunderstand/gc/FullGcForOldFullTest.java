package com.yumaolin.deepunderstand.gc;
/** 
 *  测试 如果分配一个大对象则直接分配在old区 并且如果ygc后old区放不下 会触发full gc
 * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 
 * -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=3145728 
 * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
 * @author yuml
 * @since 2019年8月16日
 */
public class FullGcForOldFullTest {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] byte1 = new byte[4 * 1024 * 1024];
		byte1 = null;
		byte[] byte2 = new byte[2 * 1024 * 1024];
		byte[] byte3 = new byte[2 * 1024 * 1024];
		byte[] byte4 = new byte[2 * 1024 * 1024];
		byte[] byte5 = new byte[128 * 1024];
		byte[] byte6 = new byte[2 * 1024 * 1024];
	}
}
