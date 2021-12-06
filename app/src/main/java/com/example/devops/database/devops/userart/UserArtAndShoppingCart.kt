package com.example.devops.database.devops.userart

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.shoppingcart.ShoppingCart

data class UserArtAndShoppingCart(

    @Embedded
    val user: UserArt,
    @Relation(
        parentColumn = "userArtId",
        entityColumn = "userOwnerId"
    )
    val shoppingCart: ShoppingCart

)