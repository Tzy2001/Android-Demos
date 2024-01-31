package com.example.test.HandlerActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName HandlerActivity
 * @Author TZY
 * @Date 2023/10/10 19:05
 **/
public class HandlerActivity1 extends Activity {
    private ImageView imagChange;
    //  定义切换的图片数组id
    int imgids[] = new int[]{
            R.drawable.apple_pic, R.drawable.banana_pic,
            R.drawable.watermelon_pic, R.drawable.pear_pic
    };
    int imgStart = 0;
    Handler handler = new Handler() {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x110) {
                imagChange.setImageResource(imgids[imgStart++ % 4]);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        imagChange = findViewById(R.id.img);

        //使用定时器,每隔200毫秒让handler发送一个空信息
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x110);
            }
        }, 0, 200);
    }
}
