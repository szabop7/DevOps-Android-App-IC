package com.example.devops.database.devops.shoppingcart

import androidx.room.Entity

@Entity(primaryKeys = ["shoppingCartId", "productId"])
data class ShoppingCartProductCrossRef(
    val shoppingCartId: Long,
    val productId: Long
)