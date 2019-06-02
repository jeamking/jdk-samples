package com.demo.threads.threadPools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor示例：根据以下运行结果，研究为什么线程6、7在最后执行。
 * 
 * 大家好，我是线程：1
大家好，我是线程：2
大家好，我是线程：3
大家好，我是线程：4
大家好，我是线程：5
大家好，我是线程：8
大家好，我是线程：9
大家好，我是线程：10
大家好，我是线程：11
大家好，我是线程：12
大家好，我是线程：6
大家好，我是线程：7

描述一下上面案例的执行流程：

1）首先通过 ThreadPoolExecutor 构造函数创建线程池；
2）执行 for 循环，提交 12 个任务（为什么是12个任务呢？）；
3）通过 threadPoolExecutor.submit 提交 Runnable 接口实现的执行任务；
4）提交第1个任务时，由于当前线程池中正在执行的任务为 0 ，小于 5（corePoolSize 指定），所以会创建一个线程用来执行提交的任务1；
5）提交第 2， 3， 4， 5 个任务的时候，由于当前线程池中正在执行的任务数量小于等于 5 （corePoolSize 指定），所以会为每一个提交的任务创建一个线程来执行任务；
6）当提交第6个任务的时候，由于当前正在执行的任务数量为 5 （因为每个线程任务执行时间为10s，所以提交第6个任务的时候，前面5个线程都还在执行中），此时会将第6个任务存放到 workQueue 队列中等待执行；
7）由于 workQueue 队列的大小为 2 ，所以该队列中也就只能保存 2 个等待执行的任务，所以第7个任务也会保存到任务队列中；
8）当提交第8个任务的时候，因为当前线程池正在执行的任务数量为5，workQueue 队列中存储的任务数量也满了，这时会判断当前线程池中正在执行的任务的数量是否小于10（maximumPoolSize指定）；
9）如果小于 10 ，那么就会新创建一个线程来执行提交的任务 8；
10）执行第9,10,11,12个任务的时候，也要判断当前线程池中正在执行的任务数是否小于10（maximumPoolSize指定），如果小于10，那么也会立即新建线程来执行这些提交的任务；
11）此时，12个任务都已经提交完毕，那 workQueue 队列中的等待 任务6 和 任务7 什么时候执行呢？
12）当任务1执行完毕后（10s后），执行任务1的线程并没有被销毁掉，而是获取 workQueue 中的任务6来执行；
13）当任务2执行完毕后，执行任务2的线程也没有被销毁，而是获取 workQueue 中的任务7来执行；

通过上面流程的分析，也就知道了之前案例的输出结果的原因。其实，线程池中会线程执行完毕后，并不会被立刻销毁，线程池中会保留 corePoolSize 数量的线程，当 workQueue 队列中存在任务或者有新提交任务时，那么会通过线程池中已有的线程来执行任务，避免了频繁的线程创建与销毁，而大于 corePoolSize 小于等于 maximumPoolSize 创建的线程，则会空闲指定时间（keepAliveTime）后进行回收。
现在有个问题，为什么上面的例子要提交12个任务呢？创建13个任务行不行？

可以看到，第13个任务提交的时候报错了，因为线程池中正在运行的线程数量为10，当前 workQueue（容量为2） 中的任务也已经满了，在提交第13个任务的时候，就会提交失败，报错 RejectedExecutionException 异常，所以上面创建的例子中，同时运行最大的任务数为12；

 *
 */
public class ThreadPoolExecutorDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
        //线程池中的核心线程数量
        int corePoolSize = 5;
        //当前线程池正在运行的最大线程数量
        int maximumPoolSize = 10;
        //超过 corePoolSize 线程数量的线程最大空闲时间
        long keepAliveTime = 2;
        //以秒为时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
        //创建线程池
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        unit,
                        workQueue);

        //循环提交任务
        for(int i = 0; i < 12; i++) {
            //提交任务的索引
            final int index = (i + 1);
            threadPoolExecutor.submit(() -> {
                //线程打印输出
                System.out.println("大家好，我是线程：" + index);
                try {
                    //模拟线程执行时间，10s
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            //每个任务提交后休眠500ms再提交下一个任务，用于保证提交顺序
            Thread.sleep(500);
        }
	}

}
