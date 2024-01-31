package com.example.testjava.thread;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @ClassName lottery
 * @Author TZY
 * @Date 2024/1/19 9:54
 **/
public class lottery {


    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 10, 5, 20, 50, 100, 200, 500, 800, 2, 80, 300, 700);

        MyThread m1 = new MyThread(list);
        MyThread m2 = new MyThread(list);
        m1.setName("抽獎箱1");
        m2.setName("抽獎箱2");
        m1.start();
        m2.start();
    }

    private static class MyThread extends Thread {

//        ArrayList<Integer> list;
//
//        public MyThread(ArrayList<Integer> list) {
//            this.list = list;
//        }
//
//        @Override
//        public void run() {
//            ArrayList<Integer> boxList = new ArrayList<>();//1 //2
//            while (true) {
//                synchronized (MyThread.class) {
//                    if (list.size() == 0) {
//                        System.out.println(getName() + boxList);
//                        break;
//                    } else {
//                        //继续抽奖
//                        Collections.shuffle(list);
//                        int prize = list.remove(0);
//                        boxList.add(prize);
//                    }
//                }
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }

        ArrayList<Integer> list;


//        //线程一
//        static ArrayList<Integer> list1 = new ArrayList<>();
//        //线程二
//        static ArrayList<Integer> list2 = new ArrayList<>();

        public MyThread() {
        }

        public MyThread(ArrayList<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            ArrayList<Integer> boxList = new ArrayList<>();
            while (true) {
                synchronized (MyThread.class) {
                    if (list.size() == 0) {
//                        if("抽獎箱1".equals(getName())){
//                            System.out.println("抽獎箱1" + list1);
//                        }else {
//                            System.out.println("抽獎箱2" + list2);
//                        }
                        System.out.println(getName()+boxList);
                        break;
                    } else {
                        //打亂順序
                        Collections.shuffle(list);
                        int prize = list.remove(0);
//                        if("抽獎箱1".equals(getName())){
//                            list1.add(prize);
//                        }else {
//                            list2.add(prize);
//                        }
                        boxList.add(prize);
//                        System.out.println("恭喜" + getName() + " 抽到了" + prize + "的大獎");
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
