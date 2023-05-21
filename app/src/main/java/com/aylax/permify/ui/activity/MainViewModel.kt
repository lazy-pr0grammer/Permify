package com.aylax.permify.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aylax.library.model.Application
import com.aylax.permify.data.repository.ApplicationRepository

class MainViewModel : ViewModel() {
    private var repository: ApplicationRepository = ApplicationRepository()

    fun loadApplications(system: Boolean): LiveData<List<Application>> {
        return repository.getApplications(system)
    }
}