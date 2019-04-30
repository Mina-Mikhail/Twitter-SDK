package com.mina_mikhail.twitter_sdk.utils;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class Constants {

  // Profile Picture url
  public static final String PROFILE_PIC_URL =
      "https://graph.facebook.com/%1$s/picture?type=large";

  // Facebook user fields
  public static final String FB_USER_ID = "id";
  public static final String FB_USER_NAME = "name";
  public static final String FB_USER_EMAIL = "email";
  public static final String FB_USER_PROFILE_PIC = "public_profile";

  // Graph Request Parameters
  public static final String REQUEST_FIELDS = "fields";
  public static final String REQUEST_PARAMETERS =
      FB_USER_ID + ", " + FB_USER_NAME + ", " + FB_USER_EMAIL;
}