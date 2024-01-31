package com.example.testjava.thread;

/**
 * @ClassName MyThread
 * @Author TZY
 * @Date 2024/1/18 16:19
 **/
public class MyThread extends Thread {
    public MyThread(){
    }
    public MyThread(String name){
        super(name);
    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
//            System.out.println(i);

/*线程休眠*/
//            try{
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
            System.out.println(getName()+":"+i);
        }
    }
}

class MyThreadDemo {
    public static void main(String[] args) {
//        MyThread m1 = new MyThread();
//        MyThread m2 = new MyThread();
//        m1.setName("飞机");
//        m2.setName("大炮");

        MyThread m1 = new MyThread("飞机");
//        - 两种调度方式
//
//                - 分时调度模型：所有线程轮流使用 CPU 的使用权，平均分配每个线程占用 CPU 的时间片
//                - 抢占式调度模型：优先让优先级高的线程使用 CPU，如果线程的优先级相同，那么会随机选择一个，优先级高的线程获取的 CPU 时间片相对多一些
//
//                - Java使用的是抢占式调度模型
//                - 随机性
//
//        假如计算机只有一个 CPU，那么 CPU 在某一个时刻只能执行一条指令，线程只有得到CPU时间片，也就是使用权，才可以执行指令。所以说多线程程序的执行是有随机性，因为谁抢到CPU的使用权是不一定的
        m1.setPriority(10);
        MyThread m2 = new MyThread("大炮");
        m2.setPriority(1);
        m1.start();
        m2.start();
        System.out.println("==============================");
        System.out.println(Thread.currentThread().getName());
        System.out.println("==============================");
    }
}
