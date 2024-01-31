package com.example.socketdemo.ui

import android.os.Bundle

class ServerPlusActivity:BaseSocketActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //開啓服務/停止服務
        setServerTitle {if (openSocket) stopServer() else startServer()}
        //發送消息給服務端
        btnSendMsg.setOnClickListener {
            val msg =etMsg.text.toString().trim()
            if (msg.isEmpty()){
                showMsg("請輸入消息")
                return@setOnClickListener
            }
            val isSend =if (openSocket) openSocket else false
            if (!isSend){
                showMsg("當前未開啓服務")
                return@setOnClickListener
            }
            sendToClient(msg)
        }
    }
}