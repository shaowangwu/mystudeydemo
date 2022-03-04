package juc.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    /**
     * *BlockingQueue是接口，不是类。
     * 核心方法
     * *
     * */

    /**
     * 抛出异常组,add IllegalStateException   remove NoSuchElementException
     * 插入add(e) 移除 remove() 检查 element()
     * **/


    /**
     * 阻塞组，一直等待不中断
     * 插入put(e) 移除 take() 检查:不可用
     * **/


    /**
     * 超时不候
     * 插入offer(e,time,unit) 移除 poll(time,unit) 检查:不可用
     * 超时退出：当队列阻塞队列满时，队列会阻塞生产者线程一定时间，超过限时时间后生产者线程会退出
     * * **/
    public static void timeOutBlockingQueue() throws InterruptedException {

        //List list = new ArrayList();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);//ArrayBlockingQueue：由数组结构组成的有界阻塞队列
        System.out.println(blockingQueue.offer("a",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c",2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d",2L, TimeUnit.SECONDS));
    }


    /**
     * ArrayBlockingQueue：由数组结构组成的有界阻塞队列
     * linkedBlockingQueue:由链表结构组成的有界(但大小默认是Integer.MAX_VALUE)阻塞有界队列
     * SynchronousQueue:不存储元素的阻塞队列，也即单个元素的队列
     * ***/
    public static void main(String[] args) throws InterruptedException {
        timeOutBlockingQueue();
    }

}
