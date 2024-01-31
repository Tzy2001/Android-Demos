package com.example.androidthreadtest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    lateinit var downloadBinder:MyService.DownloadBinder
    private val connection=object :ServiceConnection{
        override fun onServiceConnected(p0: ComponentName, p1: IBinder) {
            downloadBinder=p1 as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        val btn1: Button = findViewById(R.id.button1)
        val btn2: Button = findViewById(R.id.button2)
        val btn3: Button = findViewById(R.id.bindServiceBtn)
        val btn4: Button = findViewById(R.id.unbindServiceBtn)
        val btn5: Button = findViewById(R.id.startIntentService)
        btn1.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }
        btn2.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            stopService(intent)
        }
        btn3.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
        }
        btn4.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            unbindService(connection)//解綁Service
        }
        btn5.setOnClickListener {
            //打印主線程的id
            Log.d("MainActivity","Thread id is ${Thread.currentThread().name}")
            val intent=Intent(this,MyIntentService::class.java)
            startService(intent)
            startActivity<MainActivity>(this){
                putExtra("param1","data")
                putExtra("param2","123")
            }
        }
    }
}