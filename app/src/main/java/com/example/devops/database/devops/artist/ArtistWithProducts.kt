package com.example.devops.database.devops.artist

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.product.ProductDatabase

data class ArtistWithProducts(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "productId"
    )
    val productDatabases: List<ProductDatabase>
)