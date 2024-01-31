package com.example.fragmentpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SecondActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_second)
        val extradata=intent.getStringExtra("extra_data")
        Log.d("SecondActivity","$extradata")
    }
}