package com.example.testjava.thread;

/**
 * @ClassName Ticket
 * @Author TZY
 * @Date 2024/1/18 17:56
 **/
public class Ticket {


    public static void main(String[] args) {
        MyThread m1 = new MyThread("窗口1");
        MyThread m2 = new MyThread("窗口2");
        m1.start();
        m2.start();
    }
    private static class MyThread extends Thread {
        //第一种方式实现多线程，测试类中MyThread会创建多次，需要加一个static
        static int ticket = 1000;

        public MyThread(){}
        public MyThread(String name){super(name);}
        @Override
        public void run() {
            while (true){
                synchronized (MyThread.class) {
                    if (ticket == 0) {
                        break;
                    }else{
                        try{
                            Thread.sleep(3000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        ticket--;
                        System.out.println(getName()+"在卖票，还剩下"+ticket+"张票！！！");
                    }
                }
            }
        }
    }
}


