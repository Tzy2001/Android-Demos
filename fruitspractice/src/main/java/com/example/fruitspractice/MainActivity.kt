package com.example.fruitspractice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruitspractice.Adapter.FruitsAdapter
import com.example.fruitspractice.Adapter.PrjFailedPrintPositionAdapter
import com.example.fruitspractice.Bean.Fruits
import com.example.fruitspractice.databinding.ActivityMainBinding
import com.yl.recyclerview.wrapper.DragAndDropWrapper
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private val fruitList = ArrayList<Fruits>()
    private val inflate by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflate.root)
        inflate.tvPrjRecordPrintOperation.apply {
        }
//        inflate.tvPrjRecordPrintOperation.textSize= 20F
        inflate.tvPrjRecordPrintOperation.setPadding(0,50,0,0)
//        initFruits()
//        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        val layoutManager = LinearLayoutManager(this)
////        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
//        inflate.recycleView.layoutManager = layoutManager
//        val adapter = FruitsAdapter(fruitList)
//        PrjFailedPrintPositionAdapter(adapter)
//        val mDragAndDropWrapper = DragAndDropWrapper(adapter, fruitList, 200)
//        inflate.recycleView.adapter = mDragAndDropWrapper
    }


    private fun initFruits() {
        val n = (20..40).random()
        repeat(n) {
            //橫向佈局要實現layoutManager.orientation=LinearLayoutManager.HORIZONTAL，高度自適應，寬度80dp
            fruitList.add(Fruits("Apple", R.drawable.apple_pic))
            fruitList.add(Fruits("Banana", R.drawable.banana_pic))
            fruitList.add(Fruits("Orange", R.drawable.orange_pic))
            fruitList.add(Fruits("Watermelon", R.drawable.watermelon_pic))
            fruitList.add(Fruits("pear", R.drawable.pear_pic))
            fruitList.add(Fruits("Grape", R.drawable.grape_pic))
            fruitList.add(Fruits("Pineapple", R.drawable.pineapple_pic))
            fruitList.add(Fruits("Strawberry", R.drawable.strawberry_pic))
            fruitList.add(Fruits("Cherry", R.drawable.cherry_pic))
            fruitList.add(Fruits("Mango", R.drawable.mango_pic))
            //瀑佈式佈局要實現val layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)傳入列數為3列縱向佈局
//            fruitList.add(Fruits(getRandomLengthString("apple"), R.drawable.apple_pic))
//            fruitList.add(Fruits(getRandomLengthString("Banana"), R.drawable.banana_pic))
//            fruitList.add(Fruits(getRandomLengthString("Orange"), R.drawable.orange_pic))
//            fruitList.add(Fruits(getRandomLengthString("Watermelon"), R.drawable.watermelon_pic))
//            fruitList.add(Fruits(getRandomLengthString("pear"), R.drawable.pear_pic))
//            fruitList.add(Fruits(getRandomLengthString("Grape"), R.drawable.grape_pic))
//            fruitList.add(Fruits(getRandomLengthString("Pineapple"), R.drawable.pineapple_pic))
//            fruitList.add(Fruits(getRandomLengthString("Strawberry"), R.drawable.strawberry_pic))
//            fruitList.add(Fruits(getRandomLengthString("Cherry"), R.drawable.cherry_pic))
//            fruitList.add(Fruits(getRandomLengthString("Mango"), R.drawable.mango_pic))
        }

    }

    private fun getRandomLengthString(str: String): String {
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n) {
            builder.append(str)
        }
        return builder.toString()
    }
}