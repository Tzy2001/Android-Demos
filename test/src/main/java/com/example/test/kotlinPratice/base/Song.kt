package com.example.test.kotlinPratice.base

data class Song(val name: String, val person: String, val year: String, val count: Int) {
//    fun isPopular(): Boolean {
//        return this.count >= 1000
//    }

    val isPopular: Boolean
        get() = count >= 1000

    fun printSong() {
        println("[$name],performed by $person,was released in [$year]")
    }
}

fun main() {
    val song = Song("shuixingji", "guoding", "2022.12.23", 1200)
    println(song.isPopular)
    song.printSong()
}