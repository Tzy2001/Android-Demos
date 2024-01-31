package com.example.testjava.dynamicProxies;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyProxyUtil
 * @Author TZY
 * @Date 2024/1/18 15:43
 **/
public class MyProxyUtil {

    public static void main(String[] args) {
        //1.真正干活的人
        final ArrayList<String> list = new ArrayList<>();

        //2.创建干活的人
        List proxyList = (List) Proxy.newProxyInstance(

                MyProxyUtil.class.getClassLoader(),
                new Class[]{List.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        //对add方法做一个增强，统计耗时时间
                        if ("add".equals(method.getName())) {
                            long start = System.currentTimeMillis();
                            method.invoke(list, objects);
                            long end = System.currentTimeMillis();
                            System.out.println("耗时时间:" + (end - start));
                            return true;
                        } else if ("remove".equals(method.getName())) {
                            System.out.println("拦截了按照对象删除的方法");
                            return false;
                        } else if ("remove".equals(method.getName()) && objects[0] instanceof Integer) {
                            System.out.println("拦截了按照索引删除的方法");
                            return null;
                        } else {
                            return method.invoke(list, objects);
                        }
                    }
                }
        );

        proxyList.add("aaa");
        proxyList.add("ddd");
        proxyList.add("ccc");
        proxyList.add("nnn");
        proxyList.remove(0);
        proxyList.remove("aaa");

        System.out.println(list);
    }
}
