package com.example.devops.database.devops.tag

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops.database.devops.product.Product


data class TagWithProducts (
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "productId",
        associateBy = Junction(TagProductCrossRef::class)
    )
    val products: List<Product>
        )