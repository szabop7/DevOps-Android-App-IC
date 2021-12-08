package com.example.devops.database.devops.shoppingcart

import androidx.room.Entity

@Entity(primaryKeys = ["shoppingCartId", "productId"], tableName = "shopping_cart_product_id_table")
data class ShoppingCartProductCrossRef(
    val shoppingCartId: Long,
    val productId: Long
)