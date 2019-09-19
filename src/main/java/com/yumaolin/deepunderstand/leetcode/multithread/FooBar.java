package com.yumaolin.deepunderstand.leetcode.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1115. 交替打印FooBar
 * @author yuml
 * @since 2019年9月17日
 */
public class FooBar {
	
	private static final Object FOO = new Object();

	private volatile int count = 1;

	private int n;

	public FooBar(int n) {
		this.n = n;
	}

	public void foo(Runnable printFoo) throws InterruptedException {

		for (int i = 0; i < n; i++) {

			// printFoo.run() outputs "foo". Do not change or remove this line.
			synchronized (FOO) {
				while (count % 2 == 0) {
					FOO.wait();
				};
				printFoo.run();
				count = 2;
				FOO.notifyAll();
			}
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {

		for (int i = 0; i < n; i++) {

			// printBar.run() outputs "bar". Do not change or remove this line.
			synchronized (FOO) {
				while (count % 2 == 1) {
					FOO.wait();
				};
				count = 1;
				printBar.run();
				FOO.notifyAll();
			}
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		FooBar fooBar = new FooBar(10);
		executorService.submit((Runnable) ()->{
			try {
				fooBar.foo(()->{System.out.println("foo");});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}});
		executorService.submit((Runnable) ()->{
			try {
				fooBar.bar(()->{System.out.println("bar");});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}});
		executorService.shutdown();
	}
}
