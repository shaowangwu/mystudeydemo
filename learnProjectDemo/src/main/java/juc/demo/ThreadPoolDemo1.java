package juc.demo;

import java.util.concurrent.*;

/**
* @author:shaowangwu
* @Date: 2022/3/5 8:23
* Description:线程池
*/
public class ThreadPoolDemo1 {

    /***
     * 1.为什么要使用线程池？
     * 线程池做的主要工作是控制运行线程的数量，处理过程中将任务放进队列，然后在线程创建后启动
     * 这些任务，如果线程数量超过了最大线程数，超出数量的线程任务进入队列排队等候，等其他线程执行完，
     * 再从队列中取出任务来执行。如果已经启动了最大线程数和队列塞满了，就根据配置的拒绝策略来决定拒绝还是其他。
     *
     * 使用线程池的优势：
     * 线程池的主要特点是：1.线程复用，减少上下文切换。2.控制最大并发数；3.管理线程
     * (上下文切换是存储和恢复 CPU 状态的过程，它使得线程执行能够从中断点恢复执.
     * 在上下文切换过程中，CPU 会停止处理当前运行的程序，并保存当前程序运行的)
     *
     * 第一：降低资源消耗，通过重复利用已创建的线程降低线程创建和销毁造成的消耗；
     * 第二：提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
     * 第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，
     * 使用线程池可以进行统一分配，调优和监控。
     *
     *
     * **/


    /***
     * Java中线程池是通过Executor框架实现的，该框架中用到了 Executor,C:Executors(工具类),ExecutorService,
     * ThreadPoolExecutor 这几个类。
     * C:ThreadPoolExecutor -->C:AbstractExecutorService-->I:ExecutorService-->I:Executor
     * C:ScheduledThreadPoolExecutor-->C:ThreadPoolExecutor、I:ScheduledExecutorService(-->I:ExecutorService)
     * （了解）Executors.newScheduledThreadPool()
     * (java8) Executors.newWorkStealingPool(int)
     *
     * 重点三种，但真正公司里都不用，公司里用的是自己手动创建填写参数，参考阿里巴巴开发手册：
     *Executors.newFixedThreadPool(int);执行长期的任务
     *Executors.newSingleThreadExecutor();一个任务一个任务执行的场景
     *Executors.newCachedThreadPool();适用执行很多线程短期异步小程序或者负载较轻的服务器
     * ***/

    public static void main(String[] args)
    {
        //ctrl+alt+v:自动补全方法返回参数类型
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        /*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());*/
        //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        //线程池创建多线程方法，threadPool.execute();threadPool.submit();
        //参数函数式接口可以使用 Lamda表达式 threadPool.execute(Runnable r),
        try {
            for (int i = 1; i <=10; i++) {
                fixedThreadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务.");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fixedThreadPool.shutdown();
        }


    }


    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters.
     *
     * @param corePoolSize the number of threads to keep in the pool, even
     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *        pool
     * @param keepAliveTime when the number of threads is greater than
     *        the core, this is the maximum time that excess idle threads
     *        will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @param threadFactory the factory to use when the executor
     *        creates a new thread
     * @param handler the handler to use when execution is blocked
     *        because the thread bounds and queue capacities are reached
     * @throws IllegalArgumentException if one of the following holds:<br>
     *         {@code corePoolSize < 0}<br>
     *         {@code keepAliveTime < 0}<br>
     *         {@code maximumPoolSize <= 0}<br>
     *         {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException if {@code workQueue}
     *         or {@code threadFactory} or {@code handler} is null
     * threadFactory:可以设置线程名字
     */
    //公司里手动创建线程池的方法，不采用框架方式。
    private void ThreadPoolExecutor(int corePoolSize,
                                   int maximumPoolSize,
                                   long keepAliveTime,
                                   TimeUnit unit,
                                   BlockingQueue<Runnable> workQueue,
                                   ThreadFactory threadFactory,
                                   RejectedExecutionHandler handler){

    }









}
