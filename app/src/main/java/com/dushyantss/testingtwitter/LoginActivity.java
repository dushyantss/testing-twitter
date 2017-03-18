package com.dushyantss.testingtwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dushyantss.testingtwitter.helpers.NetworkingHelper;
import com.dushyantss.testingtwitter.helpers.PreferenceHelper;
import com.dushyantss.testingtwitter.models.CurrentUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;

import retrofit2.Call;

/**
 * Basic flow is: we login with the button, if success we fetch user info and store so that we can
 * show it instantly. if failure we show error
 */
public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton mLoginButton;
    private AVLoadingIndicatorView mLoadingIndicator;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // setting up views
        mLoadingIndicator = (AVLoadingIndicatorView) findViewById(R.id.loading_indicator);
        mLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        // required callback for the widget to work
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(LoginActivity.this,
                        getString(R.string.welcome_space) + result.data.getUserName(),
                        Toast.LENGTH_SHORT).show();
                fetchUserDetails();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hideLoading();
    }

    private void hideLoading() {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.smoothToHide();
        }
    }

    private void fetchUserDetails(){
        // do the animation here
        if (mLoginButton != null) {
            mLoginButton.setVisibility(View.GONE);
        }
        showLoading();

        // start actual fetching of data
        TwitterSession session =
                Twitter.getSessionManager().getActiveSession();
        Call<User> call = Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false);

        call.enqueue(new Callback<User>() {

                    @Override
                    public void success(Result<User> userResult) {

                        // successful fetching, update user and show home screen

                        User user = userResult.data;
                        CurrentUser currentUser = new CurrentUser(user);

                        PreferenceHelper.setCurrentUser(mSharedPreferences, currentUser);
                        startHomeActivity();

                    }
                    @Override
                    public void failure(TwitterException e) {
                        // if not successful and internet is connected then we ask to relogin
                        if (NetworkingHelper.isConnected(getApplicationContext())) {
                            Toast.makeText(LoginActivity.this,
                                    R.string.fetching_info_failure,
                                    Toast.LENGTH_SHORT)
                                    .show();
                            hideLoading();
                        }

                        // else we ask to start internet and restart the app
                        else {
                            Toast.makeText(LoginActivity.this,
                                    R.string.internet_failure,
                                    Toast.LENGTH_SHORT)
                                    .show();
                            hideLoading();
                        }
                    }

                });
    }

    private void showLoading() {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.smoothToShow();
        }
    }

    private void startHomeActivity() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
