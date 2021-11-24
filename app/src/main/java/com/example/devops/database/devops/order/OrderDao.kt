package com.example.devops.database.devops.order

import androidx.room.*
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OrderDao {
    @Insert
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Query("SELECT * from order_table WHERE orderId = :key")
    suspend fun get(key: Long): Order?

    @Query("DELETE FROM order_table")
    suspend fun clear()

    @Query("SELECT * FROM order_table ORDER BY orderId DESC")
    suspend fun getAllOrders(): List<Order>

    @Query("SELECT * FROM order_table ORDER BY orderId DESC LIMIT 1")
    suspend fun getLastOrder(): Order?

    @Query("SELECT COUNT(*) FROM order_table")
    suspend fun numberOfOrders(): Int

    @Transaction
    @Query("SELECT * FROM order_table")
    fun getOrdersWithProducts(): List<OrderWithProducts>

}