package com.example.testjava.thread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName zuseduilie
 * @Author TZY
 * @Date 2024/1/18 17:29
 **/
public class ZuSeDuiLie {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> bd = new ArrayBlockingQueue<>(1);
        Foodies f = new Foodies(bd);
        Cookers c = new Cookers(bd);
        f.start();
        c.start();
    }
}

class Cookers extends Thread {
    private ArrayBlockingQueue<String> bd;

    public Cookers(ArrayBlockingQueue<String> bd) {
        this.bd = bd;
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("厨师放入一个汉堡包");
                bd.put("汉堡包");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class Foodies extends Thread {
    private ArrayBlockingQueue<String> bd;

    public Foodies(ArrayBlockingQueue<String> bd) {
        this.bd = bd;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("吃貨吃了一个汉堡包");
                bd.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}