package com.example.devops.domain

data class Artist(
    var artistId: Long = 0L,
    var artistFirstName: String = "",
    var artistLastName: String = "",
    var artistEmail: String = "",
    var artistBic: String = "",
    var artistBank: String = "",
    var artistNationality: String = "",
    var artistIsYoungArtist: Boolean = false,
    var artistIsConformed: Boolean = false,
)
