package com.yumaolin.deepunderstand.geektime.jdk;

/**
 * 安全点测试
 * @author yuml
 * @since 2019年7月1日
 */
public class SafepointTest {
	static double sum = 0;

	public static void foo() {
		long start = System.nanoTime();
		for (int i = 0; i < 0x77777777; i++) {
			sum += Math.sqrt(i);
		}
		System.out.println("foo:" + (System.nanoTime() - start) / 1000000);
	}

	public static void bar() {
		long start = System.nanoTime();
		for (int i = 0; i < 50_000_000; i++) {
			new Object().hashCode();
		}
		System.out.println("bar:" + (System.nanoTime() - start) / 1000000);
	}

	public static void main(String[] args) {
		 //虽然是两个线程 但是第二个线程会等第一个线程 把for里面的i改成long 则bar会先执行完
		 new Thread(SafepointTest::foo).start();
		 new Thread(SafepointTest::bar).start();
	}
}
