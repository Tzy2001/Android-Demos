package com.example.test.networkDemo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://192.168.8.148:8081"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    fun <T> create(serviceClass: Class<T>): T = retrofit.create((serviceClass))
     private inline fun <reified T> create(): T = retrofit.create(T::class.java)
}