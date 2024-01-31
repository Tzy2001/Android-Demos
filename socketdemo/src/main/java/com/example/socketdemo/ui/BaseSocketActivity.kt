package com.example.socketdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketdemo.R
import com.example.socketdemo.SocketApp
import com.example.socketdemo.adapter.EmojiAdapter
import com.example.socketdemo.adapter.MsgAdapter
import com.example.socketdemo.client.ClientCallback
import com.example.socketdemo.client.SocketClient
import com.example.socketdemo.databinding.ActivityBaseSocketAvtivityBinding
import com.example.socketdemo.server.ServerCallback
import com.example.socketdemo.server.SocketServer
import com.google.android.material.bottomsheet.BottomSheetBehavior

open class BaseSocketActivity : BaseActivity(), ServerCallback, ClientCallback, EmojiCallback {

    private val TAG = BaseSocketActivity::class.java.simpleName
    private val binding by lazy { ActivityBaseSocketAvtivityBinding.inflate(layoutInflater) }

    lateinit var etMsg: EditText
    lateinit var btnSendMsg: Button
    lateinit var ivMore: ImageView

    //Socket服务是否打开
    var openSocket = false

    //Socket服务是否连接
    var connectSocket = false

    //消息列表
    private val messages = ArrayList<Message>()

    //消息适配器
    private lateinit var msgAdapter: MsgAdapter


    //是否显示表情
    private var isShowEmoji = false

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        etMsg = binding.layBottomSheetEdit.etMsg
        btnSendMsg = binding.layBottomSheetEdit.btnSendMsg
        ivMore = binding.layBottomSheetEdit.ivEmoji
        //初始化BottomSheet
        initBottomSheet()
        //初始化列表
        msgAdapter = MsgAdapter(messages)
        binding.rvMsg.apply {
            layoutManager = LinearLayoutManager(this@BaseSocketActivity)
            adapter = msgAdapter
        }
    }

    private fun initBottomSheet() {
        //Emoji布局
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.layBottomSheetEdit.bottomSheet).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
                isHideable = false
                isDraggable = false
            }
        //表情列表适配器
        binding.layBottomSheetEdit.rvEmoji.apply {
            layoutManager = GridLayoutManager(context, 6)
            adapter = EmojiAdapter(SocketApp.instance().emojiList).apply {
                setOnItemClickListener(object : EmojiAdapter.OnClickListener {
                    override fun onItemClick(position: Int) {
                        val charSequence = SocketApp.instance().emojiList[position]
                        checkedEmoji(charSequence)
                    }
                })
            }
        }
        //显示emoji
        binding.layBottomSheetEdit.ivEmoji.setOnClickListener {
            bottomSheetBehavior!!.state =
                if (isShowEmoji) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_EXPANDED
        }
        //BottomSheet显示隐藏的相关处理
        bottomSheetBehavior!!.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                isShowEmoji = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {//显示
                        binding.layBottomSheetEdit.ivEmoji.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@BaseSocketActivity,
                                R.drawable.ic_emoji_checked
                            )
                        )
                        true
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {//隐藏
                        binding.layBottomSheetEdit.ivEmoji.setImageDrawable(
                            ContextCompat.getDrawable(this@BaseSocketActivity, R.drawable.ic_emoji)
                        )
                        false
                    }

                    else -> false
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }


    fun setServerTitle(startService: View.OnClickListener) =
        setTitle("服务端", "IP: ${getIp()}", "开启服务", startService)

    fun setClientTitle(connectService: View.OnClickListener) =
        setTitle("客戶端", funcTitle = "开启连接", click = connectService)


    fun startServer() {
        openSocket = true
        SocketServer.startServer(this)
        showMsg("开启服务")
        binding.tvFunc.text = "关闭服务"
    }

    fun stopServer() {
        openSocket = false
        SocketServer.stopServer()
        showMsg("关闭服务")
        binding.tvFunc.text = "开启服务"
    }

    fun connectServer(ipAddress: String) {
        connectSocket = true
        SocketClient.connectServer(ipAddress, this)
        showMsg("连接服务")
        binding.tvFunc.text = "关闭连接"
    }

    fun closeConnect() {
        connectSocket = false
        SocketClient.closeConnect()
        showMsg("关闭連接")
        binding.tvFunc.text = "开启连接"
    }

    private fun setTitle(
        mTitle: String,
        mSubTitle: String = "",
        funcTitle: String,
        click: View.OnClickListener
    ) {
        binding.toolbar.apply {
            title = mTitle
            subtitle = mSubTitle
            setNavigationOnClickListener { onBackPressed() }
        }
        binding.tvFunc.text = funcTitle
        binding.tvFunc.setOnClickListener(click)
    }

    fun sendToClient(msg:String){
        SocketServer.sendToClient(msg)
        etMsg.setText("")
        updateList(true, msg)
    }
    fun sendToServer(msg:String){
        SocketClient.sendToServer(msg)
        etMsg.setText("")
        updateList(true, msg)
    }

    /**
     * 功能描述：更新列表
     * @param [isMyself]
     * @param [msg]
     * @author Tzy
     * @date 2024/01/18 10:06
     */
    private fun updateList(isMyself: Boolean, msg: String) {
        messages.add(Message(isMyself, msg))
        runOnUiThread {
            (if (messages.size == 0) 0 else messages.size - 1).apply {
                msgAdapter.notifyItemChanged(this)
                binding.rvMsg.smoothScrollToPosition(this)
            }
        }
    }


    override fun receiveServerMsg(ipAddress: String, msg: String) {
        updateList(false, msg)
    }

    override fun receiveClientMsg(ipAddress: String, msg: String) {
        updateList(false, msg)
    }


    override fun otherMsg(msg: String) {
        Log.d(TAG,"other: $msg")
    }

    override fun checkedEmoji(charSequence: CharSequence) {
        etMsg.apply {
            setText(text.toString()+charSequence)
            setSelection(text.toString().length)
        }
    }
}