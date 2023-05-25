package com.aylax.permify.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aylax.library.model.Application
import com.aylax.permify.data.DataManager

class MainViewModel : ViewModel() {
    fun loadApplications(system: Boolean): LiveData<List<Application>> {
        return DataManager.getInstance().appRepository.getApplications(system)
    }
}