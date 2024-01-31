package com.example.playaudiotest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playaudiotest.databinding.ActivityPlayVideoTestBinding

class PlayVideoTest : AppCompatActivity() {
    private val binding by lazy {
       ActivityPlayVideoTestBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val uri= Uri.parse("android.resource://$packageName/${R.raw.video}")
        binding.videoView.setVideoURI(uri)
        binding.play.setOnClickListener {
            if (!binding.videoView.isPlaying){
                binding.videoView.start()
            }
        }
        binding.pause.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.pause()
            }
        }
        binding.stop.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.resume()//重新播放
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.suspend()
    }
}