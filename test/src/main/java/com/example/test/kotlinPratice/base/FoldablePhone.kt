package com.example.test.kotlinPratice.base

class FoldablePhone(var isFoldOn: Boolean = false) : Phone() {
    fun flodOn() {
        isFoldOn = true
    }

    fun flodOff() {
        isFoldOn = false
    }

    override fun switchOn() {
        //這裡我寫的不好的一點是沒有根據邏輯設計，這個方法本來就是打開的方法，所以重寫的方法裡面的邏輯應該是過濾掉所有的false，像下面這樣
//        super.switchOn()
        if (!isFoldOn) {
            isScreenLightOn = true
        }
    }
//    fun checkPhoneFlod() {
//        val phoneFlod = if (isFoldOn) "flod and ${isScreenLightOn}" else "off and $isScreenLightOn"
//        println("The phone screen's light is $phoneFlod.")
//    }
}
fun main(){
    val foldPhone= FoldablePhone(false)
    foldPhone.flodOn()
    foldPhone.switchOn()
    foldPhone.checkPhoneScreenLight()
    foldPhone.flodOff()
    foldPhone.switchOn()
    foldPhone.checkPhoneScreenLight()
}