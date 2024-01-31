package com.example.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.fragment.app.Fragment
import com.example.shopping.databinding.ActivityMainBinding
import com.example.shopping.databinding.LeftFragmentBinding
import com.example.shopping.databinding.RightFragmentBinding

class MainActivity : AppCompatActivity() {
    //    private val leftfrag by lazy { LeftFragmentBinding.inflate(layoutInflater) }
    /* val a=RightFragment()
     val b=LeftFragment()*/
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //    val fragment = supportFragmentManager.findFragmentById(R.id.left_frag) as LeftFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val findViewById = findViewById<TextView>(R.id.txt_content)
        findViewById<Button>(R.id.button).setOnClickListener {
            replaceFragment(AnotherRightFragment())
        }
        replaceFragment(RightFragment())
//     supportFragmentManager.findById<LeftFragment>(R.id.left_frag)

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.right_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}