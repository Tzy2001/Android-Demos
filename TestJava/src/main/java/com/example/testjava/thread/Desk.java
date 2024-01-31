package com.example.testjava.thread;

/**
 * @ClassName Desk
 * @Author TZY
 * @Date 2024/1/18 17:02
 **/
public class Desk {

    private boolean flag;
    private int count;

    private final Object lock = new Object();

    public Desk() {
        this(false, 10);
    }

    public Desk(boolean flag, int count) {
        this.flag = flag;
        this.count = count;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Desk{" +
                "flag=" + flag +
                ", count=" + count +
                ", lock=" + lock +
                '}';
    }
}

class Cooker extends Thread {
    private Desk desk;

    public Cooker(Desk desk) {
        this.desk = desk;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (desk.getLock()) {
                if (desk.getCount() == 0) {
                    break;
                } else {
                    if (!desk.isFlag()) {
                        System.out.println("厨师正在生产汉堡包");
                        desk.setFlag(true);
                        desk.getLock().notifyAll();
                    } else {
                        try {
                            desk.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

class Foodie extends Thread{

    private Desk desk;
    public Foodie(Desk desk){
        this.desk= this.desk;
    }
    @Override
    public void run() {
        while (true){
            synchronized (desk.getLock()){
                if (desk.getCount()==0){
                    break;
                }else {
                    if (desk.isFlag()){
                        System.out.println("吃货吃了汉堡包");
                        desk.setFlag(false);
                        desk.notifyAll();
                        desk.setCount(desk.getCount()-1);
                    }else {
                        try{
                            desk.getLock().wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

class Demo{
    public static void main(String[] args) {
        Desk desk = new Desk();
        Foodie f = new Foodie(desk);
        Cooker c = new Cooker(desk);
        f.start();
        c.start();
    }
}
