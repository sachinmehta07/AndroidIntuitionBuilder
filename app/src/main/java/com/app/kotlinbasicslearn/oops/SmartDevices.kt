package com.app.kotlinbasicslearn.oops

//Class basic template
class SmartDevices(var deviceName: String, val deviceCategory: String, val age: Int) {


    init {
        //Extra code during object creation

        //if i have to do validation


        require(age <= 18) { "age is less than 18" }

        println("class started")
    }

//    constructor(name: String, category: String, statusCode: Int) : this(name, category) {
//        this.deviceName = name
//    }


    val name = deviceName
    val category = deviceCategory


    var deviceStatus = "online"

    var speakerVolume = 1
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }

    //methods
    fun turnOn() {
        println("Smart Device Turned on.")

    }

    fun turnOff() {
        println("Smart Device Turned off.")
    }


}