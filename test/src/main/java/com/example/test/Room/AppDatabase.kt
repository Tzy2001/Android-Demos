package com.example.test.Room

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


//遷移數據庫時要更新版本，多個實體類的時候要用,隔開
//必須要繼承RoomDatabase抽象類和定義相應的抽象方法獲取Dao裡面的對數據的操作方法
@Database(version = 3, entities = [User::class, Book::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object {
//升級數據庫的方案
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table Book (name text not null,pages integer not null,id integer primary key autoincrement not null)")
            }
        }
//        新增字段的升級數據庫
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table Book add column author text not null default unknown ")
            }
        }


        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
//                .fallbackToDestructiveMigration()//暴力銷毀（只要升級就銷毀）
                .build().apply {
                    instance = this
                }
        }
    }
}