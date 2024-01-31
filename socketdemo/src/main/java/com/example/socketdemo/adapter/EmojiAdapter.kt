package com.example.socketdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.emoji2.text.EmojiCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.socketdemo.R
import com.example.socketdemo.databinding.ItemEmojiBinding

class EmojiAdapter(private val emojis: ArrayList<CharSequence>) :
    RecyclerView.Adapter<EmojiAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: ItemEmojiBinding) : RecyclerView.ViewHolder(itemView.root) {
        var mView: ItemEmojiBinding

        init {
            mView = itemView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEmojiBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = emojis.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emoji = emojis[position]
        holder.mView.tvEmoji.apply {
            text = EmojiCompat.get().process(emoji)
            setOnClickListener { clickListener?.onItemClick(position) }
        }
    }


    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    private var clickListener: OnClickListener? = null

    fun setOnItemClickListener(listener: OnClickListener) {
        clickListener = listener
    }
}