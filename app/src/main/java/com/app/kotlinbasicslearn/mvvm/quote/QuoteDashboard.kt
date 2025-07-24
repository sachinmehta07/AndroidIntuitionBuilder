package com.app.kotlinbasicslearn.mvvm.quote

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityQouteDashboardBinding
import com.app.kotlinbasicslearn.mvvm.quote.data.Quote
import com.app.kotlinbasicslearn.mvvm.quote.modelfactory.QuoteViewModelFactory
import com.app.kotlinbasicslearn.mvvm.quote.viewmodel.QuoteViewModel

class QuoteDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityQouteDashboardBinding
    private lateinit var viewModel: QuoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qoute_dashboard)
//        setContentView(binding.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        viewModel = ViewModelProvider(
            this,
            QuoteViewModelFactory(application)
        )[QuoteViewModel::class.java]

        binding.quote = viewModel.getQuote()

        //    setQuote(viewModel.getQuote())

//        binding.btnNext.setOnClickListener {
//            setQuote(viewModel.nextQuote())
//        }
//
//        binding.btnPrevious.setOnClickListener {
//            setQuote(viewModel.previousQuote())
//        }


    }


    private fun setQuote(quote: Quote) {

        quote.text.let {
            binding.txQuote.text = it
        }

        quote.author.let {
            binding.txAuthor.text = it
        }
    }

}