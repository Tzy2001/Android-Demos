package com.example.testjava.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName Server
 * @Author TZY
 * @Date 2024/1/19 17:11
 **/
public class Server {
    //1. accept方法是阻塞的,作用就是等待客户端连接
//2. 客户端创建对象并连接服务器,此时是通过三次握手协议,保证跟服务器之间的连接
//3. 针对客户端来讲,是往外写的,所以是输出流
//            针对服务器来讲,是往里读的,所以是输入流
//4. read方法也是阻塞的
//5. 客户端在关流的时候,还多了一个往服务器写结束标记的动作
//6. 最后一步断开连接,通过四次挥手协议保证连接终止
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10000);
        Socket socket = ss.accept();
        InputStream is = socket.getInputStream();
        //使用中文的时候
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        int b;
        while ((b = is.read()) != -1) {
            System.out.println((char) b);
        }
        is.close();
        socket.close();
        ss.close();
    }
}
