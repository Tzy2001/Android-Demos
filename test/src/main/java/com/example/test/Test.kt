package com.example.test

import android.app.DatePickerDialog
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.Util.HideTextWatcher
import com.example.test.databinding.LayoutTestBinding
import java.util.Calendar

class Test : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private val binding by lazy { LayoutTestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //隱藏系統軟鍵盤
        binding.edPhone.addTextChangedListener(HideTextWatcher(binding.edPhone, 11, this))
        binding.edPhone.addTextChangedListener(HideTextWatcher(binding.edPassword, 6, this))
        binding.edPassword.setOnFocusChangeListener { _, b ->
            if (b) {
                val phone = binding.edPhone.text.toString()
                if (phone.isNullOrBlank() || phone.length < 11) {
                    binding.edPhone.requestFocus()
                    Toast.makeText(this, "錯誤的手機號格式，請重新輸入手機號", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val format = String.format("您選擇的日期是%d年%d月%d日", p1, p2 + 1, p3)
        binding.tvDate.text = format
    }


}