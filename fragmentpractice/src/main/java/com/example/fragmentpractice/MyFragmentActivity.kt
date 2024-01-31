package com.example.fragmentpractice

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fragmentpractice.databinding.ActivityMyFragmentBinding

class MyFragmentActivity : AppCompatActivity() {
  /*  private val binding by lazy { ActivityMyFragmentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }


    fun initView(){
setStyle(R.drawable.wode)
    }
    fun setStyle(selector:Int,rb:RadioButton){
        val drawableHome:Drawable=getResources().getDrawable(selector)
        drawableHome.setBounds(0,0,80,80)
        rb.setCompoundDrawables(null,drawableHome,null,null)
    }*/
}