package com.example.test.kotlinPratice.coroutine.basic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//fun main() {
//    val start = System.currentTimeMillis()
//    //一般只在测试环境使用能够保证里面的代码全部执行完，在正式环境用会有性能问题，因为他是阻塞线程的
//    runBlocking {
////        println("codes run in coroutine scope")
////        delay(1500)
////        println("codes run in coroutine scope finished")
//
//        repeat(100000) {
//            launch {
//                println(".")
//            }
//        }
//
//
////        launch{
////            println("launch1")
////            delay(1000)
////            println("launch1 finished")
////        }
////        launch{
////            println("launch2")
////            delay(1000)
////            println("launch2 finished")
////        }
//    }
//    val end = System.currentTimeMillis()
//    println(end - start)
//
//
////    GlobalScope.launch {
////        println("codes run in coroutine scope")
////        delay(1500L)
////        println("codes run in coroutine scope is Finished")
////    }
////    Thread.sleep(1000L)
//}


fun main() {
//    runBlocking {
//        coroutineScope {
//            launch {
//                for (i in 1..10) {
//                    println(i)
//                    delay(1000)
//                }
//            }
//        }
//        println("coroutineScope finished")
//    }
//    println("runBlocking finished")
    runBlocking {
        //想要獲取協程的結果，需要使用async函數，他會創建一個新的Deferred對象，如果需要獲取結果，只需調用await方法即可
//        val result = async {
//            5 + 5
//        }.await()
//        println(result)
        val start = System.currentTimeMillis()
        val deferred1 = async {
            2 + 6
        }
        val deferred2 = async {
            3 + 6
        }
        //這段是并行，如果在每個defgerred後面都await，那麽就會很卡，是串行
        println(deferred1.await() + deferred2.await())
        val end = System.currentTimeMillis()
        println("${(end - start) / 1000.0}s")
    }
    runBlocking {
        val result = withContext(Dispatchers.Default) {
            5 + 5
        }
        println(result)
    }
}

//结合coroutineScope将部分代码提取到一个单独的函数中，coroutineScope也是一个挂起函数，他会继承外部的协程的作用域创建一个子协程（与runBlocking类似确保代码的完全执行）。
//与runBlocking不同的是，runBlocking是阻塞线程，而coroutineScope是挂起函数，他会阻塞协程。
suspend fun printDot() = coroutineScope {
    launch {
        println(".")
        delay(1000)
    }
}

//不推荐这个，因为他要逐个取消
//val job = GlobalScope.launch {
//    //处理具体的逻辑
//}
//job.cancel()

//推荐这种，coroutineScope函数会返回一个coroutineScope实例,随时随地调用，一次性全部取消
//val job = job()
//val scope = CoroutineScope(job)
//scope.launch{
//    //处理具体的逻辑
//}
//scope.launch {
//    //处理具体的逻辑
//}
//job.cancel()