package com.example.devops.database.devops.order

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.product.Product

data class OrderWithProducts(
    @Embedded val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "buyOrderId"
    )
    val products: List<Product>
)