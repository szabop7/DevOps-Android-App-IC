package com.example.devops.screens.detailview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailViewViewModelFactory(private val id: Long, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewViewModel::class.java)) {
            return DetailViewViewModel(id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}