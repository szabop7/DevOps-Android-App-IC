package com.example.devops.database.devops.artist

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.review.Review

data class ArtistWithReviews(
    @Embedded val artist: ArtistDatabase,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "userReviewerId"
    )
    val reviews: List<Review>
)
