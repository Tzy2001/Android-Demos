package com.example.test.kotlinPratice.operator

class Money(val value: Int) {
    operator fun plus(money: Money): Money {
        val sum = value + money.value
        return Money(sum)
    }
//可以重载多个
    operator fun plus(newValue: Int): Money {
        val sum = value + newValue
        return Money(sum)
    }
}

fun main() {
    val money1 = Money(2)
    val money2 = Money(12)
    val money3 = money1 + money2
    val money4 = money1 + 20
    println(money4.value)
}