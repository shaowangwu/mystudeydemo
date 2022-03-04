package juc.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列SynchronousQueue演示
 * 该队列不存储，产生一个消费一个，没消费掉就阻塞不生产
 * ***/
public class SynchronousQueueDemo {




    public static void main(String[] args) {
        //左边接口，右边实现类
        BlockingQueue blockingQueue = new SynchronousQueue();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put aa");
                blockingQueue.put("aa");
                System.out.println(Thread.currentThread().getName()+"\t put bb");
                blockingQueue.put("bb");
                System.out.println(Thread.currentThread().getName()+"\t put cc");
                blockingQueue.put("cc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5L);
                System.out.println(blockingQueue.take());

                TimeUnit.SECONDS.sleep(5L);
                System.out.println(blockingQueue.take());

                TimeUnit.SECONDS.sleep(5L);
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();
    }













}
