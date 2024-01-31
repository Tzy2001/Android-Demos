package com.example.testjava.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName LotteryBetter
 * @Author TZY
 * @Date 2024/1/19 10:51
 **/
public class LotteryBetter {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 10, 5, 20, 50, 100, 200, 500, 800, 2, 80, 300, 700);
        MyCallable mc = new MyCallable(list);


        //創建多線程運行結果的管理者對象
        //線程1
        FutureTask<Integer> ft1 = new FutureTask<>(mc);
        //線程1
        FutureTask<Integer> ft2 = new FutureTask<>(mc);

        //創建線程對象
        Thread t1 = new Thread(ft1);
        Thread t2 = new Thread(ft2);

        t1.setName("抽獎箱1");
        t2.setName("抽獎箱2");

        t1.start();
        t2.start();

        Integer max1 = ft1.get();
        Integer max2 = ft2.get();

        //在此次抽奖过程中,抽奖箱2中产生了最大奖项,该奖项金额为800元
        if(max1 == null){
            System.out.println("在此次抽奖过程中,抽奖箱2中产生了最大奖项,该奖项金额为"+max2+"元");
        }else if(max2 == null){
            System.out.println("在此次抽奖过程中,抽奖箱1中产生了最大奖项,该奖项金额为"+max1+"元");
        }else if(max1 > max2){
            System.out.println("在此次抽奖过程中,抽奖箱1中产生了最大奖项,该奖项金额为"+max1+"元");
        }else if(max1 < max2){
            System.out.println("在此次抽奖过程中,抽奖箱2中产生了最大奖项,该奖项金额为"+max2+"元");
        }else{
            System.out.println("两者的最大奖项是一样的");
        }
    }
    private static class MyCallable implements Callable<Integer> {

        ArrayList<Integer> list;

        public MyCallable(ArrayList<Integer> list) {
            this.list = list;
        }


        @Override
        public Integer call() throws Exception {
            ArrayList<Integer> boxList = new ArrayList<>();
            while (true) {
                synchronized (MyCallable.class) {
                    if (list.size() == 0) {
                        System.out.println(Thread.currentThread().getName() + boxList);
                        break;
                    } else {
                        //繼續抽獎
                        Collections.shuffle(list);
                        int prize = list.remove(0);
                        boxList.add(prize);
                    }
                }
                Thread.sleep(10);
            }
            //把集合中的最大值返回
            if (boxList.size() == 0) {
                return null;
            } else {
                return Collections.max(boxList);
            }
        }
    }
}
