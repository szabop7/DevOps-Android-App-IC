package com.example.devops.database.devops.artist

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.review.Review

data class ArtistWithReviews(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "userReviewerId"
    )
    val reviews: List<Review>
)
