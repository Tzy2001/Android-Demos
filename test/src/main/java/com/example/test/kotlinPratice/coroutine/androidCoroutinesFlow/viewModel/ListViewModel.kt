package com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.model.NewsRepository

class ListViewModel : ViewModel() {
    val newSArticles = NewsRepository().getNewsArticles().asLiveData()
}