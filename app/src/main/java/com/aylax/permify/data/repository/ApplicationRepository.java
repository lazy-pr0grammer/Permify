package com.aylax.permify.data.repository;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.aylax.permify.App;
import com.aylax.permify.data.model.Application;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository {
  private final PackageManager packageManager;

  public ApplicationRepository() {
    packageManager = App.context.getPackageManager();
  }

  public LiveData<List<Application>> getApplications(boolean isSystem) {
    List<Application> applications = new ArrayList<>();
    MutableLiveData<List<Application>> result = new MutableLiveData<>();
    @SuppressLint("QueryPermissionsNeeded")
    List<ApplicationInfo> applicationInfos =
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    for (ApplicationInfo infos : applicationInfos) {
      Application app = new Application();
      app.setPkg_name(infos.packageName);
      app.setApp_icon(infos.loadIcon(packageManager));
      app.setApp_name(infos.loadLabel(packageManager).toString());
      if (isSystem) {
        if ((infos.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
          app.set_system(true);
          applications.add(app);
        }
      } else {
        if ((infos.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
          app.set_system(false);
          applications.add(app);
        }
      }
    }
    result.setValue(applications);

    return result;
  }
}
