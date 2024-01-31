package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.StaffAdapter
import com.example.myapplication.bean.StaffsBean
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityRecycleviewBinding


class StaffsManageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 查找 RecyclerView
        val inflate = ActivityRecycleviewBinding.inflate(layoutInflater)
        setContentView(inflate.root)


        // 创建数据集合
        val staffList = listOf(
            StaffsBean("张三", "外卖员", "137****1234"),
            StaffsBean("李四", "營收员", "150****5678")
        )

        // 创建 Adapter 并设置给 RecyclerView
        val adapter = StaffAdapter(staffList)
        inflate.rvStaffs.adapter = adapter

// 设置 LayoutManager
        inflate.rvStaffs.layoutManager = LinearLayoutManager(this)
    }
}