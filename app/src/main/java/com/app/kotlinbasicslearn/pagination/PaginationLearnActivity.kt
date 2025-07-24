package com.app.kotlinbasicslearn.pagination

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityPaginationLearnBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaginationLearnActivity : AppCompatActivity() {

    private lateinit var paginationBinding: ActivityPaginationLearnBinding
    private lateinit var paginationAdapter: PaginationAdapter
    private var isLoading = false
    private val numbers = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        paginationBinding = ActivityPaginationLearnBinding.inflate(layoutInflater)
        setContentView(paginationBinding.root)



        for (i in 0..9) {
            numbers.add("Item $i")
        }


        paginationAdapter = PaginationAdapter(numbers)

        paginationBinding.rvPagination.adapter = paginationAdapter

        paginationBinding.rvPagination.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //pagination logic

                //to get scroll position we need layout manager

                val layManager = recyclerView.layoutManager as LinearLayoutManager

                //to get the pos of last visible item in screen

                //findLastCompletelyVisibleItemPosition >> fully item is visible not just bit pixel or peeking from bottom

//                findLastVisibleItemPosition() >> even slightest visibility of item will be counted
                val lastVisibleItem = layManager.findLastVisibleItemPosition()

                //simply return the total num of items in rv
                val totalItemVisible = layManager.itemCount

                Log.d(
                    "onScrolled:",
                    "lastVisibleItem: $lastVisibleItem totalItemVisible: $totalItemVisible "
                )

                if (!isLoading && lastVisibleItem >= totalItemVisible - 4) {
                    isLoading = true
                    loadData()
                }

            }

        })

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

    }

    private fun loadData() {
        paginationBinding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(1000)
            val newNum = mutableListOf<String>()
            for (i in numbers.size ..numbers.size + 9) {
                newNum.add("Item $i")
            }
            paginationBinding.progressBar.visibility = View.GONE
            numbers.addAll(newNum)
            paginationAdapter.notifyItemRangeInserted(numbers.size - newNum.size, newNum.size)
            isLoading = false

        }
    }
}