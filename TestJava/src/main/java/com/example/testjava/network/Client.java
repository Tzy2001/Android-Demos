package com.example.testjava.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @ClassName Client
 * @Author TZY
 * @Date 2024/1/19 16:57
 **/
public class Client {

    //    Java为客户端提供了Socket类，为服务器端提供了ServerSocket类
    public static void main(String[] args) throws IOException {


        Socket socket = new Socket("127.0.0.1", 10000);
        OutputStream os = socket.getOutputStream();
        os.write("hello".getBytes());
        os.close();
        socket.close();


    }
}
