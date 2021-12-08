package com.example.devops.database.devops.shoppingcart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingCartDao {

    @Insert
    suspend fun insert(shoppingCart: ShoppingCart)

    @Update
    suspend fun update(shoppingCart: ShoppingCart)

    @Query("SELECT * from shopping_cart_table WHERE shoppingCartId = :key")
    suspend fun get(key: Long): ShoppingCart?

    @Query("DELETE FROM shopping_cart_table")
    suspend fun clear()

    // @Query("SELECT * FROM shopping_cart_table ORDER BY shoppingCartId DESC")
    // suspend fun getAllProducts(shoppingCart: ShoppingCart): List<Product>

    @Query("SELECT * FROM shopping_cart_table ORDER BY shoppingCartId DESC LIMIT 1")
    suspend fun getLastShoppingCart(): ShoppingCart?

    @Query("SELECT COUNT(*) FROM shopping_cart_table")
    suspend fun numberOfShoppingCarts(): Int

    @Transaction
    @Query("SELECT * FROM shopping_cart_table")
    fun getShoppingCartsWithProducts(): List<ShoppingCartWithProducts>

    @Transaction
    @Query("SELECT * FROM shopping_cart_table")
    fun getShoppingCartsWithProductsLive(): LiveData<List<ShoppingCartWithProducts>>

    @Query(
        "INSERT INTO shopping_cart_product_id_table (shoppingCartId, productId) VALUES (:shoppingCartId, :productId)")
    suspend fun insertProductShoppingCart(shoppingCartId: Long, productId: Long)

    @Query("SELECT COUNT(*) FROM shopping_cart_product_id_table WHERE shoppingCartId = :shoppingCartId AND productId = :productId")
    suspend fun getShoppingCartExists(shoppingCartId: Long, productId: Long): Long

    @Query("DELETE FROM shopping_cart_product_id_table WHERE shoppingCartId = :shoppingCartId AND productId = :productId")
    suspend fun removeShoppingCartItem(shoppingCartId: Long, productId: Long)
}