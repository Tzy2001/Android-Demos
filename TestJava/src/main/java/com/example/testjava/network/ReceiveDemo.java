package com.example.testjava.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @ClassName ReceiveDemo
 * @Author TZY
 * @Date 2024/1/19 15:51
 **/
public class ReceiveDemo {

//    DatagramPacket(byte[] buf, int len)创建一个DatagramPacket用于接收长度为len的数据包

//    | byte[]  getData() | 返回数据缓冲区                           |
//            | ----------------- | ---------------------------------------- |
//            | int  getLength()  | 返回要发送的数据的长度或接收的数据的长度 |
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket(12345);
        byte[] bys = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bys, bys.length);

        ds.receive(dp);
        System.out.println("数据是: "+new String(dp.getData(),0,dp.getLength()));
        ds.close();

    }
}
