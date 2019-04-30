package com.mina_mikhail.twitter_sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public final class NetworkUtils {

  private NetworkUtils() {
    // This utility class is not publicly instantiable
  }

  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
  }
}