package com.example.devops.database.devops.review

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_table")
data class Review (
    @PrimaryKey(autoGenerate = true)
    var reviewId: Long = 0L,

    @ColumnInfo(name = "review_rating")
    var reviewRating: Long = 0L,

    @ColumnInfo(name = "review_title")
    var reviewTitle: String = "",

    @ColumnInfo(name = "review_text")
    var reviewText: String = "",

    val userReviewerId: Long,

    val productReviewId: Long,

    val artistReviewId: Long
    )