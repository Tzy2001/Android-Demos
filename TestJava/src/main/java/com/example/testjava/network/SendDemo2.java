package com.example.testjava.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @ClassName SendDemo2
 * @Author TZY
 * @Date 2024/1/19 16:46
 **/
public class SendDemo2 {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        String s = "sdas";
        DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, InetAddress.getByName("192.168.1.151"), 8801);
        ds.send(dp);
        ds.close();
    }
}

class ReceiveDemo2 {
    public static void main(String[] args) throws IOException {


        MulticastSocket ms = new MulticastSocket(8801);
        while (true) {
            byte[] bys = new byte[1024];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            ms.joinGroup(InetAddress.getByName("224.0.1.0"));
            ms.receive(dp);
            System.out.println("數據是: " + new String(dp.getData(), 0, dp.getLength()));
            ms.close();
        }
    }
}
