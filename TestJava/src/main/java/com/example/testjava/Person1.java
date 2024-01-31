package com.example.testjava;

/**
 * @ClassName Person1
 * @Author TZY
 * @Date 2024/1/19 15:10
 **/
public class Person1 implements Comparable{
    String name;
    int age;
    Person1(String name,int age)
    {
        this.name=name;
        this.age=age;
    }


    @Override
    public int compareTo(Object o) {
        Person1 p = (Person1) o;
        if (this.name.compareTo(p.name)!=0){
            return this.name.compareTo(p.name);
        }else {
            if (this.age > p.age) {
                return 1;
            } else if (this.age < p.age) {
                return -1;
            }
        }
        return 0;
    }
}
