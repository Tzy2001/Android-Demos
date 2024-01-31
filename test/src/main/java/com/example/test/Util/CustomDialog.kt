package com.example.test.Util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.test.databinding.DialogLayoutBinding

class CustomDialog(context: Context) : Dialog(context) {
    private val binding by lazy { DialogLayoutBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.closeDialog.setOnClickListener {
            dismiss()
        }
    }

    public fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    public fun setContent(content: String) {
        binding.tvContent.text = content
    }
}