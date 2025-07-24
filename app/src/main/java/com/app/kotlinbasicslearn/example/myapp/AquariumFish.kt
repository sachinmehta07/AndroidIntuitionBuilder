package com.app.kotlinbasicslearn.example.myapp

abstract class AquariumFish {

    //its shared data nd logic

    //its template of common things
    abstract val color: String
    abstract val size: Int
    abstract val type: String

}


object GrayColor : FishColor {
    override val color = "GRAY"
}

object GoldColor : FishColor {
    override val color = "GOLD"
}

class AggressiveFishAction : FishAction {
    override fun eat() = println("YES SMALL FISH")
    override fun canKill(boolean: Boolean) = boolean
}

class PeacefulFishAction : FishAction {
    override fun eat() = println("YES FISH FOOD")
    override fun canKill(boolean: Boolean) = boolean
}

class Shark(
    fishColor: FishColor = GrayColor,
    fishAction: FishAction = AggressiveFishAction()
) : FishColor by fishColor, FishAction by fishAction

class MiniFish(
    fishColor: FishColor = GoldColor,
    fishAction: FishAction = PeacefulFishAction()
) : FishColor by fishColor, FishAction by fishAction