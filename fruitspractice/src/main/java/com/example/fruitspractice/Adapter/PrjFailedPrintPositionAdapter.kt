package com.example.fruitspractice.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitspractice.Bean.Fruits
import com.example.fruitspractice.R

class PrjFailedPrintPositionAdapter(val list:ArrayList<String>) :
    RecyclerView.Adapter<PrjFailedPrintPositionAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnPrintFailed = itemView.findViewById<Button>(R.id.btn_print_failed)
    }
//    override fun convert(holder: BaseViewHolder, item: PrjBean) {
//        if (item.printerType == UpdateBean.FAILED_PRINT) {
//            holder.setText(android.R.id.btn_print_failed, item.printPosition)
//        }
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_print_failed, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.btnPrintFailed.text = data
    }


}
