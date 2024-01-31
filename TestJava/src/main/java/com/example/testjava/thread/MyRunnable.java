package com.example.testjava.thread;

/**
 * @ClassName MyRunnable
 * @Author TZY
 * @Date 2024/1/18 16:26
 **/
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}
class MyRunnableDemo{
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread t1 = new Thread(myRunnable,"坦克");
        Thread t2 = new Thread(myRunnable,"飞机");
        t1.start();
        t2.start();
    }
}