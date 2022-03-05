package juc.demo;

/**
* @author:shaowangwu
* @Date: 2022/3/5 14:34
* Description:死锁编码及定位分析
 *
 * 定位过程：
 * 1.IDEA-->当前类代码--右键Open in Explorer -->
 * 电脑上端显示的类代码所在路径栏，输入cmd，进入cmd命令窗口
 * 2.jps -l  如下，我们自己写的代码DeadLockDemo的进程号是14184
 * 6148
 * 13112 sun.tools.jps.Jps
 * 14184 juc.demo.DeadLockDemo
 * 12604 org.jetbrains.jps.cmdline.Launcher
 *
 * 3.jstack 14184   即可看到如下死锁信息。....Found 1 deadlock.
 *
 *Java stack information for the threads listed above:
 * ===================================================
 * "ThreadAAA":
 *         at juc.demo.HoldLockThread.run(DeadLockDemo.java:25)
 *         - waiting to lock <0x00000007194db8f0> (a java.lang.String)
 *         - locked <0x00000007194db8c0> (a java.lang.String)
 *         at java.lang.Thread.run(java.base@11.0.14/Thread.java:834)
 * "ThreadBBB":
 *         at juc.demo.HoldLockThread.run(DeadLockDemo.java:25)
 *         - waiting to lock <0x00000007194db8c0> (a java.lang.String)
 *         - locked <0x00000007194db8f0> (a java.lang.String)
 *         at java.lang.Thread.run(java.base@11.0.14/Thread.java:834)
 *
 * Found 1 deadlock.
 *
*/
class HoldLockThread implements Runnable{

    private String lockA;
    private String lockB;

    //get,set方法，构造方法，快捷键：alt+insert
    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 持有锁："+lockA+"，并且请求持有"+lockB+"\t");

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t 持有锁："+lockB+"，并且请求持有"+lockA+"\t");

            }
        }
    }



}
/***
 * 死锁是指两个或两个以上的线程1在执行过程中，因争夺资源而造成的一种互相等待的现象，
 * 若无外力干涉它们都将无法进行下去。
 *
 * 产生死锁主要原因：
 * 系统资源不足；
 * 进程运行推进的顺序不合适；
 * 资源分配不当
 *
 * @author Administrator**/
public class DeadLockDemo {


    public static void main(String[] args) {
        String lockA="lockA";
        String lockB="lockB";

        //new Thread(()->{},"ThreadAAA").start();//lambda表达式写法
        new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"ThreadBBB").start();
        /***
         * linux ps -ef|grep xxx
         * 类比：window，但我们只要查看的是Java进程，jps=java ps         jps -l
         *
         * */

    }










    
    
}
