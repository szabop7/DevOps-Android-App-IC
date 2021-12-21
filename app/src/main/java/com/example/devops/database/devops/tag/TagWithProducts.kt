package com.example.devops.database.devops.tag

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops.database.devops.product.ProductDatabase

data class TagWithProducts(
    @Embedded val tagDatabase: TagDatabase,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "productId",
        associateBy = Junction(TagProductCrossRef::class)
    )
    val productDatabases: List<ProductDatabase>
)