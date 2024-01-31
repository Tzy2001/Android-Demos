package com.example.androidthreadtest

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService:IntentService("MyIntentService") {
    override fun onHandleIntent(p0: Intent?) {
        //打印當前線程的id
        Log.d("MyIntentService","Thread id is ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService","onDestroy executed")
    }
}