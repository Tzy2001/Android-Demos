package com.example.socketdemo.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketdemo.R
import com.example.socketdemo.SocketApp
import com.example.socketdemo.adapter.EmojiAdapter
import com.example.socketdemo.adapter.MsgAdapter
import com.example.socketdemo.databinding.ActivityServerBinding
import com.example.socketdemo.server.ServerCallback
import com.example.socketdemo.server.SocketServer
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ServerActivity : BaseActivity(), ServerCallback, EmojiCallback {

    private val TAG = ServerActivity::class.java.simpleName
    private val binding by lazy { ActivityServerBinding.inflate(layoutInflater) }

    //Socket服务是否打开
    private var openSocket = false

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
        //初始化BottomSheet
        initBottomSheet()

    }

    private fun initView() {
        binding.toolbar.apply {
            subtitle = "IP:${getIp()}"
            setNavigationOnClickListener { onBackPressed() }
        }
        //开启服务/关闭服务/ 服务端处理
        binding.tvStartService.setOnClickListener {
            openSocket = if (openSocket) {
                SocketServer.stopServer()
                false
            } else SocketServer.startServer(this)
            Log.e(TAG, "openSocket:$openSocket")
            //顯示日志
            showMsg(if (openSocket) "開啟服務" else "關閉服務")
            //改變按鈕文字
            binding.tvStartService.text = if (openSocket) "開啟服務" else "關閉服務"
        }

        //發送消息給客戶端
        binding.layBottomSheetEdit.btnSendMsg.setOnClickListener {
            val msg = binding.layBottomSheetEdit.etMsg.text.toString().trim()
            if (msg.isEmpty()) {
                showMsg("請輸入要發送的信息")
                return@setOnClickListener
            }
            //檢查是否能發送信息
            val isSend = if (openSocket) openSocket else false
            if (!isSend) {
                showMsg("當前未開啟服務或連接服務")
                return@setOnClickListener
            }
            SocketServer.sendToClient(msg)
            binding.layBottomSheetEdit.etMsg.setText("")
            updateList(1, msg)
        }
        //顯示emoji
        binding.layBottomSheetEdit.ivEmoji.setOnClickListener {
            //顯示底部彈窗
            showEmojiDialog(this, this)
        }
        //初始化列表
        msgAdapter = MsgAdapter(messages)
        binding.rvMsg.apply {
            layoutManager = LinearLayoutManager(this@ServerActivity)
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
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {//显示
                        isShowEmoji = true
                        binding.layBottomSheetEdit.ivEmoji.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@ServerActivity,
                                R.drawable.ic_emoji_checked
                            )
                        )
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {//隐藏
                        isShowEmoji = false
                        binding.layBottomSheetEdit.ivEmoji.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@ServerActivity,
                                R.drawable.ic_emoji
                            )
                        )
                    }

                    else -> isShowEmoji = false
                }
                Log.e(TAG,"onStateChanged: $newState")
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    /**
     * 功能描述：更新列表
     * @param [type]
     * @param [msg]
     * @author Tzy
     * @date 2024/01/16 11:27
     */
    private fun updateList(type: Int, msg: String) {
//        messages.add(Message(type, msg))
        messages.add(Message(false, msg))
        runOnUiThread {
            (if (messages.size == 0) 0 else messages.size - 1).apply {
                msgAdapter.notifyItemChanged(this)
                binding.rvMsg.smoothScrollToPosition(this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun checkedEmoji(charSequence: CharSequence) {
        binding.layBottomSheetEdit.etMsg.apply {
            setText(text.toString() + charSequence)
            setSelection(text.toString().length)//光標移動到末尾
        }
    }

//    override fun receiveClientMsg(success: Boolean, msg: String) {
//        updateList(2, msg)
//    }

    override fun receiveClientMsg(ipAddress: String, msg: String) {
    }

    override fun otherMsg(msg: String) {
        Log.d(TAG, msg)
    }
}