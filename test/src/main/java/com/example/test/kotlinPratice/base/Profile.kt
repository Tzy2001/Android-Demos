package com.example.test.kotlinPratice.base

fun main() {
    val amanda = Person("Amanda", 33, "swim", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)
    amanda.showProfile()
    atiqah.showProfile()
}

class Person(
    val name: String,
    val age: Int,
    val hobby: String?,
    val referrer: Person?
) {
    fun showProfile() {
        println("Name: " + name)
        println("Age: " + age)
        var referrerInfo: String = ""
        referrer?.let {
            var referrerHobby=""
            if (!referrer.hobby.isNullOrEmpty()){
                referrerHobby=", who likes to ${referrer.hobby}."
            }else{
                "."
            }
            referrerInfo = "Has a referrer named ${referrer.name} $referrerHobby"
        } ?: run { referrerInfo = "Doesn't have a referrer." }
        if (!hobby.isNullOrEmpty()){
            println("Likes to ${hobby}. $referrerInfo")
        }else{
            println(referrerInfo)
        }
    }
}