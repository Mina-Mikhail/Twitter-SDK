package com.mina_mikhail.twitter_sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import com.mina_mikhail.twitter_sdk.callback.TwitterResponse;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class TwitterSDK {

  @SuppressLint("StaticFieldLeak")
  private static volatile TwitterSDK INSTANCE;

  private Context context;
  private String customerKey;
  private String customerSecret;
  private TwitterResponse response;

  public static synchronized TwitterSDK getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TwitterSDK();
    }
    return INSTANCE;
  }

  public void logIn(Context context, String customerKey, String customerSecret,
      @NonNull TwitterResponse response) {
    this.context = context;
    this.customerKey = customerKey;
    this.customerSecret = customerSecret;
    this.response = response;

    initTwitter();
  }

  private void initTwitter() {
    TwitterSignInActivity.open(context);
  }

  TwitterResponse getResponse() {
    return response;
  }

  String getCustomerKey() {
    return customerKey;
  }

  String getCustomerSecret() {
    return customerSecret;
  }
}