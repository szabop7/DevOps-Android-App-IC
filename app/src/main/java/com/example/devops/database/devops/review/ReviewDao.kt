package com.example.devops.database.devops.review

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReviewDao {
    @Insert
    suspend fun insert(review: Review)

    @Update
    suspend fun update(review: Review)

    @Query("SELECT * from review_table WHERE Id = :key")
    suspend fun get(key: Long): Review?

    @Query("DELETE FROM review_table")
    suspend fun clear()

    @Query("SELECT * FROM review_table ORDER BY Id DESC")
    suspend fun getAllReviews(): List<Review>

    @Query("SELECT * FROM review_table ORDER BY Id DESC LIMIT 1")
    suspend fun getLastReview(): Review?

    @Query("SELECT COUNT(*) FROM review_table")
    suspend fun numberOfReviews(): Int
}