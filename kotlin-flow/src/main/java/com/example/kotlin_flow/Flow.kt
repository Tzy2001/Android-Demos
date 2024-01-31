package com.example.kotlin_flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

fun main() {
//    runBlocking {
//        val flow = flowOf(1, 2, 3, 4, 5)
//        flow.filter { it % 2 == 0 }
//            .onEach { println(it) }
//            .map {
//            it * it
//        }.collect {
//
//            }
////            .collect {
////            println(it)
////        }
//    }
//    runBlocking {
//        flow {
//            emit(1)
//            emit(2)
//            kotlinx.coroutines.delay(600)
//            emit(3)
//            kotlinx.coroutines.delay(100)
//            emit(4)
//            kotlinx.coroutines.delay(100)
//            emit(5)
//        }.debounce(500)//类似于浏览器搜索框，输入内容时给予提示，每间隔一段时间提示一次，而不是实时提示
//            .collect {
//                println(it)
//            }
//    }

//    runBlocking {
//        flow {
//            while (true) {
//                emit("发送一条弹幕")
//            }
//        }.sample(1000)//类似于发送弹幕，每隔一段时间采一次样
//            .flowOn(Dispatchers.IO)
//            .collect {
//                println(it)
//            }
//    }

//    runBlocking {
//        val result = flow {
//            for (i in 1..100) {
//                emit(i)
//            }
//        }.reduce { acc, value -> acc + value }//类似与高斯老师计算1++..100的和，这里acc表示累加value表示当前值，注意reduce是一个终端操作符，会结束当前的flow
//        println(result)
//    }

//    runBlocking {
//        val result = flow {
//            for (i in 'A'..'Z') {
//                emit(i.toString())
//            }
//        }.fold("Alphabet: ") { acc, value -> acc + value }//和reduce類似也是終端操作符，不過不同的是他需要提供一個初始值
//        println(result)
//    }

    //flatMap系列，用于嵌套调用，链式调用
//    runBlocking {
//        flowOf(1, 2, 3)
//            .flatMapConcat { //不会改变顺序，保持顺序,调用完第一个flow戒指使用第二个flow
//                flowOf("a$it", "b$it")
//            }
//            .collect {
//                println(it)
//            }
//    }

//    runBlocking {
//        sendGetTokenRequest()
//            .flatMapConcat { token ->
//                sendGetUserInfoRequest(token)
//            }
//            .flowOn(Dispatchers.IO)
//            .collect { userInfo ->
//                println(userInfo)
//            }
//    }

    //flatMapMerge不會保證順序執行，他是可以並發處理數據的，那條數據的時間更短，他就優先處理那一個
//    runBlocking {
//        flowOf(300, 200, 100)
//            .flatMapMerge {//这里如果使用flatMapConcat，那么将会按照顺序执行，300-200-100，这里会先执行时间更短的
//                flow {
//                    kotlinx.coroutines.delay(3000)
//                    emit("a$it")
//                    emit("b$it")
//                }
//            }
//            .collect { s ->
//                println(s)
//            }
//    }


//    runBlocking {
//        flow {
//            emit(1)
//            kotlinx.coroutines.delay(150)
//            emit(2)
//            kotlinx.coroutines.delay(50)
//            emit(3)
//        }.flatMapLatest { //類似於collectLatest,如果有最新的數據，但是上一個數據還在處理中時，放棄現在的處理最新的
//            flow {
//                delay(100)
//                emit("$it")
//            }
//        }.collect {
//            println(it)
//        }
//    }


    //zip，兩個或兩個以上
    //和flatMap有點類似，不過他是並行的，而flatMap的運行方式是一個flow中的數據流向列外一個flow，是串行的關係
    //天氣預報，如果同時展示今天和未來七天的天氣，就可以使用這個
//    runBlocking {
//        val flow1 = flowOf("a", "b", "c")
//        val flow2 = flowOf(1, 2, 3, 4, 5)
//        flow1.zip(flow2) { a, b ->
//            a + b
//        }
//            .collect {
//                println(it)//打印a1,b2,c3
//            }
//    }


//buffer,collectLatest,conflate背壓三劍客，應用於數據的發送和處理速度不均匀的問題
//    runBlocking {
//        flow {
//            emit(1)
//            delay(1000)
//            emit(2)
//            delay(1000)
//        }
//            .onEach { println("$it is ready") }
//            .buffer()
//            .collect {
//                delay(1000)//collect裏面的會影響到flow裏的（如果collect沒有處理完，flow裏的會被挂起），使用buffer之後，數據的發送的處理互不干擾
//                println("$it is done")
//            }
//    }

    runBlocking {
        flow {
            var count = 0
            while (true) {
                emit(count)
                delay(1000)
                count++
            }
        }
            .conflate()//他會强制吧正在處理的數據執行萬，再去處理最新的數據有別於collectLatest
            .collect{
                println("start handle $it")
                delay(2000)
                println("finish handle $it")
            }
    }


}