package com.example.devops.fragments.marketplace

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.Product
import com.example.devops.database.devops.product.ProductDao
import kotlinx.coroutines.launch

class MarketPlaceViewModel(val database: ProductDao, application: Application): AndroidViewModel(application) {

    val products = database.getAllProductsLive()
    init {
        initializeLiveData()
    }

    private fun initializeLiveData(){
        viewModelScope.launch{
            deleteAllProducts()
            createProducts()
        }
    }

    private suspend fun createProducts(){
        database.insert(Product(1, "Product 1", 3.0, "", "The first of the products"))
        database.insert(Product(2, "Product 2", 3.0, "", "The second of the products"))
        database.insert(Product(3, "Product 3", 5.0, "", "The third of the products"))
    }

    private suspend fun deleteAllProducts(){
        database.clear()
    }
}