package com.app.kotlinbasicslearn.wallpaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ItemWallpaperBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class WallpaperAdapter(private val images: List<WallpaperItem>) :
    RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemWallpaperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WallpaperItem) {
            binding.txvId.text = item.id
            Glide.with(binding.ivWallpaper.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivWallpaper)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //for view
//            LayoutInflater.from(parent.context).inflate(R.layout.item_wallpaper, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }


}