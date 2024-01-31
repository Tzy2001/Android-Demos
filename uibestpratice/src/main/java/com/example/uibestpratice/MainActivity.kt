package com.example.uibestpratice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uibestpratice.Adapter.MsgAdapter
import com.example.uibestpratice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val msgList = ArrayList<Msg>()
    private  var adapter: MsgAdapter?=null
    private val inflate by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
        initMsg()
        val layoutManager = LinearLayoutManager(this)
        inflate.recycleView.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        inflate.recycleView.adapter = adapter
        inflate.send.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            inflate.send -> {
                val content = inflate.inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)
                    adapter?.notifyItemInserted(msgList.size - 1)
                    inflate.recycleView.scrollToPosition(msgList.size - 1)
                    inflate.inputText.setText("")
                }
            }
        }
    }

    private fun initMsg() {
        val msg1 = Msg("Hello guy.", Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("Hello. Who is that?", Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg("123", Msg.TYPE_RECEIVED)
        msgList.add(msg3)

    }
}