package com.example.devops.database.devops.artist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtistDao {

    @Insert
    suspend fun insert(artist: ArtistDatabase)

    // adding insert all with vararg
    // replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg artists: ArtistDatabase)

    @Update
    suspend fun update(artist: ArtistDatabase)

    @Query("SELECT * from artist_table WHERE artistId = :key")
    suspend fun get(key: Long): ArtistDatabase?

    @Query("DELETE FROM artist_table")
    suspend fun clear()

    @Query("SELECT * FROM artist_table ORDER BY artistId DESC LIMIT 1")
    suspend fun getLastArtist(): ArtistDatabase?

    @Query("SELECT COUNT(*) FROM artist_table")
    suspend fun numberOfArtist(): Int

    @Transaction
    @Query("SELECT * FROM artist_table")
    fun getArtistsWithProducts(): List<ArtistWithProducts>

    @Transaction
    @Query("SELECT * FROM artist_table")
    fun getArtistWithReviews(): List<ArtistWithReviews>

    @Query("SELECT * FROM artist_table ORDER BY artistId DESC")
    fun getAllArtistsLive(): LiveData<List<ArtistDatabase>>
}