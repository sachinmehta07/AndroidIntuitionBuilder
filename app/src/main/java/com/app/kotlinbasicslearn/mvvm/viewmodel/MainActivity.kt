package com.app.kotlinbasicslearn.mvvm.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityMain3Binding
import com.app.kotlinbasicslearn.mvvm.factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var viewModel: MainViewModel


    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3)

        viewModel = ViewModelProvider(this, MainViewModelFactory(10))[
            MainViewModel::class.java]

//        viewModel.count.toString().let {
//            binding.txCount.text = it
//        }

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

//        viewModel.countLiveData.observe(this) {
//            binding.edtCount.setText(String.format(it.toString()))
//            binding.txCount.text = String.format(it.toString())
//        }


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.btnUpdate.setOnClickListener {
            val newCount = binding.edtCount.text.toString().toInt()
            viewModel.updateCountLiveData(newCount)
        }
//
//        binding.btnIncrease.setOnClickListener {
//
//            //withViewModel
//
//            viewModel.increaseCount()
//            viewModel.count.toString().let {
//                binding.txCount.text = it
//            }
//
////            //Without ViewModel
////            increaseCount()
////            count.toString().let {
////                binding.txCount.text = it
////            }
//
//        }
//        binding.btnDecrease.setOnClickListener {
//
//            //with View model
//
//            viewModel.decreaseCount()
//            viewModel.count.toString().let {
//                binding.txCount.text = it
//            }
//
//            //Without ViewModel
////            decreaseCount()
////            count.toString().let {
////                binding.txCount.text = it
////            }
//        }

    }


//    private fun increaseCount() {
//        count++
//    }
//
//    private fun decreaseCount() {
//        if (count > 0) {
//            count--
//        }
//    }
}