package com.example.test.networkDemo

import android.annotation.SuppressLint
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

@SuppressLint("SuspiciousIndentation")
private fun sendRequestWithHttpUrlConnection() {
    thread {
        var connection: HttpURLConnection? = null
        try {
            val response=StringBuilder()
            val url=URL("https://www.baidu.com")
            connection=url.openConnection() as HttpURLConnection
            connection.connectTimeout=8000
            connection.readTimeout=8000
            val input=connection.inputStream

//            对获取到的输入流进行读取
        val reader=BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine { response.append(it) }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            connection?.disconnect()
        }
    }
}
