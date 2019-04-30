package com.mina_mikhail.twitter_sdk_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mina_mikhail.twitter_sdk.TwitterSDK;
import com.mina_mikhail.twitter_sdk.callback.TwitterResponse;
import com.mina_mikhail.twitter_sdk.data.model.TwitterUser;
import com.squareup.picasso.Picasso;

public class MainActivity
    extends AppCompatActivity
    implements TwitterResponse {

  private String customerKey = "YOUR_API_KEY";
  private String customerSecret = "YOUR_API_SECRET_KEY";

  private View userData;
  private ImageView userPhoto;
  private TextView userID;
  private TextView userName;
  private TextView userEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userData = findViewById(R.id.ll_user_data);
    userPhoto = findViewById(R.id.iv_user_photo);
    userID = findViewById(R.id.tv_user_id);
    userName = findViewById(R.id.tv_user_name);
    userEmail = findViewById(R.id.tv_user_email);

    findViewById(R.id.btn_log_in).setOnClickListener(
        v -> TwitterSDK.getInstance().logIn(MainActivity.this, customerKey, customerSecret, this));
  }

  @Override
  public void onTwitterLogInSuccess(TwitterUser twitterUser) {
    Picasso.get().load(twitterUser.getUserProfilePicture()).into(userPhoto);
    userID.setText(twitterUser.getUserID());
    userName.setText(twitterUser.getFullName());
    userEmail.setText(twitterUser.getUserEmail());

    userData.setVisibility(View.VISIBLE);
  }

  @Override
  public void onTwitterLogInFail(int errorType, String errorMsg) {
    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
  }
}