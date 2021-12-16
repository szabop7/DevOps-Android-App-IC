package com.example.devops.database.devops.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devops.domain.Artist

@Entity(tableName = "artist_table")
data class ArtistDatabase(

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

    @ColumnInfo(name = "artist_nationality")
    var artistNationality: String = "",

    @ColumnInfo(name = "artist_is_young_artist")
    var artistIsYoungArtist: Boolean = false,

    @ColumnInfo(name = "artist_is_conformed")
    var artistIsConformed: Boolean = false
)

// convert Joke to ApiJoke
fun List<ArtistDatabase>.asDomainModel(): List<Artist> {
    return map {
        Artist(
            artistId = it.artistId,
            artistFirstName = it.artistFirstName,
            artistLastName = it.artistLastName,
            artistEmail = it.artistEmail,
            artistBic = it.artistBic,
            artistBank = it.artistBank,
            artistNationality = it.artistNationality,
            artistIsYoungArtist = it.artistIsYoungArtist,
            artistIsConformed = it.artistIsConformed
        )
    }
}
