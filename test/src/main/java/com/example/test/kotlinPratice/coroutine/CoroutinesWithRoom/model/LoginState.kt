package com.example.test.kotlinPratice.coroutine.CoroutinesWithRoom.model

object LoginState {
    var isLoggedIn = false
    var user: User? = null
    fun logout() {
        isLoggedIn = false
        user = null
    }

    fun logIn(user: User) {
        isLoggedIn = true
        this.user = user
    }

}