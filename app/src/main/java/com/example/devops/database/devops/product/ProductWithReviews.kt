package com.example.devops.database.devops.product

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.review.Review

data class ProductWithReviews(
    @Embedded val productDatabase: ProductDatabase,
    @Relation(
        parentColumn = "productId",
        entityColumn = "userReviewerId"
    )
    val reviews: List<Review>
)
