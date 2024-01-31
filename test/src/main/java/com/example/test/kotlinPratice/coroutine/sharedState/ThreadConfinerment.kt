package com.example.test.kotlinPratice.coroutine.sharedState

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
        val counterContext = newSingleThreadContext("CounterContext")
        var counter = 0
        withContext(counterContext) {
            Util.massiveRunAtomic { counter++ }
        }
        println("Counter :$counter")
    }
}