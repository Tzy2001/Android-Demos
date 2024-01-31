package com.example.imagediscerndemo.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ClassName FileUtil
 * @Author TZY
 * @Date 2024/1/31 11:34
 **/
public class FileUtil {

    /**
     * 功能描述：读取文件内容，作为字符串返回
     *
     * @param filePath
     * @return {@link String }
     * @author Tzy
     * @date 2024/01/31 11:39
     */

    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("文件大小超过1G");
        }

        StringBuilder bu = new StringBuilder((int) (file.length()));
//創捷字節輸入流
        FileInputStream fis = new FileInputStream(filePath);
        //創建一個長度爲10240的Buffer
        byte[] bbuf = new byte[10240];
        //用於保存實際讀取的字節數
        int hasRead=0;
        while ((hasRead=fis.read())>0){
            bu.append(new String(bbuf,0,hasRead));
        }
        fis.close();
        return bu.toString();
    }

    public static byte[] readFileByBytes(String filePath) throws IOException{
        File file = new File(filePath);
        if (!file.exists()){
            throw new FileNotFoundException(filePath);
        }else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in=null;
            try{
                in=new BufferedInputStream(new FileInputStream(file));
                short bufSize=1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while((len1=in.read(buffer,0,bufSize))!=-1){
                    bos.write(buffer,0,len1);
                }
                byte[] var7 = bos.toByteArray();
                return var7;
            }finally {
                try{
                    if (in!=null){
                        in.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                bos.close();
            }
        }
    }
}
