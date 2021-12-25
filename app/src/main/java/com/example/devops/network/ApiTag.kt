package com.example.devops.network

import com.example.devops.database.devops.tag.TagDatabase
import com.squareup.moshi.Json

/*ApiProduct, representing a product from the network*/
data class ApiTag(
    @Json(name = "id")
    var tagId: Long,

    @Json(name = "name")
    var tagName: String
)
/*
* Convert a single api product to a database product
* */
fun ApiTag.asDatabaseTag(): TagDatabase {
    return TagDatabase(
        tagId = tagId,
        tagName = tagName
    )
}

