package com.example.devops.database.devops.artist

import androidx.room.*

@Dao
interface ArtistDao {

    @Insert
    suspend fun insert(artist: Artist)

    @Update
    suspend fun update(artist: Artist)

    @Query("SELECT * from artist_table WHERE artistId = :key")
    suspend fun get(key: Long): Artist?

    @Query("DELETE FROM artist_table")
    suspend fun clear()

    @Query("SELECT * FROM artist_table ORDER BY artistId DESC")
    suspend fun getAllArtists(): List<Artist>

    @Query("SELECT * FROM artist_table ORDER BY artistId DESC LIMIT 1")
    suspend fun getLastArtist(): Artist?

    @Query("SELECT COUNT(*) FROM artist_table")
    suspend fun numberOfArtist(): Int

    @Transaction
    @Query("SELECT * FROM artist_table")
    fun getArtistsWithProducts(): List<ArtistWithProducts>

    @Transaction
    @Query("SELECT * FROM artist_table")
    fun getArtistWithReviews(): List<ArtistWithReviews>

}