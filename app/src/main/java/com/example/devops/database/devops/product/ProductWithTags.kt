package com.example.devops.database.devops.product

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops.database.devops.tag.Tag

class ProductWithTags(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "productId",
        entity = Tag::class,
        entityColumn = "tagId",
        associateBy = Junction(value = ProductTagCrossRef::class)
    )
    val tags: List<Tag>
)