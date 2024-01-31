package com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.databinding.ActivityMain9Binding
import com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.viewModel.ListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private val newsListAdapter = NewsListAdapter()
    private val binding by lazy { ActivityMain9Binding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        binding.newsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsListAdapter
        }
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.newSArticles.observe(this, Observer { article ->
            binding.loadingView.visibility = View.GONE
            binding.newsList.visibility = View.VISIBLE
            newsListAdapter.onAddNewsItem(article)
            binding.newsList.smoothScrollToPosition(0)
        })
    }
}