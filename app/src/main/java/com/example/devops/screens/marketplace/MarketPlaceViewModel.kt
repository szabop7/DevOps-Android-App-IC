package com.example.devops.screens.marketplace

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.domain.Product
import com.example.devops.repository.DevOpsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

enum class DevOpsApiStatus { LOADING, ERROR, DONE }

class MarketPlaceViewModel(application: Application) : AndroidViewModel(application) {

    // var products: LiveData<List<ProductDatabase>>
    var filter = MutableLiveData<String>("")

    private val _status = MutableLiveData<DevOpsApiStatus>()
    val status: LiveData<DevOpsApiStatus>
        get() = _status

    private val devOpsDatabase = DevOpsDatabase.getInstance(application.applicationContext)
    private val devOpsRepository = DevOpsRepository(devOpsDatabase)

    val products: LiveData<List<Product>> = devOpsRepository.products

    // This LiveData triggers onChanges when both filter or products change
    val productsAndFilter = MediatorLiveData<List<Product>>()

    init {
        productsAndFilter.addSource(filter, this::onFilterChange)
        productsAndFilter.addSource(products, this::onProductChange)
        viewModelScope.launch {
            _status.value = DevOpsApiStatus.LOADING
            devOpsRepository.refreshProducts()
            _status.value = DevOpsApiStatus.DONE
        }
    }

    /***
     * Checks if a [Product] is currently filtered by [filter]
     * @return If the product should be visible with the given filter
     */
    fun productIsFiltered(product: Product, filter: String?): Boolean {
        return filter == null || filter.isEmpty() ||
                product.productName.contains(filter, true) ||
                product.productDescription.contains(filter, true)
    }

    fun onFilterChange(newFilter: String) {
        Log.i("Filter change", "$newFilter filtering $products ${products.value?.toString()}")
        productsAndFilter.value = products.value?.filter {
            productIsFiltered(it, newFilter)
        }
    }

    fun onProductChange(newProducts: List<Product>) {
        Log.i("Products change", "$filter for ${filter.value} filtering $newProducts")
        productsAndFilter.value = newProducts.filter {
            productIsFiltered(it, filter.value)
        }
    }

    // set the filter for allItemsFiltered
    fun setFilter(newFilter: String) {
        filter.postValue(newFilter) // apply the filter
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}