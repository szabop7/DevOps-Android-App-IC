package com.example.devops.network

import com.example.devops.database.devops.tag.TagDatabase
import com.example.devops.domain.Tag
import com.squareup.moshi.Json

data class ApiTagContainer(
    @Json(name = "body")
    val apiTags: List<ApiTag>
)

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

/*
* Convert a single api tag to a model tag
* */
fun ApiTag.asDomainModel(): Tag {
    return Tag(
        tagId = tagId,
        tagName = tagName
    )
}