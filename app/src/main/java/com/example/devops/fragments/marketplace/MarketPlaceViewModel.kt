package com.example.devops.fragments.marketplace

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.Product
import com.example.devops.database.devops.product.ProductDao
import kotlinx.coroutines.launch

class MarketPlaceViewModel(val database: ProductDao, application: Application): AndroidViewModel(application) {

    var products: LiveData<List<Product>>
    var filter = MutableLiveData<String>("%")

    init {
        initializeLiveData()
        products = Transformations.switchMap(filter) { filter ->
            database.getProductsFiltered(filter)
        }
    }

    private fun initializeLiveData(){
        viewModelScope.launch{
            deleteAllProducts()
            createProducts()
        }
    }

    private suspend fun createProducts(){
        database.insert(Product(1, "Winter", 3.0, "", "A cold winter morning"))
        database.insert(Product(2, "Fall", 3.0, "", "Brown leaves"))
        database.insert(Product(3, "Summer", 5.0, "", "Sunny days"))
    }

    private suspend fun deleteAllProducts(){
        database.clear()
    }

    // set the filter for allItemsFiltered
    fun setFilter(newFilter: String) {
        // optional: add wildcards to the filter
        val f = when {
            newFilter.isEmpty() -> "%"
            else -> "%$newFilter%"
        }
        filter.postValue(f) // apply the filter
    }
}