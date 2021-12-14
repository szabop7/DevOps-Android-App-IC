package com.example.devops.screens.detailview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    val products: LiveData<List<Product>> = devOpsRepository.products

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

    /**
     * Checks if a [Product] is in the cart and
     * @return boolean
     */
    fun checkIfInCart(product: Product): Boolean {
        if (devOpsRepository.shoppingCart.value != null)
            return devOpsRepository.shoppingCart.value!!.contains(product)
        return false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}