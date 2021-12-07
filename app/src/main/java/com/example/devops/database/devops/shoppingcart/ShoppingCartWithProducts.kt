package com.example.devops.database.devops.shoppingcart

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops.database.devops.product.ProductDatabase

data class ShoppingCartWithProducts(
    @Embedded val shoppingCart: ShoppingCart,
    @Relation(
        parentColumn = "shoppingCartId",
        entityColumn = "productId",
        associateBy = Junction(ShoppingCartProductCrossRef::class)
    )
    val productDatabases: List<ProductDatabase>
)