package com.app.kotlinbasicslearn.example.myapp

fun buildAquarium() {

//    val myAquarium = Aquarium() // create Aqm Obj
//    myAquarium.printSize()
//    myAquarium.height = 586

//    println("volume fresh" + myAquarium.volume)
//    myAquarium.printSize()

//    val aqm = Aquarium(50, 60)
//    println("cons value")
//    aqm.printSize()

    val myAquariumA = Aquarium(width = 25, length = 25, height = 40)
    myAquariumA.printSize()

    //wanted to be known for something
    val myTower = TowerTank(diameter = 25, height = 40)
    myTower.printSize()

}

//secondary function demo
fun eComExample() {
    val orderOne = OrderDetails("Sachin", listOf("Iphone", "Charger"), 120000.0)
    orderOne.printOrderDetails()

    //normal
    val order2 = OrderDetails("Rahul", "Happy Buday", 500.00)
    order2.printOrderDetails()

    //factory method used in real prod level
    val order3 = OrderDetails.createGiftOrder("Rahul", "Happy Buday", 500.00)
}

fun main() {
//    buildAquarium() //call the fun to build Aqm
//    eComExample()
//    makeFish()
    dataClassDemo()
}

fun makeFish() {

    val shark = Shark()
    val miniFish = MiniFish()

    println("SHARK COLOR: ${shark.color}")
    shark.eat()
    println("CAN SHARK KILL? ${shark.canKill(true)}")

    println()

    println("MINIFISH COLOR: ${miniFish.color}")
    miniFish.eat()
    println("CAN MINIFISH KILL? ${miniFish.canKill(false)}")


}

//mimic the pojo of java in kotlin
fun dataClassDemo() {

    val listDemo = mutableListOf<UserData>()

    listDemo.add(UserData("Sachin", 1))
    listDemo.add(UserData("Karan", 2))
    listDemo.add(UserData("Harsh", 3))
    listDemo.add(UserData("Raj", 4))

    for ((userName, userId) in listDemo) {
        println("$userName,$userId")
    }

    //replacing .. with until will go including last size which will throw index out of bound
    for (i in 0 until listDemo.size) {
        println("$i , : ${listDemo[i]}")
    }

}