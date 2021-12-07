package com.example.devops.screens.marketplace

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.database.devops.product.ProductDatabase
import com.example.devops.database.devops.product.ProductDao
import com.example.devops.domain.Product
import com.example.devops.network.ApiProduct
import com.example.devops.network.DevOpsApi
import com.example.devops.repository.DevOpsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

enum class DevOpsApiStatus { LOADING, ERROR, DONE }

class MarketPlaceViewModel(application: Application) : AndroidViewModel(application) {


    // var products: LiveData<List<ProductDatabase>>
    var filter = MutableLiveData<String>("%")


    private val _status = MutableLiveData<DevOpsApiStatus>()
    val status: LiveData<DevOpsApiStatus>
        get() = _status

    private val devOpsDatabase = DevOpsDatabase.getInstance(application.applicationContext)
    private val devOpsRepository = DevOpsRepository(devOpsDatabase)

    val products: LiveData<List<Product>> = devOpsRepository.products

    init {
        viewModelScope.launch {
            _status.value = DevOpsApiStatus.LOADING
            devOpsRepository.refreshProducts()
            _status.value = DevOpsApiStatus.DONE
        }
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}