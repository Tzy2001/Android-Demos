package com.example.notification

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notification.databinding.ActivityMainBinding
import com.example.notification.databinding.ActivityNotificationBinding

class NotificatonActivity :AppCompatActivity(){
    private val inflate by lazy { ActivityNotificationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
    }
}