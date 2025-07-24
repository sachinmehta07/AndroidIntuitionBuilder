package com.app.kotlinbasicslearn.scopfunctions

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.ActivityScopFunctionBinding
import kotlin.math.log

class ScopFunctionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScopFunctionBinding
    private lateinit var listUser: MutableList<PersonData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityScopFunctionBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // Initialize sample data
        listUser = mutableListOf(
            PersonData("Sachin", 25, "Male"),
            PersonData("Priya", 22, "Female"),
            PersonData("Aman", 30, "Male")
        )


    }

    private fun exampleLet(): String {
        val person: PersonData? = listUser.find { it.name == "Sachin" }

        person?.let {
//            Log.d("LET", "Name: ${it.name}, Age: ${it.age}")
            return it.name
        }

        return ""
    }

    private fun exampleAlso() {
        val newUser = PersonData("Rajesh", 35, "Male").also {
            Log.d("ALSO", "ALSO new user : ${it.name} ")
        }
    }

    private fun exampleApply() {

        //if obj are var
//        val newUser = PersonData(name = "",age = 0 , gender = "").apply {
//            name = "Rajesh"
//            age = 35
//            gender = "Male"
//        }

//        if  val
        val newUser = PersonData(name = "", age = 0, gender = "").copy(
            name = "Rajesh",
            age = 35,
            gender = "Male"
        )

    }


    // ✅ 4. run - Compute something from an object and return a result
    private fun exampleRun(): Int {
        //When you need to perform operations on an object and return a result.
        val user = PersonData("Sachin", 25, "Male")
        val isAdult = user.run {
            age + 18
        }
        return isAdult
    }

    // ✅ 5. with - Perform multiple operations on a non-null object
    private fun exampleWith() {
        val user = listUser[1]

        val summary = with(user) {
            "Summary: $name is a $gender aged $age"
        }

        Log.d("WITH", summary)
    }


}


