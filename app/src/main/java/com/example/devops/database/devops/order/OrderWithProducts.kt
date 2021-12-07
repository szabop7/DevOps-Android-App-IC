package com.example.devops.database.devops.order

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.product.ProductDatabase

data class OrderWithProducts(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "buyOrderId"
    )
    val productDatabases: List<ProductDatabase>
)