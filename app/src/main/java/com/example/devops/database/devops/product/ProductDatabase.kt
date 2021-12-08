package com.example.devops.database.devops.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devops.domain.Product

@Entity(tableName = "product_table")
data class ProductDatabase(

    @PrimaryKey(autoGenerate = true)
    var productId: Long = 0L,

    @ColumnInfo(name = "product_name")
    var productName: String = "",

    @ColumnInfo(name = "product_price")
    var productPrice: Double = 0.0,

    @ColumnInfo(name = "product_Img_Path")
    var productImgPath: String = "",

    @ColumnInfo(name = "product_description")
    var productDescription: String = "",

    @ColumnInfo(name = "product_deadline")
    var productDeadline: String = "0000-00-00T00:00:00",

    @ColumnInfo(name = "product_is_auction")
    var productIsAuction: Boolean = false,

    @ColumnInfo(name = "product_width")
    var productWidth: Double = 0.0,

    @ColumnInfo(name = "product_height")
    var productHeight: Double = 0.0,
    val userBuyerId: Long = 0L,
    val buyOrderId: Long? = 0L,
    val userOwnerId: Long = 0L
)

// convert Joke to ApiJoke
fun List<ProductDatabase>.asDomainModel(): List<Product> {
    return map {
        Product(
            productId = it.productId,
            productName = it.productName,
            productPrice = it.productPrice,
            productImgPath = it.productImgPath,
            productDescription = it.productDescription,
            productDeadline = it.productDeadline,
            productIsAuction = it.productIsAuction,
            productWidth = it.productWidth,
            productHeight = it.productHeight
        )
    }
}
