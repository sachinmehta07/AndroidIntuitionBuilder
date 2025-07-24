package com.app.kotlinbasicslearn.example.myapp

class TowerTank(override var height: Int, private var diameter: Int) :
    Aquarium(length = diameter, width = diameter, height = height) {

    override val shape = "cylinder"
    override var volume: Int = ((diameter/2.0 * diameter/2.0 * height * 3.14) / 1000).toInt()
    override var water = volume * 0.8


}