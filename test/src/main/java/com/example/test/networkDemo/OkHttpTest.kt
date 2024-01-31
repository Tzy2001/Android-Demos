package com.example.test.networkDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.R
import com.example.test.databinding.ActivityOkHttpTestBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpTest : AppCompatActivity() {
    private val binding by lazy { ActivityOkHttpTestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        Get
//        创建OkHttpClient的实例
        val client= OkHttpClient()
//        创建Request对象(发送http请求必须创建Request的对象)
//        val request = Request.Builder().build()//空对象，没有任何意义
        val request =Request.Builder()
            .url("https://www.jd.com")
            .build()

//        使用newCall方法创建一个Call对象，调用它的execute方法发送请求并获取服务器数据

        val response = client.newCall(request).execute()
//将数据展示出来
        val responseData = response.body?.string()
//

//        Post
        val client1 = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("username", "admin")
            .add("password", "123456")
            .build()
        val request1 = Request.Builder()
            .url("https://www.biadu/com")
            .post(requestBody)
            .build()
        val response1 = client.newCall(request).execute()
        val responseData1 = response.body?.string()
    }
}