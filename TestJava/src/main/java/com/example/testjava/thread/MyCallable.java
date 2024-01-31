package com.example.testjava.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName MyCallable
 * @Author TZY
 * @Date 2024/1/18 16:32
 **/
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println("跟女孩表白"+i);
        }
        return "答应";
    }
}

//        | V call()                         | 计算结果，如果无法计算结果，则抛出一个异常         |
//        | -------------------------------- | -------------------------------------------------- |
//        | FutureTask(Callable<V> callable) | 创建一个 FutureTask，一旦运行就执行给定的 Callable |
//        | V get()                          | 如有必要，等待计算完成，然后获取其结果             |

//+ 定义一个类MyCallable实现Callable接口
//        + 在MyCallable类中重写call()方法
//        + 创建MyCallable类的对象
//        + 创建Future的实现类FutureTask对象，把MyCallable对象作为构造方法的参数
//        + 创建Thread类的对象，把FutureTask对象作为构造方法的参数
//        + 启动线程
//        + 再调用get方法，就可以获取线程结束之后的结果
class MyCallableDemo{
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> ft = new FutureTask<>(myCallable);
        Thread t1 = new Thread(ft);
        String s = ft.get();
        t1.start();
        System.out.println(s);
    }
}
