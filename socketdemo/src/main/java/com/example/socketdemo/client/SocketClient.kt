package com.example.socketdemo.client

import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object SocketClient {

    private val TAG = SocketClient::class.java.simpleName

    //心跳時間間隔
    private const val HEART_SPACETIME = 3 * 1000

    //客戶端線程池
    private var clientThreadPool: ExecutorService? = null
    private val mHandler: Handler = Handler()

    private var socket: Socket? = null
    private var outputStream: OutputStream? = null
    private var inputStreamReader: InputStreamReader? = null
    private lateinit var mCallback: ClientCallback
    private const val SOCKET_PORT = 9528

    //連接服務
    fun connectServer(ipAddress: String, callback: ClientCallback) {
        mCallback = callback
        Thread {
            try {
                socket = Socket(ipAddress, SOCKET_PORT)
                //開啟心跳,每隔3秒鐘，發送一次心跳
                mHandler.post(mHeartRunnable)
                ClientThread(socket!!, mCallback).start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    //關閉連接
    fun closeConnect() {
        inputStreamReader?.close()
        outputStream?.close()
        socket?.close()
        //关闭线程池
        clientThreadPool?.shutdownNow()
        clientThreadPool = null
//        socket?.apply {
//            shutdownInput()
//            shutdownOutput()
//            close()
//        }
//        Log.d(TAG, "SocketClient: closeConnect")
    }


    //發送數據到服務器
    fun sendToServer(msg: String) {
        if (clientThreadPool == null) {
            clientThreadPool = Executors.newSingleThreadExecutor()
        }
        clientThreadPool?.execute {
            if (socket == null) {
                mCallback.otherMsg("客户端还未连接")
                return@execute
            }
            if (socket!!.isClosed) {
                mCallback.otherMsg("Socket已关闭")
                return@execute
            }
            outputStream = socket?.getOutputStream()
            try {
                outputStream?.write(msg.toByteArray())
                outputStream?.flush()
            } catch (e: IOException) {
                e.printStackTrace()
                mCallback.otherMsg("向服务端发送消息: $msg 失败")
            }
        }
//        Thread {
//            if (socket!!.isClosed) {
//                Log.e(TAG, "SendToServer: Socket is closed")
//                return@Thread
//            }
//            outputStream = socket?.getOutputStream()
//            try {
//                outputStream?.write(msg.toByteArray())
//                outputStream?.flush()
//                mCallback.otherMsg("toServer: $msg")
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Log.e(TAG, "向服務端發送消息失敗")
//            }
//        }.start()
    }


    private val mHeartRunnable = Runnable { sendHeartbeat() }

    //發送心跳消息
    private fun sendHeartbeat() {
        if (clientThreadPool == null) {
            clientThreadPool = Executors.newSingleThreadExecutor()
        }
        val msg = "洞幺洞幺，呼叫洞拐，听到请回答，听到请回答，Over!"
        clientThreadPool?.execute {
            if (socket == null) {
                mCallback.otherMsg("客戶端還未鏈接")
                return@execute
            }
            if (socket!!.isClosed) {
                mCallback.otherMsg("Socket已關閉")
                return@execute
            }
            outputStream = socket?.getOutputStream()
            try {
                outputStream?.write(msg.toByteArray())
                outputStream?.flush()
                //發送成功之後，重新建立一個心跳消息
                mHandler.postDelayed(mHeartRunnable, HEART_SPACETIME.toLong())
                Log.i(TAG, msg)
            } catch (e: IOException) {
                e.printStackTrace()
                mCallback.otherMsg("想服務端發送消息: ${msg}失敗")
            }
        }
    }

    class ClientThread(private val socket: Socket, private val callback: ClientCallback) :
        Thread() {
        override fun run() {
            val inputStream: InputStream?
            try {
                inputStream = socket.getInputStream()
                val buffer = ByteArray(1024)
                var len: Int
                var receiveStr = ""
                if (inputStream.available() == 0) {
                    Log.e(TAG, "inputStream.available()==0")
                }
                while (inputStream.read(buffer).also { len = it } != -1) {
                    receiveStr += String(buffer, 0, len, Charsets.UTF_8)
                    if (len < 1024) {
//                        socket.inetAddress.hostAddress?.let { callback.receiveServerMsg(it,receiveStr) }
//                        receiveStr=""
                        socket.inetAddress.hostAddress?.let {
                            if (receiveStr == "洞拐收到，洞拐收到，Over!"){//收到来自服务端的心跳回复消息
                                Log.i(TAG,"洞拐收到，洞拐收到，Over!")
                                //準備回復
                            }else{
                                callback.receiveServerMsg(it,receiveStr)
                            }
                        }
                        receiveStr=""
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                when (e) {
                    is SocketTimeoutException -> {
                        Log.e(TAG, "连接超时，正在重连")
                    }
                    is NoRouteToHostException -> {
                        Log.e(TAG, "该地址不存在，请检查")
                    }
                    is ConnectException -> {
                        Log.e(TAG, "连接异常或被拒绝，请检查")
                    }
                    is SocketException -> {
                        when (e.message) {
                            "Already connected" -> Log.e(TAG, "连接异常或被拒绝，请检查")
                            "Socket closed" -> Log.e(TAG, "连接已关闭")
                        }
                    }
                }
            }
        }
    }

}