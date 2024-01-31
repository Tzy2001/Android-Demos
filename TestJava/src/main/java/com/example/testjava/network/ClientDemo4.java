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
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ClinetDemo4
 * @Author TZY
 * @Date 2024/1/20 10:54
 **/
public class ClientDemo4 {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8080);

        //读取本地文件中的数据，并写到服务器当中
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("mysocketnet\\clientdir\\a.jpg"));

        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        int len;
        byte[] bys = new byte[1024];
        while ((len = bis.read(bys)) != -1) {
            bos.write(bys, 0, len);
        }
        socket.shutdownOutput();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
        bos.close();
        bis.close();
        socket.close();

    }


    class ServerDemo4 {
        public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(8080);


            while (true) {
                Socket socket = ss.accept();
//多线程
//                new Thread(new MyRunnable(socket)).start();

                //綫程池
                ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 16, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
                pool.submit(new MyRunnable(socket));
            }

        }
    }
}

class MyRunnable implements Runnable {
    Socket socket;

    public MyRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //读取数据并保存到本地文件中
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

            String name = UUID.randomUUID().toString().replace("-", "");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("mysocketnet\\serverdir\\" + name + ".jpg"));


            int len;
            byte[] bys = new byte[1024];
            while ((len = bis.read(bys)) != -1) {
                bos.write(bys, 0, len);
            }
            bos.close();

            //回写数据
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("ok了客户端");
            bw.newLine();
            bw.flush();
            bw.close();
            bos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
