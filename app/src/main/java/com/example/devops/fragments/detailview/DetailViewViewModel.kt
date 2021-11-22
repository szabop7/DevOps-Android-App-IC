package com.example.devops.fragments.detailview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.devops.database.devops.DevOpsDatabase

class DetailViewViewModel(val database: DevOpsDatabase, application: Application): AndroidViewModel(application) {
}