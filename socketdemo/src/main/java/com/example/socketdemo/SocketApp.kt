package com.example.socketdemo

import android.app.Application
import android.util.Log
import androidx.emoji2.bundled.BundledEmojiCompatConfig
import androidx.emoji2.text.EmojiCompat
import androidx.emoji2.text.EmojiCompat.InitCallback
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.properties.Delegates

class SocketApp : Application() {
    private val TAG = SocketApp::class.java.simpleName

    val emojiList = arrayListOf<CharSequence>()

    companion object {
        private var instance: SocketApp by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initEmoji2()

    }

    private fun initEmoji2() =
        EmojiCompat.init(BundledEmojiCompatConfig(this).apply {
            setReplaceAll(true)
            registerInitCallback(object : InitCallback() {
                override fun onInitialized() {
                    //初始化成功回調
                    Log.d(TAG, "onInitialized")
                    //加載表情列表
                    loadEmoji()
                }

                override fun onFailed(throwable: Throwable?) {
                    //初始化失敗回調
                    Log.d(TAG, throwable.toString())
                }
            })
        })

    private fun loadEmoji() {
        val inputStream = assets.open("emoji.txt")
        BufferedReader(InputStreamReader(inputStream)).use {
            var line: String
            while (true) {
                line = it.readLine() ?: break
                emojiList.add(line)
            }
        }
    }

}