package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences
    private val inflate by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
        lifecycle.addObserver(MyObserver(lifecycle))
        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(countReserved)).get(MainViewModel::class.java)
        inflate.plusOneBtn.setOnClickListener {
//            viewModel.counter++
//            refreshCounter()
            viewModel.plusone()
        }
        inflate.clearBtn.setOnClickListener {
//            viewModel.counter = 0
//            refreshCounter()
            viewModel.clear()
        }
        viewModel.counter.observe(this, Observer { count ->
            inflate.infoText.text=count.toString()
        })


    }

    override fun onPause() {
        super.onPause()
        sp.edit {

            putInt("count_reserved", viewModel.counter.value ?:0)

        }
    }

    private fun refreshCounter() {
        inflate.infoText.text = viewModel.counter.toString()
    }
}