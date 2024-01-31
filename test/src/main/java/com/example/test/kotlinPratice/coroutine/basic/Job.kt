package com.example.test.kotlinPratice.coroutine.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//suspend fun main(){
//    coroutineScope {
//        val job1=launch {
//            println("Job 1 started")
//            val job2=launch {
//                println("Job 2 started")
//                delay(2000)
//                println("Job 2 finished")
//            }
//            delay(2000)
//            println("Job 1 completed")
//        }
//        Thread.sleep(1000)
//        job1.cancel()
//        println("Continue execution")
//        Thread.sleep(3000)
//    }
//}

fun main() {
    runBlocking {
        coroutineScope {
            val coroutine = launch {
                delay(1000)
                println("coroutine is started")
            }
        }
    }
    Thread.sleep(5000)
    println("132")
    println("256")
}