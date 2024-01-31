package com.example.test.bookSQLiteHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

//    real 浮點型 text文本型 blon二進制 primary key autoincrement主鍵自增
    private val createBook = "create table Book(" +
            "id integer primary key autoincrement," +
            "author text," +
            "price  real," +
            "pages integer," +
            "name text)"

    private val createCategory = "create table Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createBook)
        db?.execSQL(createCategory)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Book")
        db?.execSQL("drop table if exists Category")
        onCreate(db)

////        數據庫更新時的操作  建新表
//        if(oldVersion<=1){
//            db?.execSQL(createCategory)
//        }
////        新增字段
//        if (oldVersion<=2){
//            db?.execSQL("alter table Book add column category_id integer")
//        }
    }

}