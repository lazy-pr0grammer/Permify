package com.aylax.permify.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aylax.library.api.AppManager
import com.aylax.library.model.Application
import com.aylax.permify.App
import com.aylax.permify.utils.DiskExecutor

class ApplicationRepository {
    fun getApplications(isSystem: Boolean): LiveData<List<Application>> {
        val result = MutableLiveData<List<Application>>()
        DiskExecutor()
            .execute { result.postValue(AppManager(App.context).getApplications(isSystem)) }
        return result
    }
}