package com.aylax.permify.data.repository;

import android.os.Build;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.aylax.library.model.Permission;
import com.aylax.library.util.Mode;
import java.io.Serializable;
import java.util.List;

public class PermissionRepository {
  public PermissionRepository() {}

  @SuppressWarnings("unchecked")
  public LiveData<List<Permission>> getPermissions(Bundle bundle, int mode) {
    MutableLiveData<List<Permission>> result = new MutableLiveData<>();
    List<Permission> permissions;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      permissions = (List<Permission>) bundle.getSerializable("data", Serializable.class);
    } else {
      permissions = (List<Permission>) bundle.getSerializable("data");
    }
    for (Permission permission : permissions) {
      if (mode == Mode.Companion.getDANGEROUS()) {
        if (Boolean.TRUE.equals(permission.is_dangerous())) {
          result.setValue(permissions);
        } else {
          result.setValue(permissions);
        }
      } else if (mode == Mode.Companion.getAUTO()) {
        result.setValue(permissions);
      }
    }
    return result;
  }
}
