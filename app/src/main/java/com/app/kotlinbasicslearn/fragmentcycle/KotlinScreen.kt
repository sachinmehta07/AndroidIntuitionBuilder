package com.app.kotlinbasicslearn.fragmentcycle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityMain2Binding

class KotlinScreen : AppCompatActivity(), KotlinChatFrg.OnResultPass {

    private lateinit var binding: ActivityMain2Binding

    private var currSelectedItemId = R.id.navHome
    private var sample = "Sample"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()


        binding = ActivityMain2Binding.inflate(layoutInflater)

        setContentView(binding.getRoot())

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        if (savedInstanceState == null) {
            loadFrag(HomeKotlinFrag())
        }

        binding.bnBarHomeScreen.setOnItemSelectedListener { item ->

            val itemID = item.itemId

            if (itemID == currSelectedItemId) {
                return@setOnItemSelectedListener true
            }

            val selectedFrg = when (itemID) {
                R.id.navHome -> HomeKotlinFrag()

                R.id.navChat -> KotlinChatFrg()

                else -> null
            }

            selectedFrg?.let {
                loadFrag(it)
                Toast.makeText(this, sample, Toast.LENGTH_SHORT).show()
                currSelectedItemId = itemID
                return@setOnItemSelectedListener true
            }

            return@setOnItemSelectedListener false
        }

        onBackPressedDispatcher.addCallback(this) {
            if (currSelectedItemId == R.id.navChat) {
                binding.bnBarHomeScreen.selectedItemId = R.id.navHome
            } else if (currSelectedItemId == R.id.navHome) {
                finish()
            }
        }
    }

    private fun normalDataPass(): Bundle {
        val bundle = Bundle().apply {
            putString("user", "sachin")
            putInt("age", 23)
            putBoolean("isStudent", true)
        }
        return bundle
    }

    private fun loadFrag(currFrg: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (currFrg is KotlinChatFrg) {
            currFrg.arguments = normalDataPass()
        } else {
            currFrg.arguments = Bundle().apply { putString("data", sample) }
        }
        transaction.replace(binding.flHomeScreenMain.id, currFrg)
        transaction.commit()
    }

    override fun onResultPass(data: String) {
        sample = data
        Toast.makeText(this, "Fragment said: $data", Toast.LENGTH_SHORT).show()
    }


}