package com.aylax.permify.data;

import com.aylax.permify.data.repository.ApplicationRepository;
import com.aylax.permify.data.repository.PermissionRepository;

public class DataManager {
    private static DataManager INSTANCE;

    private DataManager() {
    }

    public static synchronized DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public ApplicationRepository getAppRepository() {
        return new ApplicationRepository();
    }

    public PermissionRepository getPermissionRepository() {
        return new PermissionRepository();
    }
}
