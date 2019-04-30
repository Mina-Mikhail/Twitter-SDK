package com.mina_mikhail.twitter_sdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.mina_mikhail.twitter_sdk.callback.TwitterResponse;
import com.mina_mikhail.twitter_sdk.data.model.TwitterUser;
import com.mina_mikhail.twitter_sdk.utils.CommonUtils;
import com.mina_mikhail.twitter_sdk.utils.NetworkUtils;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;
import retrofit2.Call;

import static com.mina_mikhail.twitter_sdk.data.enums.ErrorCode.ERROR_NO_INTERNET;
import static com.mina_mikhail.twitter_sdk.data.enums.ErrorCode.ERROR_OTHER;
import static com.mina_mikhail.twitter_sdk.data.enums.ErrorCode.ERROR_USER_CANCELLED;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class TwitterSignInActivity
    extends Activity {

  private ProgressDialog mProgressDialog;
  private TwitterLoginButton loginButton;

  public static void open(Context context) {
    Intent intent = new Intent(context, TwitterSignInActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    makeStatusBarTransparent();

    if (!NetworkUtils.isNetworkConnected(this)) {
      getResponse()
          .onTwitterLogInFail(ERROR_NO_INTERNET, getResources().getString(R.string.no_internet));
      finish();
    } else {
      initTwitter();
    }
  }

  private void makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
    }
  }

  private void initTwitter() {
    TwitterConfig config = new TwitterConfig.Builder(this)
        .logger(new DefaultLogger(Log.DEBUG))
        .twitterAuthConfig(new TwitterAuthConfig(getCustomerKey(), getCustomerSecret()))
        .debug(false)
        .build();
    Twitter.initialize(config);

    loginButton = new TwitterLoginButton(this);
    loginButton.setCallback(new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        logInUsingTwitter(session);
      }

      @Override
      public void failure(TwitterException exception) {
        getResponse()
            .onTwitterLogInFail(ERROR_OTHER, getResources().getString(R.string.some_error));
      }
    });

    loginButton.performClick();
  }

  private void logInUsingTwitter(TwitterSession session) {
    showLoading();

    AccountService accountService = new TwitterApiClient(session).getAccountService();
    Call<User> callback = accountService.verifyCredentials(true, true, true);
    callback.clone().enqueue(new Callback<User>() {
      @Override
      public void success(Result<User> result) {
        TwitterUser user = new TwitterUser();
        user.setUserID(String.valueOf(session.getUserId()));
        user.setFullName(result.data.name);
        user.setUserEmail(result.data.email == null ? "" : result.data.email);
        user.setUserProfilePicture(result.data.profileImageUrl.replace("_normal", ""));

        new Handler().postDelayed(() -> {
          hideLoading();
          getResponse().onTwitterLogInSuccess(user);
          finish();
        }, 1000);
      }

      @Override
      public void failure(TwitterException exception) {
        getResponse()
            .onTwitterLogInFail(ERROR_OTHER, getResources().getString(R.string.some_error));
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    loginButton.onActivityResult(requestCode, resultCode, data);
  }

  private TwitterSDK getTwitterInstance() {
    return TwitterSDK.getInstance();
  }

  private TwitterResponse getResponse() {
    return getTwitterInstance().getResponse();
  }

  private String getCustomerKey() {
    return getTwitterInstance().getCustomerKey();
  }

  private String getCustomerSecret() {
    return getTwitterInstance().getCustomerSecret();
  }

  public void showLoading() {
    hideLoading();
    mProgressDialog = CommonUtils.showLoadingDialog(this);
  }

  public void hideLoading() {
    CommonUtils.hideLoadingDialog(mProgressDialog, this);
  }

  @Override
  public void onBackPressed() {
    getResponse()
        .onTwitterLogInFail(ERROR_USER_CANCELLED,
            getResources().getString(R.string.user_cancelled));
    finish();
  }
}