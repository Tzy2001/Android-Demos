package com.example.test.progressBar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.test.contentProvider.later
import com.example.test.databinding.ActivityProgressBarDemoBinding

class ProgressBarDemo : AppCompatActivity() {
    private val binding by lazy { ActivityProgressBarDemoBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val p by later {
            Log.d("123456", "456789")
            val a: String = "dasdsadasdas"
            a
        }
        binding.btnTestProgress.setOnClickListener {
            binding.apply {
                horizontalProgressBar.start()
                circleProgressBar.start()
                Log.d("123456", p.toString())
//                println(p)
            }
        }
    }
}