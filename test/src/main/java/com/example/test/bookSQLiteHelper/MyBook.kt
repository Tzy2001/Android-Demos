package com.example.test.bookSQLiteHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ActivityMyBookBinding

class MyBook : AppCompatActivity() {
    private val binding by lazy { ActivityMyBookBinding.inflate(layoutInflater) }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //創建數據庫
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        binding.createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

        //添加數據
        binding.addData.setOnClickListener {
            val db = dbHelper.writableDatabase
//            Android默認語句
            val values1 = ContentValues().apply {
                //開始裝第一條數據
                put("name", "葫蘆娃救爺爺")
                put("author", "小白鼠")
                put("pages", "150")
                put("price", "10.99")
            }
            db.insert("Book", null, values1)
            val values2 = ContentValues().apply {
                //開始裝第一條數據
                put("name", "諸葛亮三顧劉備茅廬")
                put("author", "劉備")
                put("pages", "300")
                put("price", "95.0")
            }
//            db.insert("Book", null, values2)


//            sql語句
            db.execSQL("insert into Book(name ,author,pages,price) values(? ,? ,? ,?)",
                arrayOf("The Shy of S8","Ning","200","30.0")
            )
            db.execSQL("insert into Book(name ,author,pages,price) values(? ,? ,? ,?)",
                arrayOf("The JackLove of S8","Rookie","200","30.0")
            )
        }

//        更新數據
        binding.updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
//            Android默認語句
            val values = ContentValues()
            values.put("price", 10.99)
//            db.update("Book", values, "name=?", arrayOf("葫蘆娃救爺爺"))


//            SQL語句
            db.execSQL("update Book set price=? where name=?", arrayOf("10.99","葫蘆娃救爺爺"))
        }

//刪除數據
        binding.deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
//            Android默認語句
//            db.delete("Book", "name=?", arrayOf("葫蘆娃救爺爺"))


//            SQL語句
            db.execSQL("delete from Book where name=?", arrayOf("葫蘆娃救爺爺"))
        }

//        查詢數據

        binding.queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
//            Android默認語句
//            查詢Book表中所有的數據
//            val cursor = db.query("Book", null, null, null, null, null, null)
//            if (cursor.moveToFirst()) {
//                do {
////                遍歷Cursor對象，取出數據並打印
//                    val name = cursor.getString(cursor.getColumnIndex("name"))
//                    val author = cursor.getString(cursor.getColumnIndex("author"))
//                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
//                    val price = cursor.getString(cursor.getColumnIndex("price"))
//                    Log.d("MyBook", "book name is $name")
//                    Log.d("MyBook", "book author is $author")
//                    Log.d("MyBook", "book pages is $pages")
//                    Log.d("MyBook", "book price is $price")
//                } while (cursor.moveToNext())
//            }
//            cursor.close()


//            SQL語句
            val cursor=db.rawQuery("select * from Book ",null)
        }

    //結合事務，當出現異常時不會對原有的數據進行任何操作，所有的關於表的操作都在try裡面
    binding.replaceData.setOnClickListener {
        val db=dbHelper.writableDatabase
        db.beginTransaction()//開啟事務
        try {
            db.delete("Book",null,null)
            if (true){
//                手動拋出一個異常，用來確認是否回建表成功
                throw NullPointerException()
            }
            val values=ContentValues().apply {
                put("name", "諸葛亮三顧劉備茅廬")
                put("author", "劉備")
                put("pages", "300")
                put("price", "95.0")
            }
            db.insert("Book",null,values)
            db.setTransactionSuccessful()//事務執行成功
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            db.endTransaction()//結束事務
        }
    }


    }
}