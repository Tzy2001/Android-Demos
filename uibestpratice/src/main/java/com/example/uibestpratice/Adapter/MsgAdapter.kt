package com.example.uibestpratice.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uibestpratice.LeftViewHolder
import com.example.uibestpratice.Msg
import com.example.uibestpratice.MsgViewHolder
import com.example.uibestpratice.R
import com.example.uibestpratice.RightViewHolder
import kotlinx.coroutines.NonDisposableHandle.parent
import java.lang.IllegalArgumentException

class MsgAdapter(val msglist:List<Msg>):RecyclerView.Adapter<MsgViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val msg =msglist[position]
        return msg.type
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=if (viewType==Msg.TYPE_RECEIVED) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mag_left_item,parent,false)
        LeftViewHolder(view)

    }else{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mag_right_item,parent,false)
        RightViewHolder(view)

    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg=msglist[position]
        when(holder){
            is LeftViewHolder->holder.leftMsg.text=msg.content
            is RightViewHolder->holder.rightMsg.text=msg.content
        }
    }

    override fun getItemCount()=msglist.size




}