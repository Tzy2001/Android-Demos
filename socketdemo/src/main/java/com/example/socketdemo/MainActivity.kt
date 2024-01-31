package com.example.socketdemo

import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketdemo.adapter.MsgAdapter
import com.example.socketdemo.client.ClientCallback
import com.example.socketdemo.client.SocketClient
import com.example.socketdemo.databinding.ActivityMainBinding
import com.example.socketdemo.server.ServerCallback
import com.example.socketdemo.server.SocketServer
import com.example.socketdemo.ui.Message

class MainActivity : AppCompatActivity(), ServerCallback, ClientCallback {

    private val TAG = MainActivity::class.java.simpleName

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val buffer = StringBuffer()

    //當前是否為服務端
    private var isServer = true

    //Socket服務是否打開
    private var openSocket = false

    //Socket服務是否連接
    private var connectSocket = false


    //消息列表
    private val messages = ArrayList<Message>()

    //消息适配器
    private lateinit var msgAdapter: MsgAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        binding.tvIpAddress.text = "IP地址: ${getIp()}"
        //服務端和客戶端切換
        binding.rg.setOnCheckedChangeListener { _, checkedId ->
            isServer = when (checkedId) {
                R.id.rb_server -> true
                R.id.rb_client -> false
                else -> false
            }
            binding.layServer.visibility = if (isServer) View.VISIBLE else View.GONE
            binding.layClient.visibility = if (isServer) View.GONE else View.VISIBLE
            binding.etMsg.hint = if (isServer) "發送給客戶端" else "發送給服務端"
        }
        //開啟服務/關閉服務 服務端處理
        binding.btnStartService.setOnClickListener {
            openSocket =
                if (openSocket) {
                    SocketServer.stopServer();false
                } else {
                    SocketServer.startServer(
                        this
                    )
                }
            //顯示日誌
//            showInfo(if (openSocket) "開啟服務" else "關閉服務")
            //消息提示
            showMsg(if (openSocket) "開啟服務" else "關閉服務")
            //改變按鈕文字
            binding.btnStartService.text = if (openSocket) "關閉服務" else "開啟服務"
        }
        //連接服務/斷開連接 客戶端處理
        binding.btnConnectService.setOnClickListener {
            val ip = binding.etIpAddress.text.toString()
            if (ip.isEmpty()) {
                showMsg("請輸入IP地址")
                return@setOnClickListener
            }
            connectSocket = if (connectSocket) {
                SocketClient.closeConnect()
                false
            } else {
                SocketClient.connectServer(ip, this)
                true
            }
//            showInfo(if (connectSocket) "連接服务" else "關閉連接")
            //消息提示
            showMsg(if (connectSocket) "連接服务" else "關閉連接")
            binding.btnConnectService.text = if (connectSocket) "关闭连接" else "連接服務"
        }
        //發送消息個服務端、客戶端
        binding.btnSendMsg.setOnClickListener {
            val msg = binding.etMsg.text.toString()
            if (msg.isEmpty()) {
                showMsg("請輸入要發送的信息")
                return@setOnClickListener
            }
            //檢查是否能發送消息
            val isSend = if (openSocket) openSocket else if (connectSocket) connectSocket else false
            if (!isSend) {
                showMsg("當前未開啟服務或連接服務")
                return@setOnClickListener
            }
            if (isServer) SocketServer.sendToClient(msg) else SocketClient.sendToServer(msg)
            binding.etMsg.setText("")
            updateList(if (isServer) 1 else 2, msg)
        }
        //初始化列表
        msgAdapter = MsgAdapter(messages)
        binding.rvMsg.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = msgAdapter
        }
    }

    private fun showMsg(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

//    private fun showInfo(info: String) {
//        buffer.append(info).append("\n")
//        runOnUiThread { binding.tvInfo.text = buffer.toString() }
//    }

    /**
     * 功能描述：更新列表数据
     * @param [type]
     * @param [msg]
     * @author Tzy
     * @date 2024/01/16 10:45
     */
    private fun updateList(type: Int, msg: String) {
//        messages.add(Message(type,msg))
        messages.add(Message(false,msg))
        runOnUiThread {
            (if (messages.size==0) 0 else messages.size-1).apply {
                msgAdapter.notifyItemChanged(this)
                binding.rvMsg.smoothScrollToPosition(this)
            }
        }
    }

    private fun getIp() =
        intToIP((applicationContext.getSystemService(WIFI_SERVICE) as WifiManager).connectionInfo.ipAddress)

    private fun intToIP(ip: Int) =
        "${(ip and 0xFF)}.${(ip shr 8 and 0xFF)}.${(ip shr 16 and 0xFF)}.${(ip shr 24 and 0xFF)}"

//    override fun receiveServerMsg(msg: String) {
//        updateList(1,msg)
//    }
//
//    override fun receiveClientMsg(success: Boolean, msg: String) {
//        updateList(2,msg)
//    }

    override fun receiveServerMsg(ipAddress: String, msg: String) {
    }

    override fun receiveClientMsg(ipAddress: String, msg: String) {
    }

    override fun otherMsg(msg: String) {
        Log.d(TAG,msg)
    }


}