package com.example.fragmentpractice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragmentpractice.databinding.ActivityNewsContentBinding
import com.example.fragmentpractice.fragment.NewsContentFragment


class NewsContentActivity : AppCompatActivity() {
//private val inflate by lazy { ActivityNewsContentBinding.inflate(layoutInflater) }
    companion object {
        fun actionStart(context: Context, title: String, content: String) {
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra("news_title", title);
                putExtra("news_content", content)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
//        setContentView(inflate.root)
        val title = intent.getStringExtra("news_title") // 获取传入的新 闻标题
        val content = intent.getStringExtra("news_content") // 获取传入 的新闻内容
        if (title != null && content != null) {
//            val fragment = newsContentFrag as NewsContentFragment
//            val fragment = inflate.newsContentFrag as NewsContentFragment
//            fragment.refresh(title, content) //刷新NewsContentFragment界面
        }
    }

}