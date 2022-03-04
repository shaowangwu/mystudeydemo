package juc.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
* @author:shaowangwu
* @Date: 2022/3/5 6:43
* Description:
 * 1.Thread(Runnable target,String threadName),这个线程的构造方法，
 * 传进来的是Runnable接口，线程并没有Callable接口的构造方法，那怎样实现Callable这种
 * 方式的多线程呢？
 * 编程思想：1)传接口而不是传实现类，2)通过一个既实现了Runnable接口，又有Callable接口的构造
 * 类来联系起来二者，而FutureTask就是这个类，实现了Runnable,RunnableFuture接口
 * 也有含有Callable接口的构造方法。FutureTask(Callable<V> callable),这个构造方法传进来
 * 一个实现了Callable接口的Thread即可。接口方法尽可能抽象，比如传接口，真正使用的时候传的是实
 * 现了该接口的实现类。这也是一种编程思想。
 *
 * 4种实现多线程方法：1)继承Thread 2)实现Runnable接口 3)实现Callable接口 4)线程池方式
*/

class MyThread02 implements Callable<Integer>
{

    @Override
    public Integer call() throws Exception {
        System.out.println("-----come in Callable MyThread02--------");
        TimeUnit.SECONDS.sleep(3L);
        return 1024;
    }
}
public class CallableDemo02
{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /***
         * FutureTask(Callable<V> callable),
         *使用场景：FutureTask线程有返回值，比如银行批量线程对账处理，处理失败需要返回才能知道是哪个账户处理失败
         *两个线程：一个是main线程,一个是AA-futureTask线程
         */
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread02());
        //futureTask把Callable,Runnable联系起来的中间商
        Thread t1 = new Thread(futureTask,"AA-futureTask");
        t1.start();
        //打印的是main线程,如果futureTask.get()放在start()后面，还没计算完就要结果会导致main线程阻塞.
        //这就导致多线程和单线程一样串行堵塞
        System.out.println(Thread.currentThread().getName()+"---------");
        //futureTask的get()建议放在最后,因为MyThread的计算处理需要时间,如果放前面可能就会导致阻塞一直等待算完再往下走
        int result01=100;
        //判断当计算完成再处理.类似于自旋锁思想,
        while (!futureTask.isDone()){//没处理完自旋

        }
        int result02=futureTask.get();
        System.out.println("------AA-futureTask线程,返回结果result: "+(result01+result02));
    }

}
