package com.app.kotlinbasicslearn.diffutil

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private lateinit var userAdapter: UserAdapter

    private val userList = MutableLiveData<MutableList<UserData>>()

    private var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(
            onEditClick = { userData ->
                Toast.makeText(this, userData.id, Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { userData ->
                Toast.makeText(this, userData.id, Toast.LENGTH_SHORT).show()
            })

        binding.btnAdd.setOnClickListener {
            if (binding.etName.text.toString().isEmpty() || binding.etAge.text.toString()
                    .isEmpty() || binding.etGender.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                insertData(
                    binding.etName.text.toString(),
                    binding.etAge.text.toString().toInt(),
                    binding.etGender.text.toString()
                )
            }
        }

        userList.observe(this) {
            userAdapter.submitList(userList.value)
        }

        binding.recyclerView.adapter = userAdapter
    }

    private fun insertData(name: String, age: Int, gender: String) {
//        userList.value?.add(UserData(counter++, name, age, gender))

        val currList = userList.value ?: mutableListOf();
        val newList = currList.toMutableList()
        newList.add(UserData(counter++, name, age, gender))
        userList.value = newList

    }
}