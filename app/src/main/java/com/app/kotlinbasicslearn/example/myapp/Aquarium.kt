package com.app.kotlinbasicslearn.example.myapp

//open keyword help to make class inheritable
open class Aquarium(
    open var width: Int = 20,
    open var height: Int = 40,
    open var length: Int = 100
) {

    open val shape = "rectangle"


    open var volume: Int
        get() = width * height * length / 1000 //every time u ask fr volume -> it calculate fresh
        set(value) {
            height =
                (value * 1000) / (width * length) // every time you set volume → it auto adjusts height
        }

    open val water = volume * 0.9


    init {
        println("Volume: ${width * length * height / 1000} liters")
    }

    fun printSize() {
        println(shape)

//        println("Width: $width cm Length: $length cm Height: $height cm")
        println("volume : $volume , water : $water")
    }


}




