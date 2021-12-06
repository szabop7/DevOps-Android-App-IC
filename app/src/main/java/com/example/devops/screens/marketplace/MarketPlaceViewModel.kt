package com.example.devops.screens.marketplace

import android.app.Application
import androidx.lifecycle.*
import com.example.devops.database.devops.product.Product
import com.example.devops.database.devops.product.ProductDao
import com.example.devops.network.ApiProduct
import com.example.devops.network.DevOpsApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.await

enum class DevOpsApiStatus { LOADING, ERROR, DONE }

class MarketPlaceViewModel(val database: ProductDao, application: Application) : AndroidViewModel(application) {

    private var _apiResponse = MutableLiveData<ApiProduct>()
    val apiResponse: LiveData<ApiProduct>
        get() = _apiResponse

    private val _status = MutableLiveData<DevOpsApiStatus>()
    val status: LiveData<DevOpsApiStatus>
        get() = _status

    var products: LiveData<List<Product>>
    var filter = MutableLiveData<String>("%")

    init {
        initializeLiveData()
        products = Transformations.switchMap(filter) { filter ->
            database.getProductsFiltered(filter)
        }
        viewModelScope.launch {
            getProductsFromAPI()
        }
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            deleteAllProducts()
            createProducts()
        }
    }

    private suspend fun createProducts() {
        database.insert(Product(1, "Winter", 3.0, "", "A cold winter morning"))
        database.insert(Product(2, "Fall", 3.0, "", "Brown leaves"))
        database.insert(Product(3, "Summer", 5.0, "", "Sunny days"))
    }

    private suspend fun deleteAllProducts() {
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

    private suspend fun getProductsFromAPI() {
        _status.value = DevOpsApiStatus.LOADING
        var getProductsDeferred = DevOpsApi.retrofitService.getProducts()

        try {
            var res = getProductsDeferred.await()
            _status.value = DevOpsApiStatus.DONE
            _apiResponse.value = res
        } catch (e: Exception) {
            _status.value = DevOpsApiStatus.ERROR
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}