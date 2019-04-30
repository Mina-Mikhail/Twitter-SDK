package com.mina_mikhail.twitter_sdk.data.model;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class TwitterUser {

  private String userID;
  private String fullName;
  private String userEmail;
  private String userProfilePicture;

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserProfilePicture() {
    return userProfilePicture;
  }

  public void setUserProfilePicture(String userProfilePicture) {
    this.userProfilePicture = userProfilePicture;
  }
}