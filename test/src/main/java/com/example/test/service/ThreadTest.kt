package com.example.test.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.test.R
import com.example.test.databinding.ActivityThreadTestBinding
import kotlin.concurrent.thread

class ThreadTest : AppCompatActivity() {

    lateinit var downloadBinder: MyService.DownloadBinder
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }


    val updateText = 1

    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
//        進行UI操作
            when (msg.what) {
                updateText -> binding.tvTextview.text = "Nice to meet you"
            }
        }

    }

    private val binding by lazy { ActivityThreadTestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.startServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)//啟動service
        }
        binding.stopServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)//停止service
        }


        binding.bindServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)//綁定Service
        }
        binding.unbindServiceBtn.setOnClickListener {
            unbindService(connection)//解綁Service
        }

        binding.startIntentServiceBtn.setOnClickListener {
            Log.d("ThreadTest", "Thread id is ${Thread.currentThread().name}")
            val intent = Intent(this, MyIntentService::class.java)
            startService(intent)
        }
        binding.changeTextBtn.setOnClickListener {
//            不能在主線程裡面征信UI
//            thread { binding.tvTextview.text = "Nice to meet you" }

            thread {
                val msg = Message()
                msg.what = updateText
//            將message對象放進去
                handler.sendMessage(msg)
            }
        }


    }
}