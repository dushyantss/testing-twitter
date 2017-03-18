package com.dushyantss.testingtwitter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dushyantss.testingtwitter.helpers.PreferenceHelper;
import com.dushyantss.testingtwitter.models.CurrentUser;

/**
 * Basic flow is: we show all the info fetched earlier( Current user should have info if someone
 * reaches this stage. On click logout, we logout and clear data. On click tweets, we show Mr. Modi's
 * tweets
 */
public class HomeActivity extends AppCompatActivity {

    private ConstraintLayout mMainView;
    private ImageView mProfileImage;

    private SharedPreferences mSharedPreferences;
    private CurrentUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mUser = PreferenceHelper.getCurrentUser(mSharedPreferences);

        initializeViews();

        setupViews();
    }

    private void initializeViews() {
        // initialize views
        mMainView = (ConstraintLayout) findViewById(R.id.main_view);
        mProfileImage = (ImageView) findViewById(R.id.profile_image);

    }

    private void setupViews() {
        if (mUser.getBackgroundColor() != null && !mUser.getBackgroundColor().isEmpty()) {
            mMainView.setBackgroundColor(Color.parseColor(mUser.getBackgroundColor()));
        }

        if (mProfileImage != null) {
            Glide.with(getApplicationContext())
                    .load(mUser.getProfileImage())
                    .asBitmap()
                    .placeholder(R.drawable.default_profile)
                    .into(mProfileImage);
        }

    }
}
