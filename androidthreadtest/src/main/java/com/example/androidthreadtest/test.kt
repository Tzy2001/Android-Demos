package com.example.androidthreadtest

class test {
    fun main(){
        val result1=getGenericType<String>()
        val result2=getGenericType<Int>()
        println("result is $result1")
        println("result is $result2")
    }
    inline fun <reified  T> getGenericType(){

    }
}