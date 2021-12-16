package com.example.devops.network

import com.example.devops.database.devops.artist.ArtistDatabase
import com.example.devops.domain.Artist
import com.squareup.moshi.Json

data class ApiArtistContainer(
    @Json(name = "body")
    val apiArtist: List<ApiArtist>
)

/*ApiArtist, representing a product from the network*/
data class ApiArtist(
    @Json(name = "id")
    var artistId: Long,

    @Json(name = "first_name")
    var artistFirstName: String,

    @Json(name = "last_name")
    var artistLastName: String,

    @Json(name = "email")
    var artistEmail: String,

    @Json(name = "bic")
    var artistBic: String,

    @Json(name = "iban")
    var artistBank: String,

    @Json(name = "nationality")
    var artistNationality: String,

    @Json(name = "is_young")
    var artistIsYoungArtist: Boolean,

    @Json(name = "is_conformed")
    var artistIsConformed: Boolean
)

/*
* Convert network results into Domain artist
* */
fun ApiArtistContainer.asDomainModel(): List<Artist> {
    return apiArtist.map {
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

/*
* Convert network result into Database artists
*
* returns an array that can be used in the insert call as vararg
* */
fun ApiArtistContainer.asDatabaseModel(): Array<ArtistDatabase> {
    return apiArtist.map {
        ArtistDatabase(
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
    }.toTypedArray()
}

/*
* Convert a single api artist to a database product
* */
fun ApiArtist.asDatabaseProduct(): ArtistDatabase {
    return ArtistDatabase(
        artistId = artistId,
        artistFirstName = artistFirstName,
        artistLastName = artistLastName,
        artistEmail = artistEmail,
        artistBic = artistBic,
        artistBank = artistBank,
        artistNationality = artistNationality,
        artistIsYoungArtist = artistIsYoungArtist,
        artistIsConformed = artistIsConformed
    )
}