package com.example.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CreateStaffActivity
import com.example.myapplication.EditStaffActivity
import com.example.myapplication.R
import com.example.myapplication.bean.StaffsBean
import com.example.myapplication.databinding.ActivityRecycleviewBinding


class StaffAdapter(private val staffList: List<StaffsBean>) : RecyclerView.Adapter<StaffAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_staffs_manage, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val staff = staffList[position]
        holder.nameView.text = staff.name
        holder.positionView.text = staff.position
        holder.phoneView.text = staff.phone
        holder.edit.setOnClickListener {
            // 在此处使用 holder 对象获取 ib_staff_edit 这个 View
            val editView = holder.edit

            // 获取该按钮所在的 Activity 的上下文
            val context = editView.context

            // 启动目标 Activity
            val intent = Intent(context, EditStaffActivity::class.java)
            context.startActivity(intent)
        }
        holder.insert.setOnClickListener{
            val insertView = holder.insert
            val context = insertView.context
           val intent = Intent(context,CreateStaffActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.tv_staff_name)
        val positionView: TextView = itemView.findViewById(R.id.tv_staff_possion)
        val phoneView: TextView = itemView.findViewById(R.id.tv_staff_phone)
        val edit: ImageButton = itemView.findViewById(R.id.ib_staff_edit)
        val insert: ImageButton = itemView.findViewById(R.id.ib_staff_insert)

    }
}