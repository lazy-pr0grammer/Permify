package com.aylax.permify.utils;

import android.view.View;
import android.widget.TextView;
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
}
