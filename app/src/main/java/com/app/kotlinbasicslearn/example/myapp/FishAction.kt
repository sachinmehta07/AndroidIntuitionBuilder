package com.app.kotlinbasicslearn.example.myapp

interface FishAction {

    //giving behaviour to multiple class

    fun eat()

    fun canKill(boolean: Boolean): Boolean

}

interface FishColor {
    val color: String
}