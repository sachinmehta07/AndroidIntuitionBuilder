package com.app.kotlinbasicslearn

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.kotlinbasicslearn.databinding.ActivityMainBinding
import com.app.kotlinbasicslearn.datapassing.DataPassingLearning
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val mutableList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userList = listOf("Sachin", "Rahul", "Dravid", "Kohli")

        for (i in 1..userList.size) {
            mutableList.add(User(userList[i - 1], i))
        }

        binding.btnSample.setOnClickListener {
            dataObjectTransfer()
        }
    }

    private fun dataObjectTransfer() {
        val intent = Intent(this, DataPassingLearning::class.java)
        intent.putParcelableArrayListExtra("user", ArrayList(mutableList))
        startActivity(intent)
    }

    //normal intent starting activity
    private fun normalIntent() {

        val intent = Intent(this, DataPassingLearning::class.java)

        intent.putExtra("name", "sachin")
        intent.putExtra("price", "150")
        intent.putExtra("details", "gems")

        startActivity(intent)
    }

    //bundle intent starting activity
    private fun bundleIntent() {

        val bundle = Bundle().apply {

            putString("name", "sachin")
            putInt("price", 150)
            putString("details", "gems")

        }

        val iNxt = Intent(this, DataPassingLearning::class.java)
        iNxt.putExtras(bundle)
        startActivity(iNxt)

    }
}




