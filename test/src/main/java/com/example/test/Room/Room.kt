package com.example.test.Room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.test.R
import com.example.test.databinding.ActivityRoomBinding
import kotlin.concurrent.thread

class Room : AppCompatActivity() {
    private val binding by lazy { ActivityRoomBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userDao=AppDatabase.getDatabase(this).userDao()
        val bookDao=AppDatabase.getDatabase(this).bookDao()
        val user1=User("zhang","san",22)
        val user2=User("li","si",21)
        val book1=Book("三國演義",300,"1")
        val book2=Book("西遊記",300,"2")
        val book3=Book("紅樓夢",300,"3")
        val book4=Book("水滸傳",300,"4")
        binding.addDataBtn.setOnClickListener {
            thread {
                user1.id=userDao.insertUser(user1)
                user2.id=userDao.insertUser(user2)
            }
        }

        binding.updateDataBtn.setOnClickListener {
            thread {
                user1.age=42
                userDao.updateUser(user1)
            }
        }

        binding.deleteDataBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("si")
            }
        }

        binding.queryDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()) {
                    Log.d("Room",user.toString())
                }
            }
        }

        binding.addBookBtn.setOnClickListener {
            thread {
                book1.id=bookDao.insertBook(book1)
                book2.id=bookDao.insertBook(book2)
                book3.id=bookDao.insertBook(book3)
                book4.id=bookDao.insertBook(book4)
            }
        }

        binding.queryAllBook.setOnClickListener {
            thread {
                for (allBook in bookDao.queryAllBooks()) {
                    Log.d("Room",allBook.toString())
                }
            }
        }


    }
}