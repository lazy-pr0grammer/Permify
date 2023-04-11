package com.aylax.permify.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.Contract;

public class Permission implements Parcelable {
    public static final Creator<Permission> CREATOR = new Creator<Permission>() {
        @NonNull
        @Contract("_ -> new")
        @Override
        public Permission createFromParcel(Parcel in) {
            return new Permission(in);
        }

        @NonNull
        @Contract(value = "_ -> new", pure = true)
        @Override
        public Permission[] newArray(int size) {
            return new Permission[size];
        }
    };
    private String permission_name;

    protected Permission(@NonNull Parcel in) {
        permission_name = in.readString();
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(permission_name);
    }
}
