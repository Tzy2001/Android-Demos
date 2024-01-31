package com.example.testjava.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName IntetAddressDemo
 * @Author TZY
 * @Date 2024/1/19 15:44
 **/
public class InetAddressDemo {

//    | static InetAddress getByName(String host) | 确定主机名称的IP地址。主机名称可以是机器名称，也可以是IP地址 |
//            | ----------------------------------------- | ------------------------------------------------------------ |
//            | String getHostName()                      | 获取此IP地址的主机名                                         |
//            | String getHostAddress()                   | 返回文本显示中的IP地址字符串                                 |
    public static void main(String[] args) throws UnknownHostException {
        //InetAddress address = InetAddress.getByName("itheima");
        InetAddress address = InetAddress.getByName("192.168.8.148");

        String hostName = address.getHostName();
        String hostAddress = address.getHostAddress();
        System.out.println("hostName: " + hostName + "hostAddress: " + hostAddress);
    }

}
