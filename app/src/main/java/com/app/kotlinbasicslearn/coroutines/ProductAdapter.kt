package com.app.kotlinbasicslearn.coroutines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.coroutines.retrofit.Product
import com.app.kotlinbasicslearn.databinding.ItemProductBinding

class ProductAdapter(
    val onEdit: (Product) -> Unit,
    val onDelete: (Product) -> Unit,
) : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffUtil) {

    inner class ViewHolder(private val itemBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Product) {
            itemBinding.tvTitle.text = item.title
            item.price.toString().let {
                itemBinding.tvPrice.text = it
            }
            itemBinding.btnEdit.setOnClickListener {
                onEdit(item)
            }
            itemBinding.btnDelete.setOnClickListener {
                onDelete(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffUtil =
            object : androidx.recyclerview.widget.DiffUtil.ItemCallback<Product>() {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem == newItem
                }
            }
    }


}