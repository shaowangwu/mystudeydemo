package juc.demo;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    /**
     * CountDownLatch:让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒
     * CountDownLatch主要有两个方法，当一个或多个线程调用await()方法时，调用线程会被阻塞。其他线程调用countDown()方法会将计数器减1(调用countDown方法不会阻塞)，
     * 当计数器的值变为0时，因调用await()方法被阻塞的线程会被唤醒，继续执行。
     * **/
    private static CountDownLatch countDownLatch = new CountDownLatch(6);

    public static void main(String[] args) throws InterruptedException {

       /* for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t");
                countDownLatch.countDown();

            },String.valueOf(i)).start();
        }*/
        for (int i = 1; i <=6; i++) {
            //Lambda 表达式 (()->{})
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"被灭.");
                //一定要有--这个方法，要不程序会一直卡在那里不动，因为没法到0，所以不会执行后面的内容
                countDownLatch.countDown();

            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }

        System.out.println("本机电脑CPU核数是:"+Runtime.getRuntime().availableProcessors());
        countDownLatch.await();
        System.out.println("当前线程名称:"+Thread.currentThread().getName());
        System.out.println(CountryEnum.SIX);
        System.out.println(CountryEnum.SIX.getRetCode());
        System.out.println(CountryEnum.SIX.getRetMessage());
    }



}
