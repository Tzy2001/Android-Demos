package com.example.test.kotlinPratice.coroutine.basic

import kotlinx.coroutines.*
import kotlin.random.Random


suspend fun main() {
    coroutineScope {
        val firstDeferred=async {
            println("The first result")
            getValue(1000)
        }
        val secondDeferred=async {
            println("The Second result")
            getValue(2000)
        }
        println("Doing some processing")
        delay(500)
        println(firstDeferred.await())
        println(secondDeferred.await())
        println("Finished processing")
    }
    withContext(Dispatchers.IO){
        Thread.sleep(4000)
    }

}

suspend fun getValue(delay: Long): Int {
    delay(delay)
    return Random.nextInt()
}


