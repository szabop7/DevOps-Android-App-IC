package com.example.devops.database.devops.order

import androidx.room.*

@Entity(tableName = "order_table")
data class Order(

    @PrimaryKey(autoGenerate = true)
    var orderId: Long = 0L,

    @ColumnInfo(name = "order_destination_street")
    var orderDestinationStreet: String = "",

    @ColumnInfo(name = "order_destination_city")
    var orderDestinationCity: String = "",

    @ColumnInfo(name = "order_destination_zip_code")
    var orderDestinationZipCode: Long = 0L,

    @ColumnInfo(name = "order_destination_country")
    var orderDestinationCountry: String = "",

    @ColumnInfo(name = "order_destination_total_price")
    var orderTotalPrice: Double = 0.0,

    val userBuyerId: Long

)