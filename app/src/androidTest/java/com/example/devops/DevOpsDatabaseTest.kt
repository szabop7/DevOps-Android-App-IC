package com.example.devops

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.Product
import com.example.devops.database.devops.product.ProductDao
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
        val product = Product()
        productDao.insert(product)
        val lastProduct = productDao.getLastProduct()
        assertEquals(lastProduct?.productName, "")
    }

    /*@Test
    @Throws(Exception::class)
    fun insertAndGetProductFromShoppingCart() = runBlocking {
        val product = Product()
        productDao.
        val lastProduct = productDao.getLastProduct()
        assertEquals(lastProduct?.productName, "")
    }*/
}