package com.example.testjava.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @ClassName SendDemo1
 * @Author TZY
 * @Date 2024/1/19 15:55
 **/
public class SendDemo1 {


    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String s = sc.nextLine();
            //输入的是886
            if ("886".equals(s)) {
                break;
            }
            byte[] bys = s.getBytes();
            DatagramPacket dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("192.168.1.151"), 8001);
            ds.send(dp);
        }
        ds.close();
    }
}

 class ReceiveDemo1 {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(8001);
        byte[] bys = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bys, bys.length);
        ds.receive(dp);
        System.out.println("数据是: " + new String(dp.getData(), 0, dp.getLength()));
//        ds.close();
    }
}
