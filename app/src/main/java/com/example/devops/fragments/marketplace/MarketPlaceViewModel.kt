package com.example.devops.fragments.marketplace

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.Product
import com.example.devops.database.devops.product.ProductDao
import kotlinx.coroutines.launch

class MarketPlaceViewModel(val database: ProductDao, application: Application): AndroidViewModel(application) {

    init {
        initializeLiveData()
    }

    private fun initializeLiveData(){
        viewModelScope.launch{
            createProduct()
        }
    }

    private suspend fun createProduct(){
        database.insert(Product(1,"alicia"))
    }

    private suspend fun getProduct(){
        database.get(1)
    }
}