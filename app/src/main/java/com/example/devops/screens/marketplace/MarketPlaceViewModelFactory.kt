package com.example.devops.screens.marketplace

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MarketPlaceViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketPlaceViewModel::class.java)) {
            return MarketPlaceViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}