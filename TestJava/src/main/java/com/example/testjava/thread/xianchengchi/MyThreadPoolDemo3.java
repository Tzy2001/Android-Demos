package com.example.testjava.thread.xianchengchi;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyThreadPoolDemo3
 * @Author TZY
 * @Date 2024/1/19 11:51
 **/
public class MyThreadPoolDemo3 {
    //    参数一：核心线程数量
//    参数二：最大线程数
//    参数三：空闲线程最大存活时间
//    参数四：时间单位
//    参数五：任务队列
//    参数六：创建线程工厂
//    参数七：任务的拒绝策略


//    corePoolSize：   核心线程的最大值，不能小于0
//    maximumPoolSize：最大线程数，不能小于等于0，maximumPoolSize >= corePoolSize
//    keepAliveTime：  空闲线程最大存活时间,不能小于0
//    unit：           时间单位
//    workQueue：      任务队列，不能为null
//    threadFactory：  创建线程工厂,不能为null
//    handler：        任务的拒绝策略,不能为null


//    ThreadPoolExecutor.AbortPolicy: 		    丢弃任务并抛出RejectedExecutionException异常。是默认的策略。
//    ThreadPoolExecutor.DiscardPolicy： 		   丢弃任务，但是不抛出异常 这是不推荐的做法。
//    ThreadPoolExecutor.DiscardOldestPolicy：    抛弃队列中等待最久的任务 然后把当前任务加入队列中。
//    ThreadPoolExecutor.CallerRunsPolicy:        调用任务的run()方法绕过线程池直接执行。

    //    明确线程池对多可执行的任务数 = 队列容量 + 最大线程数
    public static void main(String[] args) {

//        ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,2, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10),
//                Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//
//        pool.submit(new MyRunnable());
//        pool.submit(new MyRunnable());
//        pool.shutdown();



//    ThreadPoolExecutor.AbortPolicy: 		    丢弃任务并抛出RejectedExecutionException异常。是默认的策略。

        /**
         * 核心线程数量为1 ， 最大线程池数量为3, 任务容器的容量为1 ,空闲线程的最大存在时间为20s
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1 , 3 , 20 , TimeUnit.SECONDS ,
                new ArrayBlockingQueue<>(1) , Executors.defaultThreadFactory() , new ThreadPoolExecutor.AbortPolicy()) ;

        // 提交5个任务，而该线程池最多可以处理4个任务，当我们使用AbortPolicy这个任务处理策略的时候，就会抛出异常
        for(int x = 0 ; x < 5 ; x++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(java.lang.Thread.currentThread().getName() + "---->> 执行了任务");
            });
        }


//    ThreadPoolExecutor.DiscardPolicy： 		   丢弃任务，但是不抛出异常 这是不推荐的做法。
        /**
         * 核心线程数量为1 ， 最大线程池数量为3, 任务容器的容量为1 ,空闲线程的最大存在时间为20s
         */
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1 , 3 , 20 , TimeUnit.SECONDS ,
//                new ArrayBlockingQueue<>(1) , Executors.defaultThreadFactory() , new ThreadPoolExecutor.DiscardPolicy()) ;
//
//        // 提交5个任务，而该线程池最多可以处理4个任务，当我们使用DiscardPolicy这个任务处理策略的时候，控制台不会报错
//        for(int x = 0 ; x < 5 ; x++) {
//            threadPoolExecutor.submit(() -> {
//                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务");
//            });
//        }

//    ThreadPoolExecutor.DiscardOldestPolicy：    抛弃队列中等待最久的任务 然后把当前任务加入队列中。

        /**
         * 核心线程数量为1 ， 最大线程池数量为3, 任务容器的容量为1 ,空闲线程的最大存在时间为20s
         */
//        ThreadPoolExecutor threadPoolExecutor;
//        threadPoolExecutor = new ThreadPoolExecutor(1 , 3 , 20 , TimeUnit.SECONDS ,
//                new ArrayBlockingQueue<>(1) , Executors.defaultThreadFactory() , new ThreadPoolExecutor.DiscardOldestPolicy());
//        // 提交5个任务
//        for(int x = 0 ; x < 5 ; x++) {
//            // 定义一个变量，来指定指定当前执行的任务;这个变量需要被final修饰
//            final int y = x ;
//            threadPoolExecutor.submit(() -> {
//                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务" + y);
//            });
//        }


//    ThreadPoolExecutor.CallerRunsPolicy:        调用任务的run()方法绕过线程池直接执行。
        /**
         * 核心线程数量为1 ， 最大线程池数量为3, 任务容器的容量为1 ,空闲线程的最大存在时间为20s
         */
//        ThreadPoolExecutor threadPoolExecutor;
//        threadPoolExecutor = new ThreadPoolExecutor(1 , 3 , 20 , TimeUnit.SECONDS ,
//                new ArrayBlockingQueue<>(1) , Executors.defaultThreadFactory() , new ThreadPoolExecutor.CallerRunsPolicy());
//
//        // 提交5个任务
//        for(int x = 0 ; x < 5 ; x++) {
//            threadPoolExecutor.submit(() -> {
//                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务");
//            });
//        }
    }
}
