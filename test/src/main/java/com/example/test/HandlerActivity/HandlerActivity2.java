package com.example.test.HandlerActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HandlerActivity2
 * @Author TZY
 * @Date 2023/10/10 19:10
 **/
public class HandlerActivity2 extends Activity {
    static final String UPPER_NUM = "upper";
    EditText mEtNum;
    CalThread calThread;

    // 定义一个线程类
    class CalThread extends Thread {
        public Handler mHandler;

        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @SuppressLint("HandlerLeak")
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x120) {
                        int upper = msg.getData().getInt(UPPER_NUM);
                        List<Integer> nums = new ArrayList<Integer>();
                        // 计算从2开始、到upper的所有质数
                        outer:
                        for (int i = 2; i <= upper; i++) {
                            // 用i处于从2开始、到i的平方根的所有数
                            for (int j = 2; j <= Math.sqrt(i); j++) {
                                // 如果可以整除，表明这个数不是质数
                                if (i != 2 && i % j == 0) {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                            // Toast.makeText(HandleActivity.this, "质数分别为：" + nums.toString(), Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Handler.class", "质数分别为：" + nums.toString());
                    }
                }
            };
            Looper.loop();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headle);
        mEtNum = findViewById(R.id.etNum);
        calThread = new CalThread();
        // 启动新线程
        calThread.start();
    }

    // 为按钮的点击事件提供事件处理函数
    public void cal(View view) {
        //  创建消息
        Message msg = new Message();
        msg.what = 0x120;
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUM, Integer.parseInt(mEtNum.getText().toString()));
        msg.setData(bundle);
        // 向新线程中的Handler发送消息
        calThread.mHandler.sendMessage(msg);
    }
}

