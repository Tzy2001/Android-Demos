package com.example.testjava.dynamicProxies;

/**
 * @ClassName Test
 * @Author TZY
 * @Date 2024/1/18 15:42
 **/
public class Test {
    public static void main(String[] args) {
        BigStar bigStar = new BigStar();
        Star proxy = ProxyUtil.createProxy(bigStar);

        String result = proxy.sing("只因你太美");
        System.out.println(result);
    }
}
