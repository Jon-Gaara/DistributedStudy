package com.yumaolin.deepunderstand.geektime.jdk;

/**
 *
 * @author yuml
 * @since 2019年7月2日
 */
public class GeekTime {
	public static int foo(boolean f, int in) {
		int v;
		if (f) {
			v = in;
		} else {
			v = (int) Math.sin(in);
		}
		if (v == in) {
			return 0;
		} else {
			return (int) Math.cos(v);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 500000; i++) {
			foo(true, 2);
		}
		Thread.sleep(2000);
	}
}
