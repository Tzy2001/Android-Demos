package com.example.test.contentProvider

import android.content.UriMatcher
import kotlin.reflect.KProperty


fun <T> later(block: () -> T) = Later(block)

class Later<T>(val block: () -> T) {
    var value: Any? = null

    //    第一个参数代表委托属性可以在哪些类中使用，第二个参数表示kotlin中的一个属性操作类，这里必须要写，暂时用不到
//    operator表示声明一些特殊的函数,这些函数具有与某个操作符函数相关的含义
    operator fun getValue(any: Any?, prop: KProperty<*>): T {
        if (value == null) {
            value = block()
        }
        return value as T
    }
}