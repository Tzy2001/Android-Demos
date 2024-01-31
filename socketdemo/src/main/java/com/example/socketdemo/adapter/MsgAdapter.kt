package com.example.socketdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socketdemo.databinding.ItemRvMsgBinding

class MsgAdapter(private val messages: ArrayList<com.example.socketdemo.ui.Message>) :
    RecyclerView.Adapter<MsgAdapter.ViewHolder>() {


    inner class ViewHolder(view: ItemRvMsgBinding) : RecyclerView.ViewHolder(view.root) {
        var mView: ItemRvMsgBinding

        init {
            mView = view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvMsgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        if (message.isMyself) {
            holder.mView.tvMyselfMsg.text = message.msg
        } else {
            holder.mView.tvOtherMsg.text = message.msg
        }
        holder.mView.layOther.visibility=if (message.isMyself) View.GONE else View.VISIBLE
        holder.mView.layMyself.visibility=if (message.isMyself) View.VISIBLE else View.GONE
    }

}