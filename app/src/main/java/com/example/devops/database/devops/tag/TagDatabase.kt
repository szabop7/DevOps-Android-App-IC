package com.example.devops.database.devops.tag

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devops.domain.Tag

@Entity(tableName = "tag_table")
data class TagDatabase(
    @PrimaryKey(autoGenerate = true)
    var tagId: Long = 0L,

    @ColumnInfo(name = "tag_name")
    var tagName: String = ""
)

// convert Joke to ApiJoke
fun List<TagDatabase>.asDomainModel(): List<Tag> {
    return map {
        Tag(
            tagId = it.tagId,
            tagName = it.tagName
        )
    }
}
