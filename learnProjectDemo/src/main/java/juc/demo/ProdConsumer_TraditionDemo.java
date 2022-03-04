package juc.demo;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

class ShareData//资源类
{
    private volatile int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception
    {
        //IDEA try catch finally 快捷键：选中需要的代码，然后Ctrl+Alt+t
        try{
            lock.lock();
            //1.判断
            while (number!=0)
            {
                //步骤1.不为0，说明已经有不能生产，线程阻塞(等待)
                System.out.println("number!=0 "+Thread.currentThread().getName()+"\t"+"阻塞中，不能生产，此时number为:"+number);
                condition.await();//老版写法使用synchronized修饰increment()方法，然后这里是this.wait();还有3.0版写法

            }
            //步骤2.是0，需要干活(生产蛋糕)
            number++;
            System.out.println("number为0 "+Thread.currentThread().getName()+"\t"+"生产后number:"+number);
            //步骤3：通知唤醒消费者线程,synchronized对应的是this.notifyAll(),而ReentrantLock的是signalAll()
            condition.signalAll();

        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    public void decrement() throws Exception
    {
        //IDEA try catch finally 快捷键：选中需要的代码，然后Ctrl+Alt+t
        try{
            lock.lock();
            //1.判断
            while (number==0)
            {
                //步骤1.等于0，不能消费，等待
                System.out.println("number==0 "+Thread.currentThread().getName()+"\t"+"阻塞中，不能消费，此时number为:"+number);
                condition.await();
            }
            //步骤2.不等于0，需要进行消费
            number--;
            System.out.println("number不为0 "+Thread.currentThread().getName()+"\t"+"需要进行消费，消费后number:"+number);
            //步骤3：通知唤醒生产者的工作线程
            condition.signalAll();

        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    /**
    * @author:shaowangwu
    * @Date: 2022/3/4 18:25
    * Description:
     * 注意：synchronized判断条件是用while,不能用if，官网,As in the one argument version,interrupts and spurious wakeups are possible,and this
     * method should always be used in a loop:
    */
    //synchronized begin
     synchronized void waitObj (Object obj) throws InterruptedException {
        while (number>0){
            obj.wait();
        }
    }
    //synchronized end

}

/**
 * @author:shaowangwu
 * @Date: 2022/3/4 15:38
 * Description:题目：一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮
 * 步骤1  线程       操作     资源类
 * 步骤2  判断      干活      通知
 * 步骤3  防止虚假唤醒机制
 * synchronized-->lock-->LockSupport,LockSupport.park();LockSupport.unPark();
 */
public class ProdConsumer_TraditionDemo {

    /**
    *@Description
    *@Author shaowangwu
    *@Date 2022/3/4 17:23
    *@Param 生产者、消费者多线程交互
    *@Return
    *@Exception
    */
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        //AA线程生产者
        new Thread(()->{
            for (int i = 0; i <5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        //BB线程消费者
        new Thread(()->{
            for (int i = 0; i <5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();



    }






}
