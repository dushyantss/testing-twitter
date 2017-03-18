package com.dushyantss.testingtwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.dushyantss.testingtwitter.helpers.Constants;
import com.dushyantss.testingtwitter.helpers.NetworkingHelper;
import com.dushyantss.testingtwitter.helpers.PreferenceHelper;
import com.dushyantss.testingtwitter.models.CurrentUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;

import retrofit2.Call;

/**
 * Basic flow is: we show animation. on animation end we check and show appropriate activity.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private ConstraintLayout mRootLayout;
    private ImageView mBackgroundImageView;
    private AVLoadingIndicatorView mLoadingIndicator;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Initialize views
        mRootLayout = (ConstraintLayout) findViewById(R.id.main_view);
        mBackgroundImageView = (ImageView) findViewById(R.id.background_image);
        mLoadingIndicator = (AVLoadingIndicatorView) findViewById(R.id.loading_indicator);

        //Bottom-Up Animation for the Screen
        Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.splash_bottom_to_up);

        mRootLayout.startAnimation(bottomUp);

        // start the zoom animation
        Runnable zoomAnimationRunnable = getZoomAnimationRunnable();
        Handler handler = new Handler();
        handler.postDelayed(zoomAnimationRunnable, 500);
    }

    // this class will provide us with the runnable we use for the zoom animation
    private Runnable getZoomAnimationRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Animation zoom = AnimationUtils.loadAnimation(SplashScreenActivity.this,
                            R.anim.splash_zoom_in_full_screen);

                    zoom.setAnimationListener(getZoomAnimationListener());

                    mBackgroundImageView.startAnimation(zoom);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        return runnable;
    }

    // returns the animation listener that will start the appropriate activity after animation ends
    private Animation.AnimationListener getZoomAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startRequiredActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    // invoked after the zoom animation ends, to start the required activity, or update data and
    // then start the activity
    private void startRequiredActivity() {

        // There is no logged in user
        if (Twitter.getSessionManager().getActiveSession() == null) {
            startLoginActivity();
        }
        // The user data is either old or user data was not fetched after logging in
        else if(needToUpdateUserData()){
            showLoading();
            updateUserData();
        }
        // Everything is fine, show HomeActivity
        else{
            startHomeActivity();
        }

    }

    private void showLoading() {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.smoothToShow();
        }
    }

    // we update the user data, if we could not update we check, if internet error, we ask them to restart
    // app after turning on internet. Else we ask them to relogin
    private void updateUserData() {
        TwitterSession session =
                Twitter.getSessionManager().getActiveSession();
        Call<User> call = Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false);

        call.enqueue(new Callback<User>() {

            @Override
            public void success(Result<User> userResult) {

                // if successful, update user

                User user = userResult.data;
                CurrentUser currentUser = new CurrentUser(user);

                PreferenceHelper.setCurrentUser(mSharedPreferences, currentUser);
                hideLoading();
                startHomeActivity();

            }
            @Override
            public void failure(TwitterException e) {

                // if not successful and internet is connected then we ask to relogin
                if (NetworkingHelper.isConnected(getApplicationContext())) {
                    Toast.makeText(SplashScreenActivity.this,
                            R.string.fetching_info_failure,
                            Toast.LENGTH_SHORT)
                            .show();
                    hideLoading();
                    startLoginActivity();
                }
                // else we ask to start internet and restart the app
                else {
                    Toast.makeText(SplashScreenActivity.this,
                            R.string.internet_failure,
                            Toast.LENGTH_SHORT)
                            .show();
                    hideLoading();
                }
            }

        });
    }

    private void hideLoading() {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.smoothToHide();
        }
    }

    private void startLoginActivity() {
        startWithIntent(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void startHomeActivity() {
        startWithIntent(new Intent(getApplicationContext(), HomeActivity.class));
    }

    // check whether we should update the user
    private boolean needToUpdateUserData() {
        // get the current user
        CurrentUser user = PreferenceHelper.getCurrentUser(mSharedPreferences);
        long current = Calendar.getInstance().getTimeInMillis();
        boolean shouldUpdate = current - user.getLastUpdated() > Constants.ONE_DAY;

        // either the user data did not get fetched then user.getId() will be null else the
        // data is too old and shouldUpdate is true
        return user.getId() == null || shouldUpdate;
    }

    // helper method to start an activity with Intent
    private void startWithIntent(Intent intent) {
        mLoadingIndicator.smoothToHide();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        finish();
    }

}
