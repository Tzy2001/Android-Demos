package com.example.test.kotlinPratice.coroutine.sharedState

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

object Util {

    suspend fun massiveRunAtomic(action: suspend () -> Unit) {
        val n = 100
        val k = 1000
        val time = measureTimeMillis {
            coroutineScope {
                repeat(n) {
                    launch {
                        repeat(k) {
                            action()
                        }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }
}