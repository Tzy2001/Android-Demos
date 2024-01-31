package com.example.databasetest

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.databasetest.databinding.ActivityMainBinding
import java.lang.Exception
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    private val inflate by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 1)
        inflate.createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }
        inflate.addData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {
                //開始組裝第一條數據
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)

            }
            db.insert("Book", null, values1)//插入第一條數據
            val values2 = ContentValues().apply {
                //開始組裝第二條數據
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 20.00)
            }
            db.insert("Book", null, values2)//插入第二條數據
        }
        inflate.updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            db.update("Book", values, "name=?", arrayOf("The Da Vinci Code"))
        }
        inflate.deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))
        }
        inflate.queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            //查詢Book表中的所有數據
            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    //遍曆Cursor對象，取出數據並打印
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))
                    Log.d("MainActivity", "book name is $name")
                    Log.d("MainActivity", "book author is $author")
                    Log.d("MainActivity", "book pages is $pages")
                    Log.d("MainActivity", "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()
        }

        inflate.replaceData.setOnClickListener {
            val db=dbHelper.writableDatabase
            db.beginTransaction()
            try {
                db.delete("Book",null, null)
                if (true){
                    //手動拋出一個異常，讓食物失敗
                    throw NullPointerException()
                }
                val values=ContentValues().apply {
                    put("name","Game of Thrones")
                    put("author","George Martin")
                    put("pages","720")
                    put("price","20.85")
                }
                db.insert("Book",null,values)
                db.setTransactionSuccessful()//事務已經執行成功
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                db.endTransaction()//結束事務
            }
        }
    }
}