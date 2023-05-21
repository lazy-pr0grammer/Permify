package com.aylax.permify.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.aylax.library.api.AppManager;
import com.aylax.library.model.Application;
import com.aylax.permify.App;
import com.aylax.permify.utils.DiskExecutor;
import java.util.List;

public class ApplicationRepository {
  public ApplicationRepository() {}

  public LiveData<List<Application>> getApplications(boolean isSystem) {
    MutableLiveData<List<Application>> result = new MutableLiveData<>();
    new DiskExecutor()
        .execute(() -> result.postValue(new AppManager(App.context).getApplications(isSystem)));

    return result;
  }
}
