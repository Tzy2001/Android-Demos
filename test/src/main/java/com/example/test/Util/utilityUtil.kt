package com.example.test.Util

import android.content.ContentValues
import android.content.SharedPreferences


//高階函數  擴展SharedPreferences的open函數，函數內部擁有擴展SharedPreferences的open函數的上下文，可以直接調用edit方法，然後block是具體的put，最終apply
fun SharedPreferences.open(block: SharedPreferences.Editor.() ->Unit){
    val editor=edit()
    editor.block()
    editor.apply()
}

//可變參數 vararg ContentValues的key始終是String，value千變萬化可以是null
fun cvOf(vararg pairs:Pair<String ,Any?>):ContentValues{
    val cv=ContentValues()
    for (pair in pairs) {
        val key =pair.first
        val value =pair.second
        when(value){
            is Int ->cv.put(key,value)
            is Long ->cv.put(key,value)
            is Short ->cv.put(key,value)
            is Float ->cv.put(key,value)
            is Double ->cv.put(key,value)
            is Boolean ->cv.put(key,value)
            is String ->cv.put(key,value)
            is Byte ->cv.put(key,value)
            is ByteArray ->cv.put(key,value)
            null ->cv.putNull(key)
        }
    }
    return cv
}