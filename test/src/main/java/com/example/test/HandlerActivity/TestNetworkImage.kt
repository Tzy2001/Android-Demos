package com.example.test.HandlerActivity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.databinding.ActivityNetworkImageBinding

class TestNetworkImage:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_image)
        val btn=findViewById<Button>(R.id.btn_image)
        val myImageView=findViewById<NetworkImage>(R.id.iv_restaurant_logo)
        btn.setOnClickListener {
            myImageView.setImageURL("https://pic.cnblogs.com/avatar/1142647/20170416093225.png")
        }
    }
}