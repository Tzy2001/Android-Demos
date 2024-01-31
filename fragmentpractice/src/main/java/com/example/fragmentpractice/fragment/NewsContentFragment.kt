package com.example.fragmentpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.fragmentpractice.R
import com.example.fragmentpractice.databinding.NewsContentFragBinding

class NewsContentFragment :Fragment(){
  /*  private val inflate by lazy { NewsContentFragBinding.inflate(layoutInflater)}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_content_frag, container, false)
    }
    fun refresh(title:String,content:String){
        inflate.contentLayout.visibility=View.VISIBLE
        inflate.newsTitle.text=title
        inflate.newsContent.text=content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setContentView(inflate.root)
    }*/
}