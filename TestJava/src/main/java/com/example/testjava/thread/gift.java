package com.example.testjava.thread;

/**
 * @ClassName gift
 * @Author TZY
 * @Date 2024/1/18 18:10
 **/
public class gift {

    public static void main(String[] args) {
        MyThread a = new MyThread("A");
        MyThread b = new MyThread("B");
        a.start();
        b.start();
    }

    private static class MyThread extends Thread {
        static int gift = 100;

        public MyThread() {
        }

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (MyThread.class) {
                    if (gift <= 10) {
                        break;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        gift--;
                        System.out.println(getName() + "在送禮品,還剩下" + gift + "張");
                    }
                }
            }

        }
    }
}

