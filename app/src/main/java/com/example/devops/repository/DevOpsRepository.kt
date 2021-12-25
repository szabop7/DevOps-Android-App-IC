package com.example.devops.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.artist.asDomainModel
import com.example.devops.database.devops.product.ProductTagCrossRef
import com.example.devops.database.devops.product.asDomainModel
import com.example.devops.database.devops.shoppingcart.ShoppingCart
import com.example.devops.database.devops.tag.asDomainModel
import com.example.devops.domain.Artist
import com.example.devops.domain.Product
import com.example.devops.domain.Tag
import com.example.devops.network.DevOpsApi
import com.example.devops.network.asDatabaseModel
import com.example.devops.network.asDatabaseProduct
import com.example.devops.network.asDatabaseTag
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
        val productsWithTags: LiveData<List<Product>> = Transformations.map(database.productDao.getProductsWithTagsLive()) {
            it.asDomainModel()
        }

        val shoppingCart: LiveData<List<Product>> = Transformations.map(database.shoppingCartDao.getShoppingCartsWithProductsLive()) {
            if (it.isNotEmpty()) {
                it[0].productDatabases.asDomainModel()
            } else {
                emptyList()
            }
        }

        // Network call
        // get products from the database, but transform them with map
        val tags: LiveData<List<Tag>> = Transformations.map(database.tagDao.getAllTagsLive()) {
            it.asDomainModel()
        }

        val artists: LiveData<List<Artist>> = Transformations.map(database.artistDao.getAllArtistsLive()) {
            it.asDomainModel()
        }

        // Database call
        suspend fun refreshProducts() {
            // switch context to IO thread
            withContext(Dispatchers.IO) {
                try {
                    val products = DevOpsApi.retrofitService.getProductsAsync().await()
                    // '*': kotlin spread operator.
                    // Used for functions that expect a vararg param
                    // just spreads the array into separate fields
                    val dbProducts = products.asDatabaseModel()
                    database.productDao.insertAll(*dbProducts)
                    for (product in products.apiProducts) {
                        val tags = product.tagList.map { it.asDatabaseTag() }
                        database.tagDao.insertAll(*tags.toTypedArray())
                        database.productDao.insertTagToProducts(*tags.map { ProductTagCrossRef(product.productId, it.tagId) }.toTypedArray())
                    }
                } catch (e: Exception) {}
            }
        }

    // Database call
        suspend fun getProduct(productId: Long) {
            // switch context to IO thread
            withContext(Dispatchers.IO) {
                try {
                    val product = DevOpsApi.retrofitService.getProductAsync(productId).await()
                    database.productDao.insertAll(product.asDatabaseProduct())
                } catch (e: Exception) {}
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
            val shoppingCart = database.shoppingCartDao.get(1)
            if (shoppingCart != null) {
                database.shoppingCartDao.removeShoppingCartItem(1, productId)
            }
        }
}