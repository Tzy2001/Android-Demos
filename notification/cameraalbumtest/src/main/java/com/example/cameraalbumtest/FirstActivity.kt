package com.example.cameraalbumtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cameraalbumtest.databinding.ItemFirstBinding

class FirstActivity :AppCompatActivity(){
    private val binding by lazy { ItemFirstBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        Log.d(tag,"onCreate")
        binding.btn1.setOnClickListener {
            /*val data="Hello SecondActivity"
            val intent=Intent(this,SecondActivity::class.java)
            intent.putExtra("extra_data",data)
            startActivity(intent)*/
            val intent=Intent(this,SecondActivity::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> if (resultCode== RESULT_OK){
                val returnedData=data?.getStringExtra("data_return")
                Log.d("FirstActivity","data_return is $returnedData")
                binding.tv.setText(returnedData)
            }
        }
    }

}