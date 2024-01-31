package com.example.testjava.thread.xianchengchi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName MyThreadPoolDemo
 * @Author TZY
 * @Date 2024/1/19 11:42
 **/
public class MyThreadPoolDemo {

    ///static ExecutorService newCachedThreadPool()   创建一个默认的线程池
    //static newFixedThreadPool(int nThreads)	    创建一个指定最多线程数量的线程池
    public static void main(String[] args) {
        //创建一个默认的线程池对象，池子中默认是空的，默认最多可以容纳int类型的最大值
        ExecutorService executorService = Executors.newCachedThreadPool();
        //Executors --- 可以帮助我们创建线程池对象
        //ExecutorService --- 可以帮助我们控制线程池
        executorService.submit(()->{
            System.out.println(java.lang.Thread.currentThread().getName()+"在执行了");
        });
        executorService.submit(()->{
            System.out.println(java.lang.Thread.currentThread().getName()+"在执行了");
        });
        executorService.shutdown();
    }
}
