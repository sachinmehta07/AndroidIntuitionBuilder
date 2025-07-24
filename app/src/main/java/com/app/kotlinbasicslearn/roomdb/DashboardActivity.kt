package com.app.kotlinbasicslearn.roomdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.kotlinbasicslearn.databinding.ActivityDashboardAcivityBinding
import com.app.kotlinbasicslearn.roomdb.model.AppDatabase
import com.app.kotlinbasicslearn.roomdb.model.Person
import com.app.kotlinbasicslearn.roomdb.model.PersonDao
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private val TAG = "DashboardAcivity"

    private lateinit var db: AppDatabase

    private lateinit var personDao: PersonDao

    private lateinit var adapter: PersonAdapter

    private var editPerson: Person? = null

    private lateinit var binding: ActivityDashboardAcivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAcivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        personDao = db.personDao()

        adapter = PersonAdapter(

            onEdit = { person ->
                binding.run {
                    etName.setText(person.personName)
                    etGender.setText(person.personGender)
                    etAge.setText(person.run { personAge.toString() })
                }
                editPerson = person
            }, onDelete = { person ->
                lifecycleScope.launch {
                    personDao.delete(person)
                }
            })

//        lifecycleScope.launch {
//            val data = personDao.getAllCoursesAsc() // since it's not LiveData
//            adapter.submitList(data)
//        }

        //This will auto-update the list every time the DB changes — no manual fetching!

        personDao.getAllCoursesAsc().observe(this) { peopleList ->
            adapter.submitList(peopleList)
        }

        binding.recyclerView.adapter = adapter



        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?: return@setOnClickListener
            val gender = binding.etGender.text.toString()
            if (editPerson != null) {
//                val updated =
//                    editPerson!!.copy(personName = name, personAge = age, personGender = gender)
                lifecycleScope.launch {
                    personDao.update(   Person(
                        id = editPerson!!.id,
                        personName = name,
                        personAge = age,
                        personGender = gender
                    ))
                }
                editPerson = null
                binding.etName.text.clear()
                binding.etAge.text.clear()
                binding.etGender.text.clear()
            } else {

                lifecycleScope.launch {
                    personDao.insert(
                        Person(
                            personName = name,
                            personAge = age,
                            personGender = gender
                        )
                    )
                }

                binding.etName.text.clear()
                binding.etAge.text.clear()
                binding.etGender.text.clear()
            }


        }


    }
}