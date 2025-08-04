package com.app.kotlinbasicslearn.workmanager

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.ProductApplication
import com.app.kotlinbasicslearn.databinding.ActivityStockNewsBinding
import com.app.kotlinbasicslearn.workmanager.utils.ConnectionState
import com.app.kotlinbasicslearn.workmanager.utils.Resources
import com.app.kotlinbasicslearn.workmanager.viewmodel.NewsModel
import com.app.kotlinbasicslearn.workmanager.viewmodel.NewsViewModelFactory

class StockNewsActivity : AppCompatActivity() {
    private lateinit var newsStockViewModel: NewsModel
    private lateinit var newsAdapter: StockNewsAdapter
    private lateinit var binding: ActivityStockNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        checkInternetConnection()

    }

    private fun checkInternetConnection() {
        newsStockViewModel.connectionState.observe(this) { isConnected ->
            when (isConnected) {
                is ConnectionState.Available -> setupRecyclerView()

                is ConnectionState.Unavailable -> showToast("No internet connection")
            }
        }
    }

    private fun setupViewModel() {

        val repository = (application as ProductApplication).NewsRepository

        newsStockViewModel =
            ViewModelProvider(this, NewsViewModelFactory(this, repository))[NewsModel::class.java]

    }

    private fun setupRecyclerView() {


        newsStockViewModel.stockNews.observe(this) { resources ->

            when (resources) {

                Resources.Loading -> showProgressBar(true)

                is Resources.Success -> {
                    newsAdapter = StockNewsAdapter(resources.data)
                    showProgressBar(false)
                    binding.rvNews.adapter = newsAdapter
                }

                is Resources.Error -> {
                    showProgressBar(false)
                    showToast(resources.message)
                }


            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}