package com.app.kotlinbasicslearn.workmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ItemNewsListBinding
import com.app.kotlinbasicslearn.workmanager.api.models.StockNewsItem
import com.bumptech.glide.Glide

class StockNewsAdapter(private val newsList: List<StockNewsItem>) :

    RecyclerView.Adapter<StockNewsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    inner class ViewHolder(val binding: ItemNewsListBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(stockNewsItem: StockNewsItem) {

            Glide.with(binding.imageNews.context).load(stockNewsItem.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageNews)

            binding.textTitle.text = stockNewsItem.title
            binding.textSummary.text = stockNewsItem.summary
            binding.textSource.text = stockNewsItem.source
            binding.textDate.text = stockNewsItem.pubDate
            binding.textTopics.text = stockNewsItem.topics.joinToString(", ")
        }
    }

}