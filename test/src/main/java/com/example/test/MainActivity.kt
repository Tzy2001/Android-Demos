package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_WRITE_SETTINGS = 1

    //自動填充文本
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestWriteSettings()
        val textView = findViewById<AutoCompleteTextView>(R.id.autocomplete_country)
        val countries: Array<out String> = resources.getStringArray(R.array.countries_array)
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries).also { adapter ->
            textView.setAdapter(adapter)
        }

    }

    /**
     * 申请权限
     */
    private fun requestWriteSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //大于等于23 请求权限
            if (!Settings.System.canWrite(applicationContext)) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS)
            }
        } else {
            //小于23直接设置
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Settings.System.canWrite方法检测授权结果
                if (Settings.System.canWrite(applicationContext)) {
                    Toast.makeText(this,"获取了权限",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"您拒绝了权限",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
