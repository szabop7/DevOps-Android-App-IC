package com.example.devops

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.ProductDatabase
import com.example.devops.database.devops.product.ProductDao
import com.example.devops.database.devops.product.ProductTagCrossRef
import com.example.devops.database.devops.product.asDomainModel
import com.example.devops.database.devops.tag.TagDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class DevOpsDatabaseTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: DevOpsDatabase

    @Before
    fun createDb() {
        Log.i("before", "running before")
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, DevOpsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        productDao = db.productDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetProduct() = runBlocking {
        val productDatabase = ProductDatabase(productName = "product")
        productDao.insert(productDatabase)
        val lastProduct = productDao.getLastProduct()
        assertEquals("product", lastProduct?.productName)
    }

    @Test
    @Throws(Exception::class)
    fun products_tagRelationship() = runBlocking {
        db.productDao.insert(ProductDatabase(1, productName = "Test"))
        db.tagDao.insert(TagDatabase(1, "Yay"))
        db.productDao.insertTagToProducts(ProductTagCrossRef(1, 1))

        val products = db.productDao.getProductsWithTags().asDomainModel()
        assertEquals(1, products.size)
        val product = products[0]
        assertEquals(1, product.productId)
        assertEquals(1, product.tagList.size)
        assertEquals(1, product.tagList[0].tagId)
    }
}