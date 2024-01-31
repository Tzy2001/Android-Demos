package com.example.test.kotlinPratice.fanixng

import kotlin.math.exp

class Box<T>(t: T) {
    val value: T = t
        get() = field
}

fun <T> printItem(item: T) {
    println(item)
}

fun main() {
    //两者是相同的，如果能推断出来用第二种
//    val box1=Box<Int>(1)
//    val box2=Box(1)
    //使用汎型類
    val intBox = Box(42)
    val stringBox = Box("Hello Kotlin!")
    println(intBox.value)
    println(stringBox.value)
    //使用泛型函数
    printItem(56)
    printItem("your name")
//泛型约束
    val catOwner = PetOwner(Cat())
    catOwner.playWithPet()
    val dogOwner = PetOwner(Dog())
    dogOwner.playWithPet()

    val jack = User("Jack", 25)
    val (name, age) = jack
    println("$name,$age years of age")

    val test=NotANumber
    val expr=Const(2.0)
    val e1=Const(3.0)
    val e2=Const(4.0)
    val sum=Sum(e1,e2)
    println(eval(expr))
    println(eval(sum))
    println(test)
}

interface Pet {
    fun play()
}

class Cat : Pet {
    override fun play() {
        println("Cat is Playing")
    }
}

class Dog : Pet {
    override fun play() {
        println("Dog  is Playing")
    }
}

class PetOwner<T : Pet>(val pet: T) {
    fun playWithPet() {
        pet.play()
    }
}
//解构
data class User(val name: String, val age: Int)

//密封类
sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
}