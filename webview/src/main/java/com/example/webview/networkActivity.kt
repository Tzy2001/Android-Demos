package com.example.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.webview.databinding.ActivityNetworkBinding
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class networkActivity : AppCompatActivity() {
    private val binding by lazy { ActivityNetworkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.sendRequestBtn.setOnClickListener {
            sendRequestWithHttpURLConnection()
        }
    }

    private fun sendRequestWithHttpURLConnection() {
//        開啟線程發起網絡請求
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
//            connection.requestMethod="POST"
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
//            val output=DataOutputStream(connection.outputStream)
//            output.writeBytes("username=admin&password=123456")
//            下面對獲取到的輸入流進行讀取
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponese(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun showResponese(reponse: String) {
        runOnUiThread {
//        在這裡進行UI操作，將結果顯示到界面上
            binding.responseText.text = reponse
        }
    }
}