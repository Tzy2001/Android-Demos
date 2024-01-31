package com.example.test.contentProvider

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.test.R
import com.example.test.databinding.ActivityProviderTestBinding

class ProviderTest : AppCompatActivity() {
    var bookId: String? = null

    private val binding by lazy { ActivityProviderTestBinding.inflate(layoutInflater) }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addData.setOnClickListener {
//            添加數據
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            val values = contentValuesOf(
                "name" to "A Clash of Kings",
                "author" to "Tzy", "pages" to "666", "price" to "23.85"
            )
            val newUri = contentResolver.insert(uri, values)
            bookId = newUri?.pathSegments?.get(1)
        }
        binding.queryData.setOnClickListener {
//            查詢數據
            val uri = Uri.parse("content://com.example.databasetest.provider/book")
            contentResolver.query(uri, null, null, null, null)?.apply {
                while (moveToNext()) {
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    val pages = getInt(getColumnIndex("pages"))
                    val price = getDouble(getColumnIndex("price"))
                    Log.d("ProviderTest", "book name is $name")
                    Log.d("ProviderTest", "author name is $author")
                    Log.d("ProviderTest", "pages name is $pages")
                    Log.d("ProviderTest", "price name is $price")
                }
                close()
            }
        }


        binding.updateData.setOnClickListener {
//            更新數據
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/book/$it")
                val values =
                    contentValuesOf("name" to "A Clash of Kings", "pages" to 1216, "price" to 26.46)
                contentResolver.update(uri, values, null, null)
            }
        }

        binding.deleteData.setOnClickListener {
//            刪除數據
            bookId?.let {
                val uri = Uri.parse("content://com.example.databasetest.provider/nook/$it")
                contentResolver.delete(uri, null, null)
            }
        }

    }
}