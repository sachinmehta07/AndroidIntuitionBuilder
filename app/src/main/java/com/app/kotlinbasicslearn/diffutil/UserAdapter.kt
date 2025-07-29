package com.app.kotlinbasicslearn.diffutil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.databinding.ItemPersonBinding

class UserAdapter(
    val onEditClick: (UserData) -> Unit,
    val onDeleteClick: (UserData) -> Unit
) : ListAdapter<UserData, UserAdapter.ViewHolder>(DiffUtil()) {

    inner class ViewHolder(private val itemBinding: ItemPersonBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: UserData) {
            data.age.toString().let {
                itemBinding.txtAge.text = it
            }
            itemBinding.btnEdit.setOnClickListener {
                onEditClick(data)
            }
            itemBinding.btnDelete.setOnClickListener {
                onDeleteClick(data)
            }
            itemBinding.txtName.text = data.name
            itemBinding.txtGender.text = data.gender
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }

    }

}