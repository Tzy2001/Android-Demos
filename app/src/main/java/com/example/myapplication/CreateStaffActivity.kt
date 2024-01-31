package com.example.myapplication

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityStaffCreateBinding


class CreateStaffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isPasswordVisible = false
        val inflate = ActivityStaffCreateBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        inflate.ibShowpassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
//            val isVisible = inflate.etAccountPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            val drawableRes =
                if (isPasswordVisible) R.drawable.ic_password_show else R.drawable.ic_password_hide
            inflate.ibShowpassword.setImageResource(drawableRes)
            inflate.etAccountPassword.inputType = if (isPasswordVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            inflate.etAccountPassword.setSelection(inflate.etAccountPassword.text.length) // 设置光标位置到文本末尾
        }
    }



}