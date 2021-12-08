package com.example.devops.domain

data class Product(
    var productId: Long = 0L,
    var productName: String = "",
    var productPrice: Double = 0.0,
    var productImgPath: String = "",
    var productDescription: String = "",
    var productDeadline: String = "0000-00-00T00:00:00",
    var productIsAuction: Boolean = false,
    var productWidth: Double = 0.0,
    var productHeight: Double = 0.0
)
