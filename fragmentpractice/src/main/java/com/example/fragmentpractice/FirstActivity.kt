package com.example.fragmentpractice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter

class FirstActivity : AppCompatActivity() {
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val tempData = "Something you like"
        outState.putString("data_key", tempData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_first)
        if (savedInstanceState != null) {
            val tempdata = savedInstanceState.getString("data_key")
        }
//        val intent=Intent(this,SecondActivity::class.java)
//        val intent=Intent("com.example.fragmentpractice.ACTION_START")
        val data = "Hello SecondActivity!!"
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("extra_data", data)
        startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        val ed = findViewById<EditText>(R.id.ed1)
        val inputText = ed.text.toString()
        save(inputText)
    }

    private fun save(inputText: String) {
        try {
            val output = openFileOutput("data", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(inputText)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}