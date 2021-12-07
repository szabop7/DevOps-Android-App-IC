package com.example.devops.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.ProductDatabase
import com.example.devops.database.devops.product.asDomainModel
import com.example.devops.domain.Product
import com.example.devops.network.ApiProduct
import com.example.devops.network.DevOpsApi
import com.example.devops.network.DevOpsApi.retrofitService
import com.example.devops.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
   * Class to connect the db and the network
   * Contains a LiveData object with products
   * Can refresh products
   * */

class DevOpsRepository (private val database: DevOpsDatabase) {


        //Network call
        //get products from the database, but transform them with map
        val products: LiveData<List<Product>> = Transformations.map(database.productDao.getAllProductsLive()){
            it.asDomainModel()
        }

        //Database call
        suspend fun refreshProducts(){
            //switch context to IO thread
            withContext(Dispatchers.IO){
                val products = DevOpsApi.retrofitService.getProducts().await()
                Log.i("Hola",products.toString())
                //'*': kotlin spread operator.
                //Used for functions that expect a vararg param
                //just spreads the array into separate fields
                database.productDao.insertAll(*products.asDatabaseModel())
            }
        }
}