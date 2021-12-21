package com.example.devops.database.devops.tag

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDao {
    @Insert
    suspend fun insert(tagDatabase: TagDatabase)

    // adding insert all with vararg
    // replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tag: TagDatabase)

    @Query("SELECT * FROM tag_table ORDER BY tagId DESC")
    fun getAllTagsLive(): LiveData<List<TagDatabase>>

    @Update
    suspend fun update(tagDatabase: TagDatabase)

    @Query("SELECT * from tag_table WHERE tagId = :key")
    suspend fun get(key: Long): TagDatabase?

    @Query("DELETE FROM tag_table")
    suspend fun clear()

    @Query("SELECT * FROM tag_table ORDER BY tagId DESC")
    suspend fun getAllTags(): List<TagDatabase>

    @Query("SELECT * FROM tag_table ORDER BY tagId DESC LIMIT 1")
    suspend fun getLastTag(): TagDatabase?

    @Query("SELECT COUNT(*) FROM tag_table")
    suspend fun numberOfTags(): Int

    @Transaction
    @Query("SELECT * FROM tag_table")
    fun getTagsWithProducts(): List<TagWithProducts>
}