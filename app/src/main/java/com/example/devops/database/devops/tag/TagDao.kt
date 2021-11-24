package com.example.devops.database.devops.tag

import androidx.room.*

@Dao
interface TagDao {
    @Insert
    suspend fun insert(tag: Tag)

    @Update
    suspend fun update(tag: Tag)

    @Query("SELECT * from tag_table WHERE tagId = :key")
    suspend fun get(key: Long): Tag?

    @Query("DELETE FROM tag_table")
    suspend fun clear()

    @Query("SELECT * FROM tag_table ORDER BY tagId DESC")
    suspend fun getAllTags(): List<Tag>

    @Query("SELECT * FROM tag_table ORDER BY tagId DESC LIMIT 1")
    suspend fun getLastTag(): Tag?

    @Query("SELECT COUNT(*) FROM tag_table")
    suspend fun numberOfTags(): Int

    @Transaction
    @Query("SELECT * FROM tag_table")
    fun getTagsWithProducts(): List<TagWithProducts>
}