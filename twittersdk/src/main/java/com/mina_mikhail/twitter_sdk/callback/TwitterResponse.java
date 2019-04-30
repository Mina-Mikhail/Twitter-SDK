package com.mina_mikhail.twitter_sdk.callback;

import com.mina_mikhail.twitter_sdk.data.model.TwitterUser;

/*
 * *
 *  * Created by Mina Mikhail on 30/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public interface TwitterResponse {

  void onTwitterLogInSuccess(TwitterUser twitterUser);

  void onTwitterLogInFail(int errorType, String errorMsg);
}