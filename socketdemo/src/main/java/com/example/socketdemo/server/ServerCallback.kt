package com.example.socketdemo.server

interface ServerCallback {

    //接收客户端消息
    fun receiveClientMsg(ipAddress:String,msg:String)

    //其他消息
    fun otherMsg(msg:String)
}