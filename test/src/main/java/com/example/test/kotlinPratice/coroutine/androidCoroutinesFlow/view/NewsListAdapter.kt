package com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.test.databinding.ItemNewsArticleBinding
import com.example.test.kotlinPratice.coroutine.androidCoroutinesFlow.model.NewsArticle

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {
    private val newsItems = arrayListOf<NewsArticle>()

    fun onAddNewsItem(item: NewsArticle) {
        newsItems.add(0, item)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListAdapter.NewsItemViewHolder {
        return NewsItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsListAdapter.NewsItemViewHolder, position: Int) {
        holder.bind(newsItems[position])
    }

    override fun getItemCount()=newsItems.size

    class NewsItemViewHolder(private val binding: ItemNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.newsImage
        private val author = binding.newsAuthor
        private val title = binding.newsTitle
        private val publishedAt = binding.newsPublishedAt

        fun bind(newsItem: NewsArticle) {
            imageView.loadImage(newsItem.urlToImage)
            author.text = newsItem.author
            title.text = newsItem.title
            publishedAt.text = newsItem.publishedAt
        }

        companion object {
            fun from(parent: ViewGroup): NewsItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsArticleBinding.inflate(layoutInflater, parent, false)
                return NewsItemViewHolder(binding)
            }
        }

    }

}