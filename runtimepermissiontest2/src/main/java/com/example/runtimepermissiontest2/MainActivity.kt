package com.example.runtimepermissiontest2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.runtimepermissiontest2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.makeCall.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,
                   arrayOf(Manifest.permission.CALL_PHONE),1)
                }else{
                    call()
            }
         /*   try {
                val intent= Intent(Intent.ACTION_CALL)
                intent.data=Uri.parse("tel:10086")
                startActivity(intent)
            }catch (e : SecurityException){
                e.printStackTrace()
            }*/
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when(requestCode){
                1->{
                    if (grantResults.isNotEmpty() &&
                        grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        call()
                    }else{
                        Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun call(){
        try {
            val intent =Intent(Intent.ACTION_CALL)
            intent.data=Uri.parse("tel:10086")
            startActivity(intent)
        }catch (e: SecurityException){
            e.printStackTrace()
        }
    }
}