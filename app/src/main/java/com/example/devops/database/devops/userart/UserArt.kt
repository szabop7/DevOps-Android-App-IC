package com.example.devops.database.devops.userart
import androidx.room.*

@Entity(tableName = "user_art_table")
data class UserArt (
    @PrimaryKey(autoGenerate = true)
    var userArtId: Long = 0L,

    @ColumnInfo(name = "user_art_first_name")
    var userArtFirstName: String = "",

    @ColumnInfo(name = "user_art_last_name")
    var userArtLastName: String = "",

    @ColumnInfo(name = "user_art_email")
    var userArtEmail: String = "",

    @ColumnInfo(name = "user_art_street")
    var userArtStreet: String = "",

    @ColumnInfo(name = "user_art_zipcode")
    var userArtZipcode: Long = 0L,

    @ColumnInfo(name = "user_art_country")
    var userArtCountry: String = "",

    @ColumnInfo(name = "user_art_city")
    var UserArtCity: String = "",

    @ColumnInfo(name = "user_art_is_Authenticated")
    var userArtIsAuthenticated: Boolean = false,




)