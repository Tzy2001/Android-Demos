package com.example.databasetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databasetest.databinding.TestBinding

class Test:AppCompatActivity() {
    private val binding by lazy {TestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.et1.setPadding(0,0,16,0)
    }
}