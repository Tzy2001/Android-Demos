package com.example.kotlin_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class MainViewModel : ViewModel() {

//    stateFlow，liveData粘性，不會重建，仍然保持（如果觀察者沒有及時接收到這些數據，是否需要繼續收到該數據）
//    sharedFlow非粘性


    private val _loginFlow=MutableSharedFlow<String>()
    val loginFlow=_loginFlow.asSharedFlow()//這樣儅反轉時，不會重新發送Toast

    fun startLogin(){
        viewModelScope.launch {
            _loginFlow.emit("Login Success")
        }
    }

//    private val _stateFlow= MutableStateFlow(0)
//    val stateFlow=_stateFlow.asStateFlow()
//
//    fun startTimer(){
//        val timer= Timer()
//            timer.scheduleAtFixedRate(object :TimerTask(){//1：TimerTask，2:第一次執行的time，3:間隔
//                override fun run() {
//                _stateFlow.value+=1
//                }
//            },0,1000)
//    }


    val timeFlow = flow {
        var time = 0
        while (true) {
            emit(time)
            kotlinx.coroutines.delay(1000)
            time++
        }
    }

    val stateFlow=
        timeFlow.stateIn(//使用stateIn將Flow轉換爲stateFlow，指定生命周期，超時機制（<=5s不重建，>5重建）->應對反轉屏幕和進入後臺
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )
}