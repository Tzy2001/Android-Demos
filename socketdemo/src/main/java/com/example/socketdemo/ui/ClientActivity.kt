package com.example.socketdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socketdemo.R
import com.example.socketdemo.SocketApp
import com.example.socketdemo.adapter.EmojiAdapter
import com.example.socketdemo.adapter.MsgAdapter
import com.example.socketdemo.client.ClientCallback
import com.example.socketdemo.client.SocketClient
import com.example.socketdemo.databinding.ActivityClientBinding
import com.example.socketdemo.databinding.DialogEditIpBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ClientActivity : BaseActivity(), ClientCallback, EmojiCallback {
    private val TAG = ClientActivity::class.java.simpleName
    private val binding by lazy { ActivityClientBinding.inflate(layoutInflater) }

    //Socket服務是否連接
    private var connectSocket = false

    //消息列表
    private val messages = ArrayList<Message>()

    //消息適配器
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
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        //連接服務/斷開連接 服務端處理
        binding.tvConnectService.setOnClickListener {
            if (connectSocket) {
                SocketClient.closeConnect()
                connectSocket = false
                showMsg("關閉連接")
            } else {
                showEditDialog()
            }
            binding.tvConnectService.text = if (connectSocket) "關閉連接" else "連接"
        }
        //發送消息給服務端
        binding.layBottomSheetEdit.btnSendMsg.setOnClickListener {
            val msg = binding.layBottomSheetEdit.etMsg.text.toString().trim()
            if (msg.isEmpty()) {
                showMsg("請輸入要發送的消息")
                return@setOnClickListener
            }
            //檢查是否能發送消息
            val isSend = if (connectSocket) connectSocket else false
            if (!isSend) {
                showMsg("當前未開啓服務或連接服務")
                return@setOnClickListener
            }
            SocketClient.sendToServer(msg)
            binding.layBottomSheetEdit.etMsg.setText("")
            updateList(2, msg)
        }
        binding.layBottomSheetEdit.ivEmoji.setOnClickListener {
            showEmojiDialog(this, this)
        }

        msgAdapter = MsgAdapter(messages)
        binding.rvMsg.apply {
            layoutManager = LinearLayoutManager(this@ClientActivity)
            adapter = msgAdapter
        }
    }

    private fun showEditDialog() {
        val dialogBinding =
            DialogEditIpBinding.inflate(LayoutInflater.from(this@ClientActivity), null, false)
        AlertDialog.Builder(this@ClientActivity).apply {
            setIcon(R.drawable.ic_connect)
            setTitle("鏈接IP地址")
            setView(dialogBinding.root)
            setPositiveButton("確定") { dialog, _ ->
                val ip = dialogBinding.etIpAddress.text.toString()
                if (ip.isEmpty()) {
                    showMsg("請輸入IP地址")
                    return@setPositiveButton
                }
                connectSocket = true
                SocketClient.connectServer(ip, this@ClientActivity)
                showMsg("連接服務")
                binding.tvConnectService.text = "關閉連接"
                dialog.dismiss()
            }
            setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
        }.show()
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
                                this@ClientActivity,
                                R.drawable.ic_emoji_checked
                            )
                        )
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {//隐藏
                        isShowEmoji = false
                        binding.layBottomSheetEdit.ivEmoji.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@ClientActivity,
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

    override fun checkedEmoji(charSequence: CharSequence) {
        binding.layBottomSheetEdit.etMsg.apply {
            setText(text.toString() + charSequence)
            setSelection(text.toString().length)
        }
    }

//    override fun receiveServerMsg(msg: String) {
//        updateList(1, msg)
//    }

    override fun receiveServerMsg(ipAddress: String, msg: String) {
    }

    override fun otherMsg(msg: String) {
        Log.d(TAG, msg)
    }
}