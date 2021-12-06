package com.example.devops.database.devops.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "product_table")
data class Product(

    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    var productId: Long = 0L,

    @ColumnInfo(name = "product_name")
    @Json(name = "name")
    var productName: String = "",

    @ColumnInfo(name = "product_price")
    @Json(name = "price")
    var productPrice: Double = 0.0,

    @ColumnInfo(name = "product_Img_Path")
    @Json(name = "imgPath")
    var productImgPath: String = "",

    @ColumnInfo(name = "product_description")
    @Json(name = "desciption")
    var productDescription: String = "",

    @ColumnInfo(name = "product_deadline")
    var productDeadline: String = "0000-00-00T00:00:00",

    @ColumnInfo(name = "product_is_auction")
    @Json(name = "isAuction")
    var productIsAuction: Boolean = false,

    @ColumnInfo(name = "product_width")
    @Json(name = "width")
    var productWidth: Double = 0.0,

    @ColumnInfo(name = "product_height")
    @Json(name = "height")
    var productHeight: Double = 0.0,
    val userBuyerId: Long = 0L,
    val buyOrderId: Long? = 0L,
    val userOwnerId: Long = 0L
)