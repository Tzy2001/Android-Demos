package com.example.test.kotlinPratice.coroutine.sharedState

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    runBlocking {
        var counter = 0
        withContext(Dispatchers.Default) {
            Util.massiveRunAtomic { counter++ }
        }
        println("Counter :$counter")
    }
}