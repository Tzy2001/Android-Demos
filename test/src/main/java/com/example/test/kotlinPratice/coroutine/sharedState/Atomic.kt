package com.example.test.kotlinPratice.coroutine.sharedState

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        var counter = AtomicInteger(0)
        withContext(Dispatchers.Default) {
            Util.massiveRunAtomic { counter.incrementAndGet() }
        }
        println("Counter = $counter")
    }
}

