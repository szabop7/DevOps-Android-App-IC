package com.example.devops.screens.marketplace

import android.app.Application
import androidx.lifecycle.*
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.domain.Artist
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
    val artists: LiveData<List<Artist>> = devOpsRepository.artists

    // This LiveData triggers onChanges when both filter or products change
    val productsAndFilter = MediatorLiveData<List<Product>>()

    init {
        productsAndFilter.addSource(filter, this::onFilterChange)
        productsAndFilter.addSource(products, this::onProductChange)
        productsAndFilter.addSource(artists, this::onArtistDataChanged)
        viewModelScope.launch {
            _status.value = DevOpsApiStatus.LOADING
            devOpsRepository.refreshProducts()
            // devOpsRepository.refreshArtists()
            _status.value = DevOpsApiStatus.DONE
        }
    }

    /***
     * Checks if a [Product] is currently filtered by [filter]
     * @return If the product should be visible with the given filter
     */
    private fun productIsFiltered(product: Product, filter: String?): Boolean {
        return filter == null || filter.isEmpty() ||
                product.productName.contains(filter, true) ||
                product.productDescription.contains(filter, true)
    }

    /***
     * Updates the listed products when the filter is changed
     */
    private fun onFilterChange(newFilter: String) {
        performProductChange(products.value, newFilter)
    }

    /***
     * Updates the listed products when new products are fetched
     */
    private fun onProductChange(newProducts: List<Product>) {
        performProductChange(newProducts, filter.value)
    }

    /***
     * Refreshes listed products when new artist data is fetched
     */
    private fun onArtistDataChanged(ignored: List<Artist>) {
        performProductChange(products.value, filter.value)
    }

    private fun performProductChange(products: List<Product>?, filter: String?) {
        productsAndFilter.value = products?.filter {
            productIsFiltered(it, filter)
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