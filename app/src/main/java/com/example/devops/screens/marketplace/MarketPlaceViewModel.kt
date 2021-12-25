package com.example.devops.screens.marketplace

import android.app.Application
import androidx.lifecycle.*
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.domain.Artist
import com.example.devops.domain.Product
import com.example.devops.domain.Tag
import com.example.devops.repository.DevOpsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

enum class DevOpsApiStatus { LOADING, DONE }

data class MarketplaceFilter(
    var text: String = "",
    var tags: List<Long> = emptyList()
)

class MarketPlaceViewModel(application: Application) : AndroidViewModel(application) {

    // var products: LiveData<List<ProductDatabase>>
    var filter = MutableLiveData(MarketplaceFilter())

    private val _status = MutableLiveData<DevOpsApiStatus>()

    private val devOpsDatabase = DevOpsDatabase.getInstance(application.applicationContext)
    private val devOpsRepository = DevOpsRepository(devOpsDatabase)

    private val products: LiveData<List<Product>> = devOpsRepository.productsWithTags
    val tags: LiveData<List<Tag>> = devOpsRepository.tags
    private val artists: LiveData<List<Artist>> = devOpsRepository.artists

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
    companion object {
        fun productIsFiltered(product: Product, filter: MarketplaceFilter?): Boolean {
            return filter == null ||
                    (
                            (filter.text.isEmpty() ||
                                    product.productName.contains(filter.text, true) ||
                                    product.productDescription.contains(filter.text, true)
                                    ) &&
                                    (filter.tags.isEmpty() || (product.tagList.map { it.tagId }.containsAll(filter.tags))))
        }
    }

    /***
     * Updates the listed products when the filter is changed
     */
    private fun onFilterChange(newFilter: MarketplaceFilter) {
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

    private fun performProductChange(products: List<Product>?, filter: MarketplaceFilter?) {
        productsAndFilter.value = products?.filter {
            productIsFiltered(it, filter)
        }
    }

    // set the filter for allItemsFiltered
    fun setTextFilter(newFilter: String) {
        val f = filter.value ?: MarketplaceFilter()
        f.text = newFilter

        filter.postValue(f) // apply the filter
    }

    fun setTagsFilter(tagIds: List<Int>) {
        val f = filter.value ?: MarketplaceFilter()
        f.tags = tagIds.map { it.toLong() }
        filter.postValue(f)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}