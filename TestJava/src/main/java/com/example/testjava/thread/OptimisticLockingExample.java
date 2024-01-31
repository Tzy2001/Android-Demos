package com.example.testjava.thread;

/**
 * @ClassName OptimisticLockingExample
 * @Author TZY
 * @Date 2024/1/19 12:08
 **/
public class OptimisticLockingExample {
    private int data = 0;
    private int version = 0; // 版本号

    public void updateData(int newData) {
        // 乐观锁实现
        int currentVersion = version;
        // 模拟一些其他可能耗时的操作
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (currentVersion == version) {
            // 检查版本号是否仍然一致
            data = newData;
            version++;
            System.out.println("更新成功，当前数据：" + data + "，版本号：" + version);
        } else {
            // 版本号不一致，说明在更新期间有其他线程修改了数据
            System.out.println("更新失败，版本号不一致");
        }
    }
}

class Main {
    public static void main(String[] args) {
        OptimisticLockingExample example = new OptimisticLockingExample();

        // 启动两个线程进行数据更新
        new Thread(() -> example.updateData(1)).start();
        new Thread(() -> example.updateData(2)).start();
    }
}
