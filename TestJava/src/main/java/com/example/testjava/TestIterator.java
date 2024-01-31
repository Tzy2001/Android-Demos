package com.example.testjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName TestIterator
 * @Author TZY
 * @Date 2024/1/19 14:47
 **/
public class TestIterator {
    public static void main(String[] args) {//迭代器裡面不能直接刪除元素，需要使用迭代器的刪除方法刪除，而且需要在next之後刪除
        //在迭代过程中删除元素会导致元素索引的变化，因此不要使用索引来删除元素
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        System.out.println(list1.get(0));
        Iterator<String> it = list1.iterator();
        while (it.hasNext()) {
            String value = it.next();
//            if ("b".equals(value)) {
//                it.remove();
                list1.add("f");
//                list1.remove("b");
//            }
        }
        System.out.println( list1);




//Map的遍曆方式
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        //1
        for (String key : map.keySet()) {
            Integer value = map.get(key);
            System.out.println("key:" + key + "value:" + value);
        }
        //2
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("key:" + entry.getKey() + "value:" + entry.getValue());
        }
        //3
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            System.out.println("key:" + entry.getKey() + "value:" + entry.getValue());
        }
        //4
        for (Integer value : map.values()) {
            System.out.println("value:" + value);
        }
        //5
        map.forEach((key, value) -> System.out.println("key:" + key + "value:" + value));

    }
}
