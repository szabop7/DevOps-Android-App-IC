package com.example.devops.screens.detailview

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.devops.database.devops.DevOpsDatabase
import com.example.devops.domain.Product
import com.example.devops.repository.DevOpsRepository
import com.example.devops.screens.marketplace.DevOpsApiStatus
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * View Model of Detail View
 */
class DetailViewViewModel(id: Long, application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<DevOpsApiStatus>()
    val status: LiveData<DevOpsApiStatus>
        get() = _status

    private val devOpsDatabase = DevOpsDatabase.getInstance(application.applicationContext)
    private val devOpsRepository = DevOpsRepository(devOpsDatabase)

    val product: LiveData<Product?> = Transformations.map(devOpsRepository.productsWithTags) {
        Log.i("Lista", it.toString())
        val p = it.firstOrNull() { product -> product.productId == id }
        Log.i("prprr", p.toString())
        p
    }

    val isInCart: LiveData<Boolean> = Transformations.map(devOpsRepository.shoppingCart) {
        it.firstOrNull() { product -> product.productId == id } != null
    }

    init {
        viewModelScope.launch {
            _status.value = DevOpsApiStatus.LOADING
            devOpsRepository.getProduct(id)
            _status.value = DevOpsApiStatus.DONE
        }
    }

    /**
     * Adds a [Product] to the cart through the repository
     */
    fun addToCart(product: Product) {
        viewModelScope.launch {
            devOpsRepository.addShoppingItem(product.productId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}