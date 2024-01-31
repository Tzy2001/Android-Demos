package com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.model

import retrofit2.http.GET

interface NewService {
    @GET("news.json")
    suspend fun getNews():List<NewsArticle>
}