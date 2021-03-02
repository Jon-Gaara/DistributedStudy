package com.yumaolin.deepunderstand.leetcode.multithread;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author yml
 * @Description
 * @Date 2021-03-01 13:07
 */
public class FizzBuzz {

    private int n;

    private volatile int count = 1;

    private Semaphore semaphore = new Semaphore(0);
    private Semaphore semaphore2 = new Semaphore(0);
    private Semaphore semaphore3 = new Semaphore(0);
    private Semaphore semaphore4 = new Semaphore(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (count <=n){
            semaphore.acquire();
            if(count > n){
                return;
            }
            final boolean isRun = count % 3 == 0 && count % 5 != 0;
            if(isRun){
                printFizz.run();
                ++count;
            }
            if(count > n){
                releaseAll(1);
                return;
            }else{
                release();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (count <=n){
            semaphore2.acquire();
            if(count > n){
                return;
            }
            final boolean isRun = count % 5 == 0 && count % 3 != 0;
            if(isRun){
                printBuzz.run();
                ++count;
            }
            if(count > n){
                releaseAll(2);
                return;
            }else{
                release();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (count <=n){
            semaphore3.acquire();
            if(count > n){
                return;
            }
            final boolean isRun = count % 3 == 0 && count % 5 == 0;
            if(isRun){
                printFizzBuzz.run();
                ++count;
            }
            if(count > n){
                releaseAll(3);
                return;
            }else{
                release();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (count <= n){
            semaphore4.acquire();
            if(count > n){
                return;
            }
            final boolean isRun = count % 5 != 0 && count % 3 != 0;
            if(isRun){
                printNumber.accept(count);
                ++count;
            }
            if(count > n){
                releaseAll(4);
                return;
            }else{
                release();
            }
        }

    }

    private void release(){
        boolean isThree = count % 3 == 0;
        boolean isFive = count % 5 == 0;
        if(isThree){
            if(isFive){
                if(semaphore3.availablePermits() <= 0){
                    semaphore3.release();
                }
            }else{
                if(semaphore.availablePermits() <=0){
                    semaphore.release();
                }
            }
        }else if(isFive){
            if(semaphore2.availablePermits() <= 0){
                semaphore2.release();
            }
        }else{
            if(semaphore4.availablePermits() <= 0) {semaphore4.release();}
        }
    }

    private void releaseAll(int notRelease){
        switch (notRelease){
            case 1:
                semaphore2.release();
                semaphore3.release();
                semaphore4.release();
                break;
            case 2:
                semaphore.release();
                semaphore3.release();
                semaphore4.release();
                break;
            case 3:
                semaphore.release();
                semaphore2.release();
                semaphore4.release();
                break;
            case 4:
                semaphore.release();
                semaphore2.release();
                semaphore3.release();
                break;
        }
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(2);
        new Thread(()->{
            try {
                fizzBuzz.fizz(()->{
                    System.out.println("fizz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                fizzBuzz.buzz(()->{
                    System.out.println("buzz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                fizzBuzz.fizzbuzz(()->{
                    System.out.println("fizzbuzz");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                fizzBuzz.number(a->{
                    System.out.println(a);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}