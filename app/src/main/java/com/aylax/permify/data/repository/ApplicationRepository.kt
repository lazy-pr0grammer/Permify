package com.aylax.permify.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aylax.library.api.AppManager
import com.aylax.library.model.Application
import com.aylax.permify.utils.DiskExecutor

class ApplicationRepository {
    fun getApplications(context:Context,isSystem: Boolean): LiveData<List<Application>> {
        val result = MutableLiveData<List<Application>>()
        DiskExecutor()
            .execute { result.postValue(AppManager(context).getApplications(isSystem)) }
        return result
    }
}