package com.app.kotlinbasicslearn.mvvm.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityDashboard2Binding
import com.app.kotlinbasicslearn.mvvm.sample.modelfactory.QuoteViewModelFactory
import com.app.kotlinbasicslearn.mvvm.sample.repository.QuoteRepository
import com.app.kotlinbasicslearn.mvvm.sample.roomDB.AppDatabase
import com.app.kotlinbasicslearn.mvvm.sample.roomDB.Quote
import com.app.kotlinbasicslearn.mvvm.sample.viewmodel.QuoteViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboard2Binding
    private lateinit var quoteViewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard2)

        val dao = AppDatabase.getDb(this).quoteDao()

        val repo = QuoteRepository(dao)

        quoteViewModel =
            ViewModelProvider(this, QuoteViewModelFactory(repo))[QuoteViewModel::class.java]

        binding.btnAddQuote.setOnClickListener {
            val quote = binding.etQuote.text.toString()
            val author = binding.etAuthor.text.toString()

            if (quote.isNotEmpty() && author.isNotEmpty()) {
                quoteViewModel.insertQuote(Quote(0, quote, author))
            } else {
                Toast.makeText(this, "add quote and author", Toast.LENGTH_SHORT).show()
            }

        }

        quoteViewModel.getQuotes().observe(this) {
            binding.tvQuotes.text = it.toString()
        }


    }
}