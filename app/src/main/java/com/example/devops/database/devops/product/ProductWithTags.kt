package com.example.devops.database.devops.product

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops.database.devops.tag.Tag

class ProductWithTags (
    @Embedded val product: Product,
    @Relation(
        parentColumn = "productId",
        entityColumn = "tagId",
        associateBy = Junction(ProductTagCrossRef::class)
    )
    val tags: List<Tag>
)