package juc.demo;

import java.util.concurrent.*;

/**
* @author:shaowangwu
* @Date: 2022/3/5 10:42
* Description:线程池工作原理
*/
public class ThreadPoolDemo2 {

    /***
     * 线程池7大参数
     * RejectedExecutionHandler：默认策略是达到最大线程数，
     * 而且队列塞满时拒绝任务并抛出异常 RejectedExecutionException
     * BlockingQueue：是阻塞队列，是接口，
     * 创建线程池时候自己传实现了这个接口的队列，如 new LinkedBlockingQueue<Runnable>()
     * threadFactory = Executors.defaultThreadFactory()//默认
     * keepAliveTime：多余的空闲线程的存活时间。
     * 当线程池数量超过corePoolSize,当空闲时间达到keepAliveTime值时,多余空闲线程会被销毁
     * 直到只剩下corePoolSize个线程为止。
     * */
    private void ThreadPoolExecutor(int corePoolSize,
                                    int maximumPoolSize,
                                    long keepAliveTime,
                                    TimeUnit unit,
                                    BlockingQueue<Runnable> workQueue,
                                    ThreadFactory threadFactory,
                                    RejectedExecutionHandler handler){};
    /***
     * 线程池拒绝策略：以下jdk内置拒绝策略均实现了RejectedExecutionHandler接口
     *
     *JDK内置拒绝策略：
     *AbortPolicy(默认,但生产上这种不能用):直接抛出异常 RejectedExecutionException，异常阻止系统正常运行。new ThreadPoolExecutor.AbortPolicy()
     *CallerRunsPolicy:"调用者运行"一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退给调用者。
     *DiscardOldestPolicy:抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务。
     *DiscardPolicy:直接丢弃任务，不予任何处理也不抛出异常。如果允许任务丢失，这是最好的一种方案。
     *
     * 4. <<阿里巴巴java开发手册>></>【强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这
     * 样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
     * 说明：Executors 返回的线程池对象的弊端如下： 1） FixedThreadPool 和 SingleThreadPool：
     * 允许的请求队列长度为 Integer.MAX_VALUE，(最大值21个亿,)可能会堆积大量的请求，从而导致 OOM。 2） CachedThreadPool：
     * 允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。
     * */

    /***
     * 实际公司中，以上三种框架提供的创建线程池方式我们都不使用，使用的是自己手动创建的线程池，自己传参数进去。
     * 通过 ThreadPoolExecutor 手动创建线程池,注意new LinkedBlockingDeque<>(5) 一定要填队列的容量，
     * 否则默认是 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM
     * new ThreadPoolExecutor.CallerRunsPolicy():这种拒绝策略是将某些任务回退给调用者main线程。
     * **/
    public static void main(String[] args) {
        //手动创建线程池，CPU密集型任务如计算处理，线程个数n=CPU核数+1，IO密集型如网络IO,磁盘io 线程个数n=2*CPU核数;
        //由于IO密集型任务并不是一直在执行任务,则应配置尽可能多的线程,(1)如n=CPU核数*2，
        // 或者(2)由于IO密集型大部分线程都阻塞，所以n=CPU核数/(1-阻塞系数0.8~0.9),如4核cpu，n=4/(1-0.9)=40
        int n = Runtime.getRuntime().availableProcessors()+1;
        ExecutorService threadPool = new ThreadPoolExecutor(n,2*n,1L,
                TimeUnit.MINUTES,new LinkedBlockingDeque<>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());

        threadPoolInit(threadPool);

    }

    public static void threadPoolInit(ExecutorService threadPool)
    {
        //ctrl+alt+v:自动补全方法返回参数类型
        /*ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();*/

        /***
         * 实际公司中，以上三种框架提供的创建线程池方式我们都不使用，使用的是自己手动创建的线程池，自己传参数进去。
         * 通过 ThreadPoolExecutor 手动创建线程池
         * **/

        try {
            for (int i = 1; i <=20; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务----");
                });
                //TimeUnit.SECONDS.sleep(1L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }








}
