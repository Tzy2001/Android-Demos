package com.example.test.HandlerActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.R;

/**
 * @ClassName HandlerActivity3
 * @Author TZY
 * @Date 2023/10/10 19:12
 **/
public class HandlerActivity3 extends Activity {
    private TextView mTVOriginal;
    private EditText mETInput;
    private Button mBtnModify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mTVOriginal = findViewById(R.id.original);
        mETInput = findViewById(R.id.etInput);
        mBtnModify = findViewById(R.id.btnModify);

        mBtnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new MyThread();
                thread.start();
            }
        });
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            String str = "***" + mETInput.getText().toString() + "***";
            Message msg = handler.obtainMessage();
            msg.obj = str;
            handler.sendMessage(msg);
            Log.d("子线程", "这里是发送消息的线程，发送的内容是：" + str + "  线程名是：" + Thread.currentThread().getName());
        }
    }

    Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String sttr = (String) msg.obj;
            mTVOriginal.setText(sttr);
            Log.d("主线程", "这里是更改UI的线程，接收到的内容是：" + sttr + "  线程名是：" + Thread.currentThread().getName());
        }
    };
}

