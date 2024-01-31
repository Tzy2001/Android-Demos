package com.example.androidthreadtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Button
import android.widget.TextView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var changeTextBtn:Button
    lateinit var textview:TextView
    val updateText=1
    val handler=object :Handler(){
        override fun handleMessage(msg: Message) {
            //在這裡可以進行UI操作
            when(msg.what){
                updateText->textview.text="Nice to meet you"
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeTextBtn=findViewById(R.id.changeTextBtn)
        textview=findViewById(R.id.textview)
        changeTextBtn?.setOnClickListener {
            thread {
                val msg=Message()
                msg.what=updateText
                handler.sendMessage(msg)//將Message對象放進去
            }
        }
    }
}