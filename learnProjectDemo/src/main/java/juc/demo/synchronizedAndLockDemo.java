package juc.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource
{
    private int number = 1;//标识位，用来标识不同线程。A:1  B:2 C:3
    private Lock lock = new ReentrantLock();
    //同一把锁lock绑定了多个条件Condition不同线程，依次signal()唤醒
    private Condition c1 = lock.newCondition();//A线程
    private Condition c2 = lock.newCondition();//B线程
    private Condition c3 = lock.newCondition();//C线程

    public void print5(){
        try {
            lock.lock();
            //1 判断
            while (number!=1){
                c1.await();
            }
            //2 干活
            for (int i = 1; i <=5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知唤醒,A干完活后通知B
            number = 2;
            c2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print10(){
        try {
            lock.lock();
            //1 判断
            while (number!=2){
                c2.await();
            }
            //2 干活
            for (int i = 1; i <=10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知唤醒,B干完活后通知C
            number = 3;
            c3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print15(){
        try {
            lock.lock();
            //1 判断
            while (number!=3){
                c3.await();
            }
            //2 干活
            for (int i = 1; i <=15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //3 通知唤醒,C干完活后通知A
            number = 1;
            c1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}

public class synchronizedAndLockDemo {

    /**
     * synchronized:Object wait()  notify()
     * lock:await()  signal()
     * 题目：synchronized,lock有什么区别？用新的lock有什么好处，
     * 还有后续3.0版的LockSupport.park()等呢
     *
     * 1.原始构成
     * synchronized是关键字属于JVM层面，
     * monitorenter(底层是通过monitor对象来完成的，其实wait/notify方法也依赖于monitor对象，只有再同步代码块或者同步方法中才能调用wait/notify等方法)
     * monitorexit
     *Lock是接口，ReentrantLock是具体类(java.util.concurrent.locks.Lock)是api层面的锁，
     *
     *2.使用方法
     * synchronized 不需要用户手动去释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
     * ReentrantLock 则需要程序员手动释放锁，若没有主动释放锁，就可能导致出现死锁现象。需要lock(),unLock()方法配合try finally语句来使用。
     *
     * 3.等待释放可中断
     * synchronized 不可中断，除非抛出异常或者正常运行完成
     * ReentrantLock 可中断：1.设置超时方法 tryLock(Long timeout,TimeUnit unit)
     *                      2.LockInterruptiby()放代码块中，调用interrupt()方法可中断
     * 4.默认都是非公平锁，但ReentrantLock可以设置为公平锁。
     *
     * 5.锁绑定多个条件Condition
     * synchronized 则没有。
     * ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个要么唤醒全部线程去抢夺锁。
     *
     * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动。要求如下：
     * AA打印5次，BB打印10次，CC打印15次
     * 紧接着
     * AA打印5次，BB打印10次，CC打印15次
     * 来10轮
     * ***/

    /*public static void main(String[] args) {
        synchronized (new Object()){
            System.out.println("111111");
        }

        new ReentrantLock();

    }*/



     /* 5.锁绑定多个条件Condition
     * synchronized 则没有。
     * ReentrantLock 用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个要么唤醒全部线程去抢夺锁。
     *
     * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动。要求如下：
     * AA打印5次，BB打印10次，CC打印15次
     * 紧接着
     * AA打印5次，BB打印10次，CC打印15次
     * 来10轮
     * ***/
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        //A线程
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                shareResource.print5();
            }
        },"A").start();

        //B线程
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                shareResource.print10();
            }
        },"B").start();

        //C线程
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                shareResource.print15();
            }
        },"C").start();


    }



}
