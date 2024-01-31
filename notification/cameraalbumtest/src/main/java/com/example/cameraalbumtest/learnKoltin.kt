package com.example.cameraalbumtest

/*
fun main() {
    val n1=1.3
    val n2=2.4
    val n= compareNumber(n1,n2)
    println(n)
}
fun compareNumber(a:Double,b:Double):Double{
    return max(a,b)
}*/
/*
fun main(){
    var str:String ?="aaa"
    str=""
    str=str?.let {
        if (it.isNotBlank()){
            it.capitalize()
        }else{
            "1"
        }
    }
    println(str)
}*/
fun main() {
    val a = 49
    val b = 29
    val n = compareNumber(a, b)
    println("this value is:" + n)
    val person = getScore("Tomsda")
    println("Tomsda's score is $person")
    val num = checkNumber(3)
    println(num)
//    val Tom=Student("103321",6,"aaa",20)
    val stu = Student("Tom", 18)
    stu.doHomeWork()
    stu.readBooks()
    /*   for (i in 0 until 10){
           println(i)
       }*/
//    val range=0..10
//    val range=0 until 10 step 2
    val range = 10 downTo 0
    for (i in range)
        println(i)


    val list = listOf("Apple", "Bananna", "pear", "Grape")
    val l = mutableListOf("Apple", "Bananna", "pear", "Grape")
    l.add("dsad")
    for (i in l) {
        println(i)
    }

    val lsit = setOf("Apple", "Bananna", "pear", "Grape")
    for (i in lsit) {
        println(i)
    }

    val map = mapOf("Apple" to 1, "Bananna" to 2, "pear" to 3)
    for ((fruit, number) in map) {
        println("fruit is $fruit,number is $number")
    }
    val newList = list.filter { it.length <= 5 }
        .map { it.toUpperCase() }
    for (i in newList) {
        println(newList)
    }


}
fun compareNumber(num1: Int, num2: Int) = if (num1 > num2) num1 else num2

/*
fun compareNumber(value1:Int,value2:Int):Int{
//    return max(a,b)
var value:Int ?=null
    if (value1>value2){
        value=value1
    }else{
        value=value2
    }
    return value
}*/
/*
fun getScore(name: String):Double = if (name == "Tom") {
    93.2
} else if (name == "kali") {
    86.2
}else if (name=="jack"){
    96.3
}else{
    0.0
}*/

/*fun getScore(name :String) = when (name){
    "Tom" ->66
    "Lucy" ->76
    "Jack" ->86
    "Handy" ->96
    "Kay" ->60
    else ->0
}*/

fun getScore(name: String) = when {
    name.startsWith("Tom") -> 66
    name == "Lucy" -> 86
    name == "Carry" -> 76
    name == "Won" -> 83
    name == "Shary" -> 90
    else -> 0

}

fun checkNumber(num: Number) {
    when (num) {
        is Int -> println("$num is Int")
        is Double -> println("$num is Double")
        is Float -> println("$num is Float")
        is Long -> println("$num is Long")
    }
}
