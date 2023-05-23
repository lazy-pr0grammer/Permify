package com.aylax.permify.ui.fragment

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aylax.library.model.Permission
import com.aylax.permify.data.repository.PermissionRepository

class PermissionViewModel : ViewModel() {
    private var repository: PermissionRepository = PermissionRepository()

    fun getPermissions(
        bundle: Bundle,
        mode: Int
    ): LiveData<List<Permission>> {
        return repository.getPermissions(bundle, mode)
    }
}