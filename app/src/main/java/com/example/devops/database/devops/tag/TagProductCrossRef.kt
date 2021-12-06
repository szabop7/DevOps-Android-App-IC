package com.example.devops.database.devops.tag

import androidx.room.Entity

@Entity(primaryKeys = ["productId", "tagId"])
class TagProductCrossRef(
    val tagId: Long,
    val productId: Long
)