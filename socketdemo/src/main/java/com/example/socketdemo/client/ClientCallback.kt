package com.example.socketdemo.client

interface ClientCallback {
    //接收服务端的消息
    fun receiveServerMsg(ipAddress:String,msg: String)
    //其他消息
    fun otherMsg(msg: String)
}