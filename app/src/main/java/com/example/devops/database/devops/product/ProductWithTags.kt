package com.example.devops.database.devops.product

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.devops.database.devops.tag.TagDatabase
import com.example.devops.database.devops.tag.asDomainModel
import com.example.devops.domain.Product
import com.example.devops.network.asDomainModel

class ProductWithTags(
    @Embedded val productDatabase: ProductDatabase,
    @Relation(
        parentColumn = "productId",
        entity = TagDatabase::class,
        entityColumn = "tagId",
        associateBy = Junction(value = ProductTagCrossRef::class)
    )
    val tagDatabases: List<TagDatabase>
)

// convert Joke to ApiJoke
fun ProductWithTags.asDomainModel(): Product {
    return Product(
        productId = productDatabase.productId,
        productName = productDatabase.productName,
        productPrice = productDatabase.productPrice,
        productImgPath = productDatabase.productImgPath,
        productDescription = productDatabase.productDescription,
        productIsAuction = productDatabase.productIsAuction,
        productWidth = productDatabase.productWidth,
        productHeight = productDatabase.productHeight,
        tagList = tagDatabases.asDomainModel()
    )
}

// convert Joke to ApiJoke
fun List<ProductWithTags>.asDomainModel(): List<Product> {
    return map {
        it.asDomainModel()
    }
}
