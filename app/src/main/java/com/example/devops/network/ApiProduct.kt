package com.example.devops.network

import com.example.devops.database.devops.product.ProductDatabase
import com.example.devops.domain.Product
import com.squareup.moshi.Json

data class ApiProductContainer(
    @Json(name = "body")
    val apiProducts: List<ApiProduct>
)

/*ApiProduct, representing a product from the network*/
data class ApiProduct(
    @Json(name = "id")
    var productId: Long,

    @Json(name = "name")
    var productName: String,

    @Json(name = "price")
    var productPrice: Double,

    @Json(name = "imgPath")
    var productImgPath: String,

    @Json(name = "desciption")
    var productDescription: String,

    @Json(name = "isAuction")
    var productIsAuction: Boolean,

    @Json(name = "width")
    var productWidth: Double,

    @Json(name = "height")
    var productHeight: Double
)

/*
* Convert network results into Domain products
* */
fun ApiProductContainer.asDomainModel(): List<Product> {
    return apiProducts.map {
        Product(
            productId = it.productId,
            productName = it.productName,
            productPrice = it.productPrice,
            productImgPath = it.productImgPath,
            productDescription = it.productDescription,
            productIsAuction = it.productIsAuction,
            productWidth = it.productWidth,
            productHeight = it.productHeight
        )
    }
}

/*
* Convert network result into Database products
*
* returns an array that can be used in the insert call as vararg
* */
fun ApiProductContainer.asDatabaseModel(): Array<ProductDatabase> {
    return apiProducts.map {
        ProductDatabase(
            productId = it.productId,
            productName = it.productName,
            productPrice = it.productPrice,
            productImgPath = it.productImgPath,
            productDescription = it.productDescription,
            productIsAuction = it.productIsAuction,
            productWidth = it.productWidth,
            productHeight = it.productHeight
        )
    }.toTypedArray()
}

/*
* Convert a single api product to a database product
* */
fun ApiProduct.asDatabaseProduct(): ProductDatabase {
    return ProductDatabase(
        productId = productId,
        productName = productName,
        productPrice = productPrice,
        productImgPath = productImgPath,
        productDescription = productDescription,
        productIsAuction = productIsAuction,
        productWidth = productWidth,
        productHeight = productHeight
    )
}