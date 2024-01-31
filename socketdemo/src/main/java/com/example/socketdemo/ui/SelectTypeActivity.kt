package com.example.socketdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.socketdemo.R

class SelectTypeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        findViewById<Button>(R.id.btn_server).setOnClickListener {
            jumpActivity(ServerPlusActivity::class.java)
        }
        findViewById<Button>(R.id.btn_client).setOnClickListener {
            jumpActivity(ClientPlusActivity::class.java)
        }

    }
}