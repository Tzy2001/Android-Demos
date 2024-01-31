package com.example.test.editTextDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.example.test.R
import com.example.test.databinding.ActivityEditTextTestBinding

class EditTextTest : AppCompatActivity() {
    private val binding by lazy { ActivityEditTextTestBinding.inflate(layoutInflater) }
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.edTestPassword.setHorizontallyScrolling(true)//設置EditText不換行
        binding.cbTestPassword.setOnCheckedChangeListener { _, checked ->
            binding.edTestPassword.transformationMethod =
                if (checked) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
            binding.edTestPassword.setSelection(binding.edTestPassword.length())
        }
//        {
//            if (!flag) {
//                binding.edTestPassword.transformationMethod =
//                    HideReturnsTransformationMethod.getInstance()
//                flag = false
//            } else {
//                binding.edTestPassword.transformationMethod =
//                    PasswordTransformationMethod.getInstance()
//                flag = true
//            }
//        }
    }
}