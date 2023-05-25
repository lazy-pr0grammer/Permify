package com.aylax.permify.data.repository

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aylax.library.model.Permission
import com.aylax.library.util.Mode.Companion.AUTO
import com.aylax.library.util.Mode.Companion.DANGEROUS
import java.io.Serializable

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class PermissionRepository {
    fun getPermissions(bundle: Bundle, mode: Int): LiveData<List<Permission>?> {
        val result = MutableLiveData<List<Permission>?>()
        val permissions: List<Permission> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable("data", Serializable::class.java) as List<Permission>
        } else ({
            bundle.getSerializable("data") as List<*>?
        }) as List<Permission>

        for (permission in permissions) {
                if (mode == DANGEROUS) {
                    if (permission.is_dangerous == true) {
                        result.setValue(permissions)
                    } else {
                        result.setValue(permissions)
                    }
                } else if (mode == AUTO) {
                    result.value = permissions
                }
            }
        return result
    }
}