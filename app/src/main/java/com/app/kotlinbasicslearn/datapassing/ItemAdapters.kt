package com.app.kotlinbasicslearn.datapassing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.User
import com.app.kotlinbasicslearn.databinding.ItemListBinding

class ItemAdapters(
    private val userList: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<ItemAdapters.ItemViewHolder>() {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
//        return ItemViewHolder(view)
//    }

    private val selectedItems = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        //viewHolder with binding class

        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //viewHolder with binding class

        holder.bind(userList[position])


        //view holder with view class
//        val user = userList[position]
//        holder.name.text = user.name
//        user.age.toString().also { holder.age.text = it }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

//with binding class


    inner class ItemViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {

            binding.nameText.text = user.name
            user.age.toString().also { binding.priceText.text = it }


            val isSelected = selectedItems.contains(user)

            binding.root.setBackgroundResource(
                if (isSelected) R.color.chat_color_sender else R.color.trans
            )

            binding.root.setOnClickListener {
                onItemClick(user)
            }

            binding.root.setOnClickListener {

                if (isSelected) {
                    selectedItems.remove(user)
                } else {
                    selectedItems.add(user)
                }

                notifyItemChanged(adapterPosition) //refreshes item only

                onItemClick(user)

            }
        }
    }

    //with view class

//    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val name: TextView = itemView.findViewById(R.id.nameText)
//        val age: TextView = itemView.findViewById(R.id.priceText)
//    }

}