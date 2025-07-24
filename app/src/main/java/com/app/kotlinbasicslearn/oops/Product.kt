//package com.app.kotlinbasicslearn.oops
//
//class Product {
//
//    var price: Int = 0
//        set(value) {
//            if (value >= 0) {
//                field = value
//            } else {
//                println("Invalid")
//            }
//        }
//
//}
//
////for custom setter nd getter for extra logic
//class User(private var _age: Int) {
//
//    //public getter
//    val age: Int
//        get() = _age
//
//    //public setter with validation
//    fun updateAge(newAge: Int) {
//        if (newAge in 0..150) {
//            _age = newAge
//        } else {
//            println("Invalid")
//        }
//    }
//}
//
//class UserPrivate() : Vehicle(){
//
//    //public getter
//    var age: Int = 0
//        set(value) {
//            if (value >= 0) {
//                field = value
//            } else {
//                println("invalid")
//            }
//        }
//
//    override fun startEngine() {
//        println("need to do start")
//        TODO("Not yet implemented")
//    }
//
//    override fun stopEngine() {
//        println("need to do stop")
//        TODO("Not yet implemented")
//    }
//}