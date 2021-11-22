package com.example.devops.database.devops.product

import androidx.room.Entity

@Entity(primaryKeys = ["productId", "tagId"])
data class ProductTagCrossRef (
    val productId: Long,
    val tagId: Long
)