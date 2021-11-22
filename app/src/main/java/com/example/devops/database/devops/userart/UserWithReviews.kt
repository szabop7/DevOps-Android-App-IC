package com.example.devops.database.devops.userart

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.review.Review

data class UserWithReviews(
    @Embedded val userArt: UserArt,
    @Relation(
        parentColumn = "userArtId",
        entityColumn = "userReviewerId"
    )
    val reviews: List<Review>
)
