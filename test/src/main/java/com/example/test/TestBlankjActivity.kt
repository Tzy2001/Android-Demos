package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.blankj.utilcode.util.BrightnessUtils
import com.example.test.databinding.ActivityTestBlankjBinding

class TestBlankjActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTestBlankjBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                val intent =  Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName())
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 200);
            } else {
                // 如果有权限做些什么
                binding.tvTest.text = BrightnessUtils.isAutoBrightnessEnabled().toString()
                BrightnessUtils.setBrightness(255)
            }


        }
    }
}