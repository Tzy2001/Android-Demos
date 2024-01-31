package com.example.test.kotlinPratice.base

fun main() {
    val child = 5
    val adult = 28
    val senior = 87
    val isMonday = true
    println("The movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}")
    println("The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}")
}

fun ticketPrice(age: Int, isMonday: Boolean): Int {
    val price: Int
    when (age) {
        in 0..12 -> price = 15
        in 13..60 -> if (isMonday) price = 25 else price = 30
        in 61..100 -> price = 20
        else -> price = -1
    }
    return price
}