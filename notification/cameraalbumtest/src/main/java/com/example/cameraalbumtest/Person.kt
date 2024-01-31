package com.example.cameraalbumtest

open class Person(val name:String ,val age:Int) {

    fun eat() {
        println(name + "is eating.He is " + age + "years old.")
    }
}
/*
open class Person() {
    var name = ""
    var age = 0
    fun eat() {
        println(name + "is eating.He is " + age + "years old.")
    }
}*/
