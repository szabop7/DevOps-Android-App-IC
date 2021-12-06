package com.example.devops.database.devops.userart

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.order.Order

data class UserArtWithOrders(
    @Embedded val user: UserArt,
    @Relation(
        parentColumn = "userArtId",
        entityColumn = "userBuyerId"
    )
    val orders: List<Order>
)