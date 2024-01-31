package com.example.fruitspractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruitspractice.Adapter.PrjFailedPrintPositionAdapter
import com.example.fruitspractice.Bean.Fruits
import com.example.fruitspractice.databinding.TestItemBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class Test : AppCompatActivity() {
    private val list = ArrayList<String>()
    private val inflate by lazy { TestItemBinding.inflate(layoutInflater) }
    private var prjFailedPrintPositionAdapter: PrjFailedPrintPositionAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
        val a: String = "asd"
        //dsad
        //dsad
        //dsad
        //dsad
        //dsad
        //dsad

        initFruits()
        Log.d("testActivity", "${a.equals(null)}")
        if (prjFailedPrintPositionAdapter == null) {
            prjFailedPrintPositionAdapter = PrjFailedPrintPositionAdapter(list)
        }
        inflate.recycleView.adapter = prjFailedPrintPositionAdapter
        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
//        val flexboxLayoutManager = FlexboxLayoutManager(this)
//        flexboxLayoutManager.flexDirection = FlexDirection.ROW //主轴为水平方向，起点在左端。
//
//        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP) //按正常方向换行
//
//        flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START //交叉轴的起点对齐。
////        val layoutManager=LinearLayoutManager(this)

        inflate.recycleView.layoutManager = layoutManager
    }

    private fun initFruits() {
        val n = (20..40).random()
        repeat(n) {
            //橫向佈局要實現layoutManager.orientation=LinearLayoutManager.HORIZONTAL，高度自適應，寬度80dp
            list.add("Apple")
            list.add("Banana")
            list.add("Watermelon")
            list.add("pear")
            list.add("Grape")
            list.add("Pineapple")
            list.add("Strawberry")
            list.add("Cherry")
            list.add("Mango")
        }

    }
}