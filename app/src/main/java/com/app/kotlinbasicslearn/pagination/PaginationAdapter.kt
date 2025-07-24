package com.app.kotlinbasicslearn.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.databinding.ItemPaginationBinding

class PaginationAdapter(private val numbers: List<String>) :
    RecyclerView.Adapter<PaginationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            ItemPaginationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(numbers[position])
    }

    inner class ViewHolder(val binding: ItemPaginationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.txvName.text = item
        }
    }

}