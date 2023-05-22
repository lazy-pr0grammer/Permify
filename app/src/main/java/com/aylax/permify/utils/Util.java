package com.aylax.permify.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import com.aylax.permify.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Util {
  public static void setToolbarFont(MaterialToolbar toolbar) {
    for (int i = 0; i < toolbar.getChildCount(); i++) {
      View view = toolbar.getChildAt(i);
      if (view instanceof TextView) {
        ((TextView) view)
            .setTypeface(ResourcesCompat.getFont(toolbar.getContext(), R.font.manrope_bold));
      }
    }
  }

  public static void launchPackage(String pkg, @NonNull Activity activity) {
    Intent intent = activity.getPackageManager().getLaunchIntentForPackage(pkg);
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    if (intent != null) activity.startActivity(intent);
    else showToast(activity, "Failed to open app!");
  }

  public static void showToast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }
}
