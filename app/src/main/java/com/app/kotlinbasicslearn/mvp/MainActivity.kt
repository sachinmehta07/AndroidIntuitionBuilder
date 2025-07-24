package com.app.kotlinbasicslearn.mvp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.User
import com.app.kotlinbasicslearn.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain4Binding
    private lateinit var presenter: UserPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = UserPresenter(object : UserViewInterface {
            override fun showUser(user: com.app.kotlinbasicslearn.mvp.User) {
                binding.tvResult.text = "Name: ${user.name}, Age: ${user.age}"
            }
        }
        )

        //        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty()) {
                presenter.saveUser(name, age)
            }
        }

    }


}