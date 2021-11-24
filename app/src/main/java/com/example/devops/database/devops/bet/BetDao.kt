package com.example.devops.database.devops.bet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.devops.database.devops.artist.Artist

@Dao
interface BetDao {
    @Insert
    suspend fun insert(bet: Bet)

    @Update
    suspend fun update(bet: Bet)

    @Query("SELECT * from bet_table WHERE betId = :key")
    suspend fun get(key: Long): Bet?

    @Query("DELETE FROM bet_table")
    suspend fun clear()

    @Query("SELECT * FROM bet_table ORDER BY betId DESC")
    suspend fun getAllBets(): List<Bet>

    @Query("SELECT * FROM bet_table ORDER BY betId DESC LIMIT 1")
    suspend fun getLastBet(): Bet?

    @Query("SELECT COUNT(*) FROM bet_table")
    suspend fun numberOfBets(): Int
}