package com.example.testjava.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName ClientDemo3
 * @Author TZY
 * @Date 2024/1/20 10:25
 **/
public class ClientDemo3 {
    //客户端：将本地文件上传到服务器。接收服务器的反馈。
    //服务器：接收客户端上传的文件，上传完毕之后给出反馈。
//BufferedOutputStream,BufferedReader(readLine,nextLine,)->InputStreamReader->InputStream,

    public static void main(String[] args) throws IOException {
        //创建Socket对象，并连接服务器
        Socket socket = new Socket("127.0.0.1", 8080);
        //讀取本地文件裡的數據，並寫到服務器當中
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("mysocketnet\\\\clientdir\\\\a.jpg"));

//寫給服務器
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        byte[] bys = new byte[1024];
        int len;
        while((len=bis.read(bys)) != -1){
            bos.write(bys, 0, len);
        }

        //往服務器寫出結束標記
        socket.shutdownOutput();

        //接收服務器的回寫數據
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while((br.readLine()) != null){
            System.out.println("服务器返回数据：" + br.readLine());
        }

        br.close();
        bos.close();
        bis.close();
        socket.close();
    }



}

class SocketDemo3 {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        Socket socket = ss.accept();

        //讀取數據並保存到本地文件中
        BufferedInputStream bis = new BufferedInputStream((socket.getInputStream()));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("mysocketnet\\\\clientdir\\\\a.jpg"));


        int len;
        byte[] bys = new byte[1024];
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        bos.close();
        //回写数据
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("服务器返回数据：");
        bw.newLine();
        bw.flush();
        bw.close();
        bos.close();
        bis.close();
        socket.close();
        ss.close();



    }
}
