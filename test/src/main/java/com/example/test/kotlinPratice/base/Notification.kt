package com.example.test.kotlinPratice.base


fun main() {
    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}

fun printNotificationSummary(numberOfMessages: Int) {
    //如果通知少於100顯示確切的數量，否則顯示99+
    //開區間

    if (numberOfMessages in 1 until 100) {
        println("You Have $numberOfMessages notifications")
    } else if (numberOfMessages >= 100) {
        println("Your phone is blowing up! You Have 99+ notifications")
    }
}