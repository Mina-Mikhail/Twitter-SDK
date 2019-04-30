package com.mina_mikhail.twitter_sdk.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.mina_mikhail.twitter_sdk.R;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public final class CommonUtils {

  private CommonUtils() {
    // This utility class is not publicly instantiable
  }

  public static ProgressDialog showLoadingDialog(Activity activity) {
    if (activity == null || activity.isFinishing()) {
      return null;
    }

    ProgressDialog progressDialog = new ProgressDialog(activity);
    progressDialog.show();
    if (progressDialog.getWindow() != null) {
      progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    progressDialog.setContentView(R.layout.progress_dialog);

    ProgressBar progressBar = progressDialog.findViewById(R.id.loading);
    Sprite circle = new Circle();
    circle.setColor(activity.getResources().getColor(R.color.libAccentColor));
    progressBar.setIndeterminateDrawable(circle);

    progressDialog.setIndeterminate(true);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    return progressDialog;
  }

  public static void hideLoadingDialog(ProgressDialog mProgressDialog, Activity activity) {
    if (activity != null && !activity.isFinishing() && mProgressDialog != null && mProgressDialog
        .isShowing()) {
      mProgressDialog.dismiss();
    }
  }
}