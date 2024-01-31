package com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/DevTides/NewsApi/master/"
        private const val NEWS_DELAY = 2000L
    }
private val newsService=Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(NewService::class.java)

    fun getNewsArticles(): Flow<NewsArticle>{
        return flow{
            var newSource =newsService.getNews()
            newSource.forEach {
                emit(it)
                delay(NEWS_DELAY)
            }
        }
    }
}