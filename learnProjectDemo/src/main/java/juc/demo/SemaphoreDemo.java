package juc.demo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

/**
 * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个是用于并发线程数的控制。比如争车位
 *两个构造方法，带boolean表示是否使用公平锁。
 * */

public static void main(String[] args) {

    Semaphore semaphore = new Semaphore(3);//模拟3个车位

    for (int i = 1; i <=6; i++) {//模拟6部车
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+"\t 抢到车位.");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t 停车3秒后离开车位.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //离开后释放停车位资源
                semaphore.release();
            }
        },String.valueOf(i)).start();
    }



}



}
