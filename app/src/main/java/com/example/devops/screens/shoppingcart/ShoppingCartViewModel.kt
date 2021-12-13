package com.example.devops.screens.shoppingcart

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

class ShoppingCartViewModel(application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<DevOpsApiStatus>()

    private val devOpsDatabase = DevOpsDatabase.getInstance(application.applicationContext)
    private val devOpsRepository = DevOpsRepository(devOpsDatabase)

    val shoppingCart: LiveData<List<Product>> = devOpsRepository.shoppingCart

    init {
        viewModelScope.launch {
            _status.value = DevOpsApiStatus.LOADING
            _status.value = DevOpsApiStatus.DONE
        }
    }

    fun removeFromShoppingCart(productId: Long) {
        viewModelScope.launch {
            devOpsRepository.removeShoppingItem(productId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}