package com.example.socketdemo.server

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object SocketServer {
    private val TAG = SocketServer::class.java.simpleName

    private const val SOCKET_PORT = 9528
    private var socket: Socket? = null

    private var serverSocket: ServerSocket? = null
    private lateinit var mCallback: ServerCallback

    private lateinit var outputStream: OutputStream

    var result = true

    private var serverThreadPool: ExecutorService? = null


    //开启服务
    fun startServer(callback: ServerCallback): Boolean {
        mCallback = callback
        Thread {
            try {
                serverSocket = ServerSocket(SOCKET_PORT)
                while (result) {
                    socket = serverSocket?.accept()
                    mCallback.otherMsg("${socket?.inetAddress} to connected")
                    ServerThread(socket!!, mCallback).start()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                result = false
            }
        }.start()
        return result
    }


    //关闭服务
    fun stopServer() {
        socket?.apply {
            shutdownInput()
            shutdownOutput()
            close()
        }
        serverSocket?.close()
        //關閉線程池
        serverThreadPool?.shutdownNow()
        serverThreadPool = null
    }


    //发送到客户端
    //发送消息到客户端时初始化线程，并执行子线程
    fun sendToClient(msg: String) {
        if (serverThreadPool == null) {
            serverThreadPool = Executors.newCachedThreadPool()
        }
        serverThreadPool?.execute {
            if (socket == null) {
                mCallback.otherMsg("客户端还未连接")
                return@execute
            }
            if (socket!!.isClosed) {
                Log.e(TAG, "sendToClient: Socket is closed")
                return@execute
            }
            outputStream = socket!!.getOutputStream()
            try {
                outputStream.write(msg.toByteArray())
                outputStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
                mCallback.otherMsg("向客户端发送消息: $msg 失败")
            }
        }
//        Thread {
//            if (socket!!.isClosed) {
//                Log.e(TAG, "sendToClient: Socket is closed")
//                return@Thread
//            }
//            outputStream = socket!!.getOutputStream()
//            try {
//                outputStream.write(msg.toByteArray())
//                outputStream.flush()
//                mCallback.otherMsg("toClient:$msg")
//                Log.d(TAG, "发送到客户端成功")
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Log.e(TAG, "向客户端发送消息失败")
//            }
//        }.start()
    }
动态代理
    /**回复心跳消息*/
    fun replyHeartBeat() {
        if (serverThreadPool == null) {
            serverThreadPool = Executors.newCachedThreadPool()
        }
        val msg = "洞拐收到，洞拐收到，Over!"
        serverThreadPool?.execute {
            if (socket == null) {
                mCallback.otherMsg("客户端还未连接")
                return@execute
            }
            if (socket!!.isClosed) {
                mCallback.otherMsg("Socket已关闭")
            }
            outputStream = socket!!.getOutputStream()
            try {
                outputStream.write(msg.toByteArray())
                outputStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
                mCallback.otherMsg("向客户端发送消息: $msg 失败")
            }
        }
    }


    class ServerThread(private val socket: Socket, private val callback: ServerCallback) :
        Thread() {
        override fun run() {
            val inputStream: InputStream?
            try {
                inputStream = socket.getInputStream()
                val buffer = ByteArray(1024)
                var len: Int
                var receiveStr = ""
                if (inputStream.available() == 0) {
                    Log.e(TAG, "inputStream.available() == 0")
                }

                while (inputStream.read(buffer).also { len = it } != -1) {
                    receiveStr += String(buffer, 0, len, Charsets.UTF_8)
                    if (len < 1024) {
                        socket.inetAddress.hostAddress?.let {
                            if (receiveStr == "洞幺洞幺，呼叫洞拐，听到请回答，听到请回答，Over!") {
                                //收到客户端发送的心跳消息
                                replyHeartBeat()
                            } else {
                                callback.receiveClientMsg(it, receiveStr)
                            }
                        }
                        receiveStr = ""
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                when (e) {
                    is SocketTimeoutException -> {
                        Log.e(TAG, "連接超時，正在重連")
                    }

                    is NoRouteToHostException -> {
                        Log.e(TAG, "該地址不存在，請檢查")
                    }

                    is ConnectException -> {
                        Log.e(TAG, "連接異常或被拒絕，請檢查")
                    }

                    is SocketException -> {
                        when (e.message) {
                            "Already connected" -> Log.e(TAG, "連接異常或被拒絕，請檢查")
                            "Socket closed" -> Log.e(TAG, "連接已關閉")
                        }
                    }
                }
//                e.message?.let { Log.e("socket error", it) }
            }
        }
    }
}