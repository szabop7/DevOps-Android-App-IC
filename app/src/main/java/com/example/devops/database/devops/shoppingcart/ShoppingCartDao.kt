package com.example.devops.database.devops.shoppingcart

import androidx.room.*
import com.example.devops.database.devops.product.Product

@Dao
interface ShoppingCartDao {

    @Insert
    suspend fun insert(shoppingCart: ShoppingCart)

    @Update
    suspend fun update(shoppingCart: ShoppingCart)

    @Query("SELECT * from shopping_cart_table WHERE Id = :key")
    suspend fun get(key: Long): ShoppingCart?

    @Query("DELETE FROM shopping_cart_table")
    suspend fun clear()

    @Query("SELECT * FROM shopping_cart_table ORDER BY Id DESC")
    suspend fun getAllProducts(shoppingCart: ShoppingCart): List<Product>

    @Query("SELECT * FROM shopping_cart_table ORDER BY Id DESC LIMIT 1")
    suspend fun getLastTag(): ShoppingCart?

    @Query("SELECT COUNT(*) FROM shopping_cart_table")
    suspend fun numberOfTags(): Int

    @Transaction
    @Query("SELECT * FROM shopping_cart_table")
    fun getShoppingCartsWithProducts(): List<ShoppingCartWithProducts>
}