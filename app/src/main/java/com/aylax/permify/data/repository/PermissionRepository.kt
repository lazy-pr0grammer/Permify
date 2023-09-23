package com.aylax.permify.data.repository

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aylax.library.model.Permission

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class PermissionRepository {
    fun getPermissions(bundle: Bundle, mode: Int): LiveData<List<Permission>?> {
        val result = MutableLiveData<List<Permission>?>()
        val permissions: List<Permission> =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) bundle.getSerializable("data") as? List<Permission>
                ?: emptyList()
            else (bundle.getSerializable("data") as? List<Permission>) ?: emptyList()


        for (permission in permissions) {
            if (mode == 0) {
                if (permission.isDangerous == true) {
                    result.setValue(permissions)
                } else {
                    result.setValue(permissions)
                }
            } else if (mode == 1) {
                result.value = permissions
            }
        }
        return result
    }
}