package com.example.androidthreadtest

import android.app.Notification
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
import java.nio.file.attribute.AclEntry.Builder
import kotlin.concurrent.thread

class MyService : Service() {
    private val mBinder=DownloadBinder()
    class DownloadBinder: Binder(){
        fun startDownload(){
            Log.d("MyService","startDownload executed")
        }
        fun getProgress():Int{
            Log.d("MyService","getProgress executed")
            return  0
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyService","onCreate executed")
        val manager=getSystemService(Context.NOTIFICATION_SERVICE)as
                NotificationManager
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.0){
            val channel=NotificationChannel("my_service","前台Service通知",
            NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent =Intent(this,MainActivity2::class.java)
        val pi=PendingIntent.getActivity(this,0,intent,0)
        val notification=NotificationCompat.Builder(this,"my_service")
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setLargeIcon(BitmapFactory.decodeResource(R.drawable.large_icon))
            .setSmallIcon(R.drawable.small_icon)
            .setContentIntent(pi)
            .build()
        startForeground(1,notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService","onStartCommand executed")
        thread {
            //處理具體的邏輯
            stopSelf()//完成之後自動停止
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService","onDestroy executed")
    }
}