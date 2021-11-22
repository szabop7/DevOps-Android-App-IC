package com.example.devops.database.devops.product

import androidx.room.*

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * from product_table WHERE productId = :key")
    suspend fun get(key: Long): Product?

    @Query("DELETE FROM product_table")
    suspend fun clear()

    @Query("SELECT * FROM product_table ORDER BY productId DESC")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM product_table ORDER BY productId DESC LIMIT 1")
    suspend fun getLastProduct(): Product?

    @Query("SELECT COUNT(*) FROM product_table")
    suspend fun numberOfProducts(): Int

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithTags(): List<ProductWithTags>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithBets(): List<ProductWithBets>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithReviews(): List<ProductWithReviews>
}