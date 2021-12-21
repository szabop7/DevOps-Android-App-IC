package com.example.devops.database.devops.product

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(productDatabase: ProductDatabase)

    // adding insert all with vararg
    // replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg product: ProductDatabase)

    @Update
    suspend fun update(productDatabase: ProductDatabase)

    @Query("SELECT * from product_table WHERE productId = :key")
    suspend fun get(key: Long): ProductDatabase?

    @Query("DELETE FROM product_table")
    suspend fun clear()

    @Query("SELECT * FROM product_table ORDER BY productId DESC")
    suspend fun getAllProducts(): List<ProductDatabase>

    @Query("SELECT * FROM product_table ORDER BY productId DESC")
    fun getAllProductsLive(): LiveData<List<ProductDatabase>>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithTagsLive(): LiveData<List<ProductWithTags>>

    @Query("SELECT * FROM product_table ORDER BY productId DESC LIMIT 1")
    suspend fun getLastProduct(): ProductDatabase?

    @Query("SELECT COUNT(*) FROM product_table")
    suspend fun numberOfProducts(): Int

    // Dao query with filter
    @Query("SELECT * from product_table WHERE product_name LIKE :filter OR product_description LIKE :filter ORDER BY productId")
    fun getProductsFiltered(filter: String): LiveData<List<ProductDatabase>>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithTags(): List<ProductWithTags>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithBets(): List<ProductWithBets>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithReviews(): List<ProductWithReviews>

    // adding insert all with vararg
    // replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTagToProducts(vararg tagsCrossRef: ProductTagCrossRef)
}