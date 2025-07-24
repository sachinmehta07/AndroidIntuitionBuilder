package com.app.kotlinbasicslearn.activitycycle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityKotlinExampleBinding

class KotlinExample : AppCompatActivity() {

    private val tag = "kotlinExample"

    private val tracker = Analytical()

    private lateinit var binding: ActivityKotlinExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityKotlinExampleBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycle.addObserver(tracker)

        val name = savedInstanceState?.getString("save_name")

        name?.let {
            binding.tvDisplay.text = it
            binding.etName.setText(getString(R.string.hello, it))
        }

    }

    //save data before Activity get destroyed
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("save_name", binding.etName.text.toString())

    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        binding.txv.text = "on resume"
        Log.d(tag, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        binding.txv.text = "on pause"
        Log.d(tag, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        binding.txv.text = "on Stop"
        Log.d(tag, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy: ")
    }

}