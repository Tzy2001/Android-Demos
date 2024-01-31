package com.example.testjava.thread;

import java.util.concurrent.Semaphore;

/**
 * @ClassName MySemaphoreDemo
 * @Author TZY
 * @Date 2024/1/19 12:13
 **/
public class MySemaphoreDemo {
    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();

        for (int i = 0; i < 100; i++) {
            new Thread(mr).start();
        }
    }
    private static class MyRunnable implements Runnable {
        //1.获得管理员对象，
        private Semaphore semaphore = new Semaphore(2);
        @Override
        public void run() {
            //2.获得通行证
            try {
                semaphore.acquire();
                //3.开始行驶
                System.out.println("获得了通行证开始行驶");
                Thread.sleep(2000);
                System.out.println("归还通行证");
                //4.归还通行证
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


