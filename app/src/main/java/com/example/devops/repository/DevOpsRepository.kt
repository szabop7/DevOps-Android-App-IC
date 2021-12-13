package com.example.devops.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.asDomainModel
import com.example.devops.database.devops.shoppingcart.ShoppingCart
import com.example.devops.domain.Product
import com.example.devops.network.DevOpsApi
import com.example.devops.network.asDatabaseModel
import com.example.devops.network.asDatabaseProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
   * Class to connect the db and the network
   * Contains a LiveData object with products
   * Can refresh products
   * */

class DevOpsRepository(private val database: DevOpsDatabase) {

        // Network call
        // get products from the database, but transform them with map
        val products: LiveData<List<Product>> = Transformations.map(database.productDao.getAllProductsLive()) {
            it.asDomainModel()
        }

        val shoppingCart: LiveData<List<Product>> = Transformations.map(database.shoppingCartDao.getShoppingCartsWithProductsLive()) {
            if (it.isNotEmpty()) {
                it[0].productDatabases.asDomainModel()
            } else {
                emptyList<Product>()
            }
        }

        // Database call
        suspend fun refreshProducts() {
            // switch context to IO thread
            withContext(Dispatchers.IO) {
                val products = DevOpsApi.retrofitService.getProducts().await()
                // '*': kotlin spread operator.
                // Used for functions that expect a vararg param
                // just spreads the array into separate fields
                database.productDao.insertAll(*products.asDatabaseModel())
            }
        }

        // Database call
        suspend fun getProduct(productId: Long) {
            // switch context to IO thread
            withContext(Dispatchers.IO) {
                val product = DevOpsApi.retrofitService.getProduct(productId).await()
                // '*': kotlin spread operator.
                // Used for functions that expect a vararg param
                // just spreads the array into separate fields
                database.productDao.insertAll(product.asDatabaseProduct())
            }
        }

        suspend fun addShoppingItem(productId: Long) {
            var shoppingCart = database.shoppingCartDao.get(1)
            if (shoppingCart == null) {
                shoppingCart = ShoppingCart(1, 0.0, 1)
                database.shoppingCartDao.insert(shoppingCart)
            }
            if (database.shoppingCartDao.getShoppingCartExists(1, productId) == 0L) {
                database.shoppingCartDao.insertProductShoppingCart(1, productId)
            }
        }

        suspend fun removeShoppingItem(productId: Long) {
            var shoppingCart = database.shoppingCartDao.get(1)
            if (shoppingCart != null) {
                database.shoppingCartDao.removeShoppingCartItem(1, productId)
            }
        }
}