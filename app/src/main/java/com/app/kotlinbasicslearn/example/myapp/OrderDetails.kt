package com.app.kotlinbasicslearn.example.myapp

class OrderDetails(
    val customerName: String = "",
    var itemList: List<String> = listOf(),
    var totalAmt: Double = 0.0,
    var isGift: Boolean = false,
    var giftMsg: String = ""
) {

    init {
        println("Order started for $customerName")
    }

    //secondary constructor for gift order
    constructor(customerName: String, giftMsg: String, giftAmt: Double) : this(customerName) {
        isGift = true
        this.giftMsg = giftMsg
        totalAmt = giftAmt
    }

    fun printOrderDetails() {
        println("Customer: $customerName")
        if (isGift) {
            println("Gift Message: $giftMsg")
            println("Gift Amount: $$totalAmt")
        } else {
            println("Items: $itemList")
            println("Total Amount: $$totalAmt")
        }
    }

    // ✨ Factory function for Gift Order
    companion object {
        fun createGiftOrder(
            customerName: String,
            giftMessage: String,
            giftAmount: Double
        ): OrderDetails {
            return OrderDetails(
                customerName = customerName,
                itemList = listOf(),
                totalAmt = giftAmount,
                isGift = true,
                giftMsg = giftMessage
            )
        }
    }
}