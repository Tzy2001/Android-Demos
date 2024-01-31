package com.example.test

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.net.HttpURLConnection
import java.net.URL

class RestaurantLogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_restaurant_logo)
        val imageUrl = intent.getStringExtra("data")
        val imageView = findViewById<ImageView>(R.id.iv_restaurant_logo)
        val btnImage = findViewById<Button>(R.id.btn_image)
        btnImage.setOnClickListener {
            Glide.with(imageView.context).asBitmap().load(imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageView.setImageBitmap(resource)
                        imageView.layoutParams.apply {
                            width = 300
                            height = 200
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val bitmap = loadBitmapFromUrl(imageUrl)
//
//            launch(Dispatchers.Main) {
//                if (bitmap != null) {
//                    imageView.setImageBitmap(bitmap)
//                } else {
//                    // 图片加载失败的处理
//                }
//            }
//        }
    }


//    private suspend fun loadBitmapFromUrl(url: String): Bitmap? {
//        try {
//            val imageUrl = URL(url)
//            val connection = imageUrl.openConnection() as HttpURLConnection
//            connection.connect()
//            val inputStream = connection.inputStream
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            inputStream.close()
//            return bitmap
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return null
//        }
//    }
}
