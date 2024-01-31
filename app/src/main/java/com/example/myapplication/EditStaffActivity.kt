package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityStaffEditBinding

class EditStaffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 val inflate=ActivityStaffEditBinding.inflate(layoutInflater)
        setContentView(inflate.root)

//  setContentView(R.layout.activity_staff_edit)
    }

}