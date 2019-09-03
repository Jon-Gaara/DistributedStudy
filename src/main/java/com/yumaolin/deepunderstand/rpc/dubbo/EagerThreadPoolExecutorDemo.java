package com.yumaolin.deepunderstand.rpc.dubbo;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import org.apache.dubbo.common.threadpool.support.eager.EagerThreadPoolExecutor;
import org.apache.dubbo.common.threadpool.support.eager.TaskQueue;

/** 
 * EagerThreadPoolExecutor
 * 1.如果线程数小于核心线程数，就创建核心线程
 * 2.如果线程数小于最大线程数，就创建最大线程   @see {@link TaskQueue#offer(Runnable)}
 * 3.如果线程数大于最大线程数，就往队列里放
 * @author yuml
 * @since 2019年8月28日
 */
public class EagerThreadPoolExecutorDemo {
	
	public static void main(String[] args) {
		TaskQueue<Runnable> taskQueue = new TaskQueue<Runnable>(100);
		EagerThreadPoolExecutor executor = new EagerThreadPoolExecutor(1, 
				10, 10000, TimeUnit.MILLISECONDS, taskQueue, Executors.defaultThreadFactory(), new AbortPolicy());
		taskQueue.setExecutor(executor);
		/*ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 
				10, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(100), Executors.defaultThreadFactory(), new AbortPolicy());*/
		for(int i = 0;i<50;i++) {
			executor.execute(new EagerThreadPoolExecutorDemo().new EagerRunable());
		}
	}
	
	class EagerRunable implements Runnable{

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(10000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
