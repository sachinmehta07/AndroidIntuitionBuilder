package com.app.kotlinbasicslearn.datapassing

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.kotlinbasicslearn.MainActivity
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.User
import com.app.kotlinbasicslearn.databinding.ActivityDataPassingLearningBinding

class DataPassingLearning : AppCompatActivity() {

    private lateinit var binding: ActivityDataPassingLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge()

        //normal intent

        binding = ActivityDataPassingLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        //normal intent
//        getBundleUsage()

        getDataUsingParcelable()


    }

    private fun getDataUsingParcelable() {

        val userList = intent.getParcelableArrayListExtra<User>("user")

        binding.rvUser.adapter = userList?.let { ItemAdapters(it) {
            user ->
            Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
        } }


    }

//    private fun normalIntent() {
//        binding.nameText.text = intent.getStringExtra("name")
//        binding.priceText.text = intent.getStringExtra("price")
//        binding.detailsText.text = iitntent.getStringExtra("details")
//    }

//    private fun getBundleUsage() {
//
//        val bundle = intent.extras
//
//        if (bundle != null) {
//            binding.nameText.text = bundle.getString("name")
//            bundle.getInt("price").toString().also { binding.priceText.text = it }
//            binding.detailsText.text = bundle.getString("details")
//        }
//
//    }


}