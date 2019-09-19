package com.yumaolin.deepunderstand.leetcode.multithread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntConsumer;


/**
 * 1116. 打印零与奇偶数 
 * 用CountDownLatch,Semaphore,AtomicInteger也能实现
 * @author yuml
 * @since 2019年9月17日
 */
public class ZeroEvenOdd {

	private CyclicBarrier countDownLatchA = new CyclicBarrier(1);
	private CyclicBarrier countDownLatchB = new CyclicBarrier(2);
	private CyclicBarrier countDownLatchC = new CyclicBarrier(2);

	private int n;

	public ZeroEvenOdd(int n) {
		this.n = n;
	}

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {
		try {
			for (int i = 0; i < n; i++) {
				countDownLatchA.await();
				if (countDownLatchA.getParties() == 1) {
					countDownLatchA = new CyclicBarrier(2);
				}
				if (i == n - 1) {
					countDownLatchA = new CyclicBarrier(1);
				}
				printNumber.accept(0);
				if (i % 2 == 0) {
					countDownLatchB.await(); // 激活打印奇数的方法
				} else {
					countDownLatchC.await();
				}
			}
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public void even(IntConsumer printNumber) throws InterruptedException {
		try {
			for (int i = 2; i <= n; i = i + 2) {
				countDownLatchC.await();
				printNumber.accept(i);
				countDownLatchA.await();
			}
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	public void odd(IntConsumer printNumber) throws InterruptedException {
		try {
			for (int i = 1; i <= n; i = i + 2) {
				countDownLatchB.await();
				printNumber.accept(i);
				countDownLatchA.await();
			}
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
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
