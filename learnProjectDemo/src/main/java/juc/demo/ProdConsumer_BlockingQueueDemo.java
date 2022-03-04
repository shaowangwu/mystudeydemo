package juc.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource
{
    private volatile boolean FLAG=true;//默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    /**
    *@Description 使用有参构造方式创建对象,需要调用者自己传具体类
    *@Author shaowangwu
    *@Date 2022/3/4 22:38
    *@Param 通用，传接口不传具体实现类，根据你传过来的具体实现类去采用，而不是写死某种实现类
    *@Return  但日志记录可以反射打印具体传的是什么类,方便排查问题
    *@Exception
    */
    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue=blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd()throws Exception{
        //注意：多线程中不能使用if判断，使用while
        String data=null;
        boolean retValue;
        while (FLAG){
            data=atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功.");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败.");
            }
            TimeUnit.SECONDS.sleep(1L);
        }
        System.out.println(Thread.currentThread().getName()+"\t 老板叫停了,表示FLAG=false,生产动作结束");
    }

    public void myConsumer()throws Exception
    {
            String result=null;
            while (FLAG)
            {
                result = blockingQueue.poll(2L,TimeUnit.SECONDS);
                if (null==result || result.equalsIgnoreCase(""))
                {
                    FLAG=false;
                    System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到蛋糕，消费退出");
                    System.out.println();
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName()+"\t 消费蛋糕队列"+result+",成功.");
            }

    }

    public void stop() throws Exception
    {
        this.FLAG=false;
    }


}
/**
* @author:shaowangwu
* @Date: 2022/3/4 23:18
* Description:线程通信之生产者消费者阻塞队列
 * volatile/CAS/atomicInteger/BlockingQueue/线程交互/原子引用
 *阻塞队列BlockingQueue底层使用的是LockSupport.park(),LockSupport.unPark()
*/
public class ProdConsumer_BlockingQueueDemo {

    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"ProdThread").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"ConsumerThread").start();
        System.out.println();
        System.out.println();
        System.out.println();

        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束。");
        try {
            myResource.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
