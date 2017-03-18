package com.dushyantss.testingtwitter;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Dushyant on 17-03-2017.
 */

public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "la8bxSuxYPHRUbrQLgdZ2sief";
    private static final String TWITTER_SECRET = "ffkyxigVjxlUWHVRTxfSce3opKzQTfNREPhRdBMBLskGUd1eal";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
    }
}
