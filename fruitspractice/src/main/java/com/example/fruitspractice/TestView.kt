package com.example.fruitspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.fruitspractice.databinding.ActivityTestViewBinding

class TestView : AppCompatActivity() {
    private val binding by lazy { ActivityTestViewBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val content = SpannableString(binding.tvTest.text.toString())
//        binding.tvTest.text = content
//        binding.tvTest.textSize=35f
//        val drawable =  ContextCompat.getDrawable(applicationContext, R.drawable.apple_pic)
//        drawable?.setBounds(0, 0, 50, 50)
//        binding.tvTest.setCompoundDrawables(null, null, null, drawable)
//        binding.tvTest.compoundDrawablePadding=50
    }
}