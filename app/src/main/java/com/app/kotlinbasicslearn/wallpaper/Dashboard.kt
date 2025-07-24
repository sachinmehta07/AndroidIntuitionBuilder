package com.app.kotlinbasicslearn.wallpaper

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityDashboardBinding
import com.app.kotlinbasicslearn.wallpaper.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val imageList = mutableListOf<WallpaperItem>()

    private lateinit var wallPaperAdapter: WallpaperAdapter

    private var isLoading = false

    private var endReached = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        //Setup rv

        wallPaperAdapter = WallpaperAdapter(imageList)

        binding.rvWallpaper.adapter = wallPaperAdapter

        loadImage()

        //load more onn scroll
        binding.rvWallpaper.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Cast the layout manager to LinearLayoutManager so we can get the scroll position
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                // Get the position of the last visible item on the screen
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Get the total number of items loaded in the RecyclerView
                val totalItemCount = layoutManager.itemCount


                // Check if:
                // - We're not already loading
                // - The user has scrolled close to the end (within 4 items of the bottom)
                if (!isLoading && !endReached && lastVisibleItem >= totalItemCount - 4) {
                    // Set loading flag to true to prevent duplicate calls
                    isLoading = true

                    // Trigger loading of next page / next batch of wallpapers
                    loadImage()
                }
            }
        })
    }


    private fun loadImage() {

        binding.progressBar.visibility = View.VISIBLE

        lifecycle.coroutineScope.launch {

            delay(1000)

            val newImages = withContext(Dispatchers.IO) {
                RetrofitClient.getWallpaper()
            }

            binding.progressBar.visibility = View.GONE

            if (newImages.isNotEmpty()) {
                imageList.addAll(newImages)
                wallPaperAdapter.notifyItemRangeInserted(
                    imageList.size - newImages.size,
                    newImages.size
                )
            } else {
                endReached = true // ✅ Stops future calls
            }

            isLoading = false
        }

    }
}