package com.example.devops.database.devops.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "artist_table")
data class Artist(

    @PrimaryKey(autoGenerate = true)
    var artistId: Long = 0L,

    @ColumnInfo(name = "artist_first_name")
    var artistFirstName: String = "",

    @ColumnInfo(name = "artist_last_name")
    var artistLastName: String = "",

    @ColumnInfo(name = "artist_email")
    var artistEmail: String = "",

    @ColumnInfo(name = "artist_bic")
    var artistBic: String = "",

    @ColumnInfo(name = "artist_bank")
    var artistBank: String = "",

    @ColumnInfo(name = "artist_nacionality")
    var artistNacionality: String = "",

    @ColumnInfo(name = "artist_is_young_artist")
    var artisIsYoungArtist: Boolean = false,

    @ColumnInfo(name = "artist_is_conformed")
    var artistIsConformed: Boolean = false

)