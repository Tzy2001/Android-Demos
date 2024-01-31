package com.example.testjava.thread.xianchengchi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName MyThreadPoolDemo2
 * @Author TZY
 * @Date 2024/1/19 11:48
 **/
public class MyThreadPoolDemo2 {
    //static ExecutorService newFixedThreadPool(int nThreads)
//创建一个指定最多线程数量的线程池
    public static void main(String[] args) {
        //参数不是初始值而是最大值
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executorService;
        System.out.println(pool.getPoolSize());
        executorService.submit(() -> {
            System.out.println(java.lang.Thread.currentThread().getName() + "在执行了");
        });
        executorService.submit(() -> {
            System.out.println(java.lang.Thread.currentThread().getName() + "在执行了");
        });
        executorService.submit(() -> {
            System.out.println(java.lang.Thread.currentThread().getName() + "在执行了");
        });
        executorService.submit(() -> {
            System.out.println(java.lang.Thread.currentThread().getName() + "在执行了");
        });
        System.out.println(pool.getPoolSize());
        executorService.shutdown();

    }
}
