package com.example.testjava;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @ClassName MyReflectDemo
 * @Author TZY
 * @Date 2024/1/18 15:04
 **/
public class MyReflectDemo {

    public static void main(String[] args) throws IOException, IllegalAccessException {
        //对于任意一个对象，都可以把对象所有的字段名和值，保存到文件中去
        Student s = new Student("小A", 23, '女', 167.5, "睡觉");
        Teacher t = new Teacher("波妞", 10000);

        saveObject(s);
    }

    public static void saveObject(Object obj) throws IOException, IllegalAccessException {
        //1.获取字节码文件的对象
        Class<?> clazz = obj.getClass();
        //2.创建IO流
        BufferedWriter bw = new BufferedWriter(new FileWriter("myreflect\\a.txt"));
        //3.获取所有的成员变量
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //获取成员变量的名字
            String name = field.getName();
            //获取成员变量的值
            Object value = field.get(obj);
            //写出数据
            bw.write(name + "=" + value);
            bw.newLine();
        }
        bw.close();
    }
}
