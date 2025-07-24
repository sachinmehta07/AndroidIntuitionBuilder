package com.app.kotlinbasicslearn.mvp

class UserPresenter(private val userView: UserViewInterface) {

    fun saveUser(name: String, age: String) {
        val ageInt = age.toIntOrNull() ?: 0
        val user = User(name, ageInt)
        userView.showUser(user)
    }

}
