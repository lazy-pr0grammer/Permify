package com.aylax.permify.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aylax.library.model.Application
import com.aylax.permify.data.DataManager

class MainViewModel : ViewModel() {
    fun loadApplications(context: Context, system: Boolean): LiveData<List<Application>> {
        return DataManager.getInstance().appRepository.getApplications(context, system)
    }
}