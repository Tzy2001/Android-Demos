package com.example.test.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.test.R

class MyService : Service() {

    private val mBinder = DownloadBinder()

    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d("MyService", "startDownload  ")
        }

        fun getProgress(): Int {
            Log.d("MyService", "getProgress ")
            return 0
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    //創建前台service
    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreated ")
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "my_service", "前台Service通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, ThreadTest::class.java)
        val pi= PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE)
        val notification=NotificationCompat.Builder(this,"my_service")
            .setContentTitle("this is content title")
            .setContentText("this is content text")
            .setSmallIcon(R.drawable.apple_pic)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.apple_pic))
            .setContentIntent(pi)
            .build()
        startForeground(1,notification)

    }

    //每次啟動service時調用
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy ")
    }
}