package com.example.testjava.kotlinPratice

import java.sql.DriverManager.println

fun main() {
    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}

fun printNotificationSummary(numberOfMessages: Int) {
        //如果通知少於100顯示確切的數量，否則顯示99+
    if(numberOfMessages<100){
        println("You Have $numberOfMessages notifications")
    }else{
        println("Your phone is blowing up! You Have 99+ notifications")
    }
}