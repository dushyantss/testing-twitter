package com.dushyantss.testingtwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dushyantss.testingtwitter.helpers.PreferenceHelper;
import com.dushyantss.testingtwitter.models.CurrentUser;
import com.twitter.sdk.android.Twitter;

/**
 * Basic flow is: we show all the info fetched earlier( Current user should have info if someone
 * reaches this stage. On click logout, we logout and clear data. On click tweets, we show Mr. Modi's
 * tweets
 */
public class HomeActivity extends AppCompatActivity {

    private ConstraintLayout mMainView;
    private ImageView mProfileImage;
    private ImageView mLogoutView;
    private ImageView mTweetsVew;
    private TextView mNameView;
    private TextView mDescription;

    private SharedPreferences mSharedPreferences;
    private CurrentUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mUser = PreferenceHelper.getCurrentUser(mSharedPreferences);

        initializeViews();

        setupStaticViews();

        setupInteractiveButtons();
    }

    private void setupInteractiveButtons() {
        // on logout we logout, clear the user data and go back to login
        mLogoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Twitter.logOut();
                PreferenceHelper.setCurrentUser(mSharedPreferences, new CurrentUser());
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                finish();
            }
        });

        // on clicking tweets show Mr. Modi's top 10 tweets
        mTweetsVew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TweetsActivity.class));

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void initializeViews() {
        // initialize views
        mMainView = (ConstraintLayout) findViewById(R.id.main_view);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mLogoutView = (ImageView) findViewById(R.id.log_out);
        mTweetsVew = (ImageView) findViewById(R.id.tweets);
        mNameView = (TextView) findViewById(R.id.name);
        mDescription = (TextView) findViewById(R.id.description);

    }

    private void setupStaticViews() {

        if (mProfileImage != null) {
            Glide.with(getApplicationContext())
                    .load(mUser.getProfileImage())
                    .asBitmap()
                    .placeholder(R.drawable.default_profile)
                    .into(mProfileImage);
        }

        if (mUser.getName() != null && !mUser.getName().isEmpty() && mNameView != null) {
            mNameView.setText(mUser.getName());
        }

        if (mUser.getDescription() != null && !mUser.getDescription().isEmpty() && mDescription != null) {
            mDescription.setText(mUser.getDescription());
        }

    }
}
