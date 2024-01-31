package com.example.test.kotlinPratice.coroutine.channel

import android.provider.Settings.Global
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val tickerChannel = ticker(100)
        launch {
            val startTime = System.currentTimeMillis()
            tickerChannel.consumeEach {
                val delta = System.currentTimeMillis() - startTime
                println("Received tick after $delta")
            }
        }
        delay(1000)
        println("Done! ")
        tickerChannel.cancel()
    }
//    runBlocking {
//        coroutineScope {
//            launch {
////            delay(800)
//                println("123")
//                println("dsad")
//                println("gdfg")
//                println("fgjgf")
//            }
//            println("546")
//        }
////        delay(1000)
//    }

}