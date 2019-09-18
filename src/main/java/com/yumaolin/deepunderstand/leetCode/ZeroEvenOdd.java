package com.yumaolin.deepunderstand.leetCode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;


/**
 * 1116. 打印零与奇偶数 用CountDownLatch Semaphore也能实现
 * 
 * @author yuml
 * @since 2019年9月17日
 */
public class ZeroEvenOdd {

	private AtomicInteger COUNT = new AtomicInteger(2);

	private int n;

	public ZeroEvenOdd(int n) {
		this.n = n;
	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {
		for (int i = 0; i < n; i++) {
			while (true) {
				if (COUNT.compareAndSet(2, 5)) {
					printNumber.accept(0);
					int j = (i + 1) % 2;
					COUNT.compareAndSet(5, j);
					break;
				}else {
					Thread.yield();
				}
			}
		}
	}

	public void even(IntConsumer printNumber) throws InterruptedException {
		for (int i = 2; i <= n; i += 2) {
			while (true) {
				if (COUNT.compareAndSet(0, 5)) {
					printNumber.accept(i);
					COUNT.compareAndSet(5, 2);
					break;
				}else {
					Thread.yield();
				}
			}
		}
	}

	public void odd(IntConsumer printNumber) throws InterruptedException {
		for (int i = 1; i <= n; i += 2) {
			while (true) {
				if (COUNT.compareAndSet(1, 5)) {
					printNumber.accept(i);
					COUNT.compareAndSet(5, 2);
					break;
				}else {
					Thread.yield();
				}
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(40);
		executorService.submit(() -> {
			try {
				zeroEvenOdd.zero(System.out::println);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		executorService.submit(() -> {
			try {
				zeroEvenOdd.even(System.out::println);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		executorService.submit(() -> {
			try {
				zeroEvenOdd.odd(System.out::println);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		executorService.shutdown();
	}
}
