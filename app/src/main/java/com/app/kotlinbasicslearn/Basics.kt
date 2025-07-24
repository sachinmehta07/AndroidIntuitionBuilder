package com.app.kotlinbasicslearn

//fun main() {
////
////    val i: Int = 6
////    val tmp = 5
////    val b1 = i.toLong()
////    println(b1)
////
////    var jj = "sachin"
////
////    jj = "mk"
////
////    val indINS = 5
////    val pakIns = 2
////
////    println("India Has $indINS and pak has $pakIns")
////    println("India Has $indINS + $pakIns")
////
////    println("India Has ${indINS + pakIns} and pak has 0")
////
////    println("ENTER INDIA")
////    val numInd = readlnOrNull()
////    println("ENTER PAK")
////    val numPak = readlnOrNull()
////    if (numInd != null && numPak != null) {
////        if (numInd.toInt() > numPak.toInt()) {
////            println("INDIA KILLED ALL PEOPLE")
////        } else {
////            println("INDIA WON")
////        }
////
////        if (numInd.toInt() in 1..50) {
////            println("JAI HIND ")
////        }
////    }
////
////    var misInd: Int? = null
////
////    misInd = 50;
//
////    feedFish()
//    demoFilterList()
//}

fun listDemo() {


    //simple list
    val lovedPeople = listOf("kp", "jp", "np", "rp")
    print(lovedPeople)

    val cars = mutableListOf(5, 1, 1, 5, 5, 5, 5, 6)
    println(cars)
    cars.remove(5)
    println("After remove $cars")


    var school = arrayOf("batli", "mamta ji", "gajera")

    var typeWith = intArrayOf(5, 6, 4, 8, 8)

    val allList = listOf(school.contentToString(), typeWith.contentToString())
    println(allList)

    val arraySa = Array(5) { it * 1 }
    println(arraySa.contentToString())

    for ((idx, ele) in school.withIndex()) {
        println("at $idx ele $ele")
    }

//    for (i in 1..10 step 2) {
//        println(i)
//    }
//
//    for (i in 10 downTo 1) {
//        println(i)
//    }

//    Keyword	Meaning
//    ..	Forward loop (small → big)
//    downTo	Backward loop (big → small)
//    step	How much to move in each jump

}


class Demo(ss: String) {

    var count: Int = 10
    var saaA = ss

}

//practice of functions
fun feedFish() {
    val day = randomDay()
    val food = fishFood(day)
    println("today is $day and the the fish eat $food")
    println("should we change water : ${shouldChangeWater(day.toString(), 30)}")
}

fun fishFood(day: Any): String {

    var food: String? = null

    when (day) {
        "MON" -> food = "ROTI"
        "TUE" -> food = "DAL"
        "WED" -> food = "RICE"
        "THU" -> food = "VEG"
        "FRI" -> food = "PIZZA"
        "SUN" -> food = "IDLI"
        else -> food = "nothing"
    }

    return food


}

fun randomDay(): Any {
    val week = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
    return week[java.util.Random().nextInt(week.size)]
}

fun isTooHot(tmp: Int) = tmp > 30
fun isDirty(dirty: Int) = dirty > 30
fun isSun(day: String) = day == "SUN"

//compact fun example
fun shouldChangeWater(day: String, tmp: Int = 22, dirty: Int = 20): Boolean {
    return when {
        isTooHot(tmp) -> true
        isDirty(dirty) -> true
        isSun(day) -> true
        else -> false
    }
}

fun demoFilterList() {
    val decoration = listOf("SACHIN", "", "LOVE", "YOU", "SO", "MUCH", "")
    //when ever filter is used evrey time new list is created
    println(decoration.filter { it[0] == 'K' })


}