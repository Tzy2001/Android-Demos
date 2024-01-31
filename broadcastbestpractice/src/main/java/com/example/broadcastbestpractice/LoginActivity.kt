package com.example.broadcastbestpractice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.example.broadcastbestpractice.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private val inflate by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_password", false)
        if (isRemember) {
            //將賬號和密碼都設置到文本框中
            val account = prefs.getString("account", "")
            val password = prefs.getString("password", "")
            inflate.accountEdit.setText(account)
            inflate.PasswordEdit.setText(password)
            inflate.rememberPass.isChecked = true
        }
        inflate.login.setOnClickListener {
            val account = inflate.accountEdit.text.toString()
            val password = inflate.PasswordEdit.text.toString()
            if (account == "admin" && password == "123456") {
                val editor = prefs.edit()
                if (inflate.rememberPass.isChecked) {
                    editor.putBoolean("remember_password", true)
                    editor.putString("account", account)
                    editor.putString("password", password)
                } else {
                    editor.clear()
                }
                editor.apply()
//                val intent = Intent(this ,MainActivity::class.java)
                val intent = Intent("com.example.broadcastbestpractice.ACTION_START")
//                intent.addCategory("com.example.broadcastbestpractice.MY_CATEGORY")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "account or password is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}