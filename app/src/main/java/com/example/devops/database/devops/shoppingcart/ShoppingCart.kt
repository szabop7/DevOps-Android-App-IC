package com.example.devops.database.devops.shoppingcart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart_table")
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true)
    var shoppingCartId: Long = 0L,

    @ColumnInfo(name = "shopping_cart_total_price")
    var shoppingCartTotalPrice: Double = 0.0,

    val userOwnerId: Long
)