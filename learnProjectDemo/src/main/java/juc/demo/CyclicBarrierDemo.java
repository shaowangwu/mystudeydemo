package juc.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
/**
 * CyclicBarrier:和CountDownLatch相反。从0开始做加法，比如集齐7个龙珠召唤神龙，比如人到齐才能开会，先到的先等着阻塞
 *字面意思是可循环使用的屏障。它要做的是，让一组线程到达一个屏障(也可以叫同步点)时被阻塞，直到最后一个线程到达屏障时，
 * 屏障才会开门，所有被屏障拦截的线程才会继续干活，线程进入屏障通过CyclicBarrier的await()方法。
 *
 *构造方法：CyclicBarrier(int parties,Runnable barrierAction)
 * */


    public static void main(String[] args) {
        //构造方法：CyclicBarrier(int parties,Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{System.out.println("集齐7颗龙珠，召唤神龙!");});

        for (int i = 1; i <= 7; i++) {
            final int tmp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 收集到第:"+ tmp +"龙珠");
                try {
                    cyclicBarrier.await();//先到的阻塞等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }



}
