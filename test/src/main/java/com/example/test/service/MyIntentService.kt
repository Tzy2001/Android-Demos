package com.example.test.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService:IntentService("MyIntentService") {
    override fun onHandleIntent(intent: Intent?) {
//        進行一些耗時邏輯
//        打印線程id
    Log.d("MyIntentService","Thread id is ${Thread.currentThread().name}")
    }
//會默認開啟子線程並且自動銷毀
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService","onDestroy")
    }

}