package com.example.test.navigationDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.R
import com.example.test.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMain4Binding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}