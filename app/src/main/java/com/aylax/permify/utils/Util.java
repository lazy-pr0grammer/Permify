package com.aylax.permify.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.aylax.permify.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Util {
    public static final String OPEN_SOURCE_URL = "https://github.com/lazy-pr0grammer/Permify";
    public static final String POLICY_URL = "https://github.com/lazy-pr0grammer/Permify/blob/master/privacy_policy.md";

    public static void setToolbarFont(MaterialToolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(ResourcesCompat.getFont(toolbar.getContext(), R.font.manrope_bold));
            }
        }
    }

    public static void launchPackage(String pkg, @NonNull Context context) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            } else
                showToast(context, "Sorry, we couldn't open the app. It may be a system app or not installed.");
        } catch (Exception e) {
            showToast(context, e.getMessage());
        }
    }

    public static void openPackageSettings(String pkg, @NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + pkg));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void launchURL(String url, @NonNull Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showToast(context, "This link can't be opened because there is no compatible browsing app installed.");
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
