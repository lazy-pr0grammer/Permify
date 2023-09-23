package com.aylax.permify.ui.details

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aylax.library.model.Permission
import com.aylax.permify.data.DataManager

class PermissionViewModel : ViewModel() {

    fun getPermissions(
        bundle: Bundle,
        mode: Int
    ): LiveData<List<Permission>?> {
        return DataManager.getInstance().permissionRepository.getPermissions(bundle, mode)
    }
}