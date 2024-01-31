package com.example.socketdemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.socketdemo.R
import com.example.socketdemo.databinding.DialogEditIpBinding

class ClientPlusActivity : BaseSocketActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //連接關閉服務
        setClientTitle{if (connectSocket) closeConnect() else showEditDialog()}
        //發送消息給服務端
        btnSendMsg.setOnClickListener {
            val msg=etMsg.text.toString().trim()
            if (msg.isEmpty()){
                showMsg("請輸入要發送的消息")
                return@setOnClickListener
            }
            val isSend =if (connectSocket) connectSocket else false
            if (!isSend){
                showMsg("當前未開啓服務")
                return@setOnClickListener
            }
            sendToServer(msg)
        }
    }

    private fun showEditDialog() {
        val dialogBinding =
            DialogEditIpBinding.inflate(LayoutInflater.from(this@ClientPlusActivity), null, false)
        AlertDialog.Builder(this@ClientPlusActivity).apply {
            setIcon(R.drawable.ic_connect)
            setTitle("連接IP地址")
            setView(dialogBinding.root)
            setPositiveButton("確定") { dialog, _ ->
                val ip = dialogBinding.etIpAddress.text.toString()
                if (ip.isEmpty()) {
                    showMsg("請輸入Ip地址")
                    return@setPositiveButton
                }
                connectServer(ip)
                dialog.dismiss()
            }
            setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
        }.show()
    }
}