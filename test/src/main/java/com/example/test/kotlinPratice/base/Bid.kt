package com.example.test.kotlinPratice.base

fun main() {
    val winningBid = Bid(5000, "Private Collector")
    println("Item A is sold at ${auctionPrice(winningBid, 2000)}.")
    println("Item B is sold at ${auctionPrice(null, 3000)}.")
}

class Bid(val amount: Int, val bidder: String)

//fun auctionPrice(bid: Bid?, minimumPrice: Int): Int {
//    var lastPrice: Int = 0
//    bid?.let {
//        lastPrice = it.amount
//    } ?: run { lastPrice = minimumPrice }
//    return lastPrice
//}

//可以使用更簡單的返回
fun auctionPrice(bid: Bid?, minimumPrice: Int): Int {
    return bid?.amount ?: minimumPrice
}