package com.example.webview

data class AgeContainer(var age: Int)

fun geta(name: String, ageAction: (AgeContainer) -> Unit) {
    val ageContainer = AgeContainer(0)
    ageAction(ageContainer)
    println("Updated age: ${ageContainer.age}")
}

fun main() {
    geta("John") { ageContainer ->
        ageContainer.age = 3
    }
}
