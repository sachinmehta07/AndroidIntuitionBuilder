package com.app.kotlinbasicslearn.roomdb

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.app.kotlinbasicslearn.databinding.ItemPersonBinding
import com.app.kotlinbasicslearn.roomdb.model.Person

class PersonAdapter(
    private val onEdit: (Person) -> Unit,
    private val onDelete: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private var people = listOf<Person>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newListOfPeople: List<Person>) {
        people = newListOfPeople
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemBinding =
            ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return people.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.txtName.text = person.personName
        holder.txtGender.text = person.personGender
        person.personAge.also { it ->
            it.toString().also { holder.txtAge.text = it }
        }

        holder.btnEdit.setOnClickListener {
            onEdit(person)
        }
        holder.btnDelete.setOnClickListener {
            onDelete(person)
        }
    }


    inner class PersonViewHolder(itemBinding: ItemPersonBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val txtName = itemBinding.txtName
        val txtAge = itemBinding.txtAge
        val txtGender = itemBinding.txtGender
        val btnEdit = itemBinding.btnEdit
        val btnDelete = itemBinding.btnDelete

    }
}