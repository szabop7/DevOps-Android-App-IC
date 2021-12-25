package com.example.devops.database.devops.userart

import androidx.room.*

@Dao
interface UserArtDao {

    @Insert
    suspend fun insert(userArt: UserArt)

    @Update
    suspend fun update(userArt: UserArt)

    @Query("SELECT * from user_art_table WHERE userArtId = :key")
    suspend fun get(key: Long): UserArt?

    @Query("DELETE FROM user_art_table")
    suspend fun clear()

    @Query("SELECT * FROM user_art_table ORDER BY userArtId DESC LIMIT 1")
    suspend fun getLastUserArt(): UserArt?

    @Query("SELECT COUNT(*) FROM user_art_table")
    suspend fun numberOfUserArts(): Int

    @Transaction
    @Query("SELECT * FROM user_art_table")
    fun getUsersAndShoppingCarts(): List<UserArtAndShoppingCart>

    @Transaction
    @Query("SELECT * FROM user_art_table")
    fun getUsersWithOrders(): List<UserArtWithOrders>

    @Transaction
    @Query("SELECT * FROM user_art_table")
    fun getUsersWithBets(): List<UserWithBets>

    @Transaction
    @Query("SELECT * FROM user_art_table")
    fun getUsersWithReviews(): List<UserWithReviews>
}