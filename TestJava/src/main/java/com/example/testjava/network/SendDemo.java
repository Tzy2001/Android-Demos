package com.example.testjava.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @ClassName SendDemo
 * @Author TZY
 * @Date 2024/1/19 15:47
 **/
public class SendDemo {

//|                  DatagramSocket()       | 创建数据报套接字并将其绑定到本机地址上的任何可用端口 |
//            | DatagramPacket(byte[] buf, int len, InetAddress add, int port) | 创建数据包,发送长度为len的数据包到指定主机的指定端口 |

//    | void send(DatagramPacket p)    | 发送数据报包           |
//            | ------------------------------ | ---------------------- |
//            | void close()                   | 关闭数据报套接字       |
//            | void receive(DatagramPacket p) | 从此套接字接受数据报包 |
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();

        byte[] bys = "sadasd".getBytes();

        DatagramPacket dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("192.168.8.148"), 10078);

        ds.send(dp);
        ds.close();
    }
}
