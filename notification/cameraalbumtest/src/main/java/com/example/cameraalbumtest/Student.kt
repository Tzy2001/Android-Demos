package com.example.cameraalbumtest

/*class Student(val sno:String,val grade:Int,name:String,age:Int) :Person(name , age ){
init {
    println("sno is "+sno)
    println("grade is "+grade)
    println("age is "+age)
    println("name is "+name)
}

    constructor(name: String,age: Int):this("",0,name,age){}
    constructor():this("",0)
}*/

/*
class Student(val sno:String,val grade:Int):Person(){
 init {
     println("sno is "+sno)
     println("grade is "+grade)
     println("age is "+age)
     println("name is "+name)
 }
}*/
class Student (name:String,age:Int):Person(name,age),Study{
    override fun readBooks() {
    println("$name is reading...")
    }

    override fun doHomeWork() {
    println("$name is doHomeWord...")
    }
}