package com.yumaolin.deepunderstand.leetcode.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/** 
 *
 * @author yuml
 * @since 2019年9月24日
 */
public class H2O {
	
	private Semaphore hSemaphore = new Semaphore(1); 
	
	private Semaphore oSemaphore = new Semaphore(2); 
	
	public H2O() {
        
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
		
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
    	hSemaphore.acquire(1);
        releaseHydrogen.run();
        oSemaphore.release(1);
        if(hSemaphore.availablePermits() == 0) {
        	hSemaphore.release(2);
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
    	oSemaphore.acquire(2);
    	releaseOxygen.run();
    	hSemaphore.release(2);
    }
    
    public static void main(String[] args) {
    	ExecutorService executorService = Executors.newFixedThreadPool(2);
    	H2O h2o = new H2O();
    	int n = 5;
    	for(int i=0;i<2*n;i++) {
    		executorService.submit(() -> {
    			try {
    				h2o.hydrogen(()->{System.out.println("H");});
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		});
    		if(i%2 == 1) {
    			executorService.submit(() -> {
        			try {
        				h2o.oxygen(()->{System.out.println("O");});
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		});	
    		}
    	}
		executorService.shutdown();
	}
}
