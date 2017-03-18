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
    private static final String TWITTER_KEY = "75vyvfoaEQfUMXQOIoOLn4boo";
    private static final String TWITTER_SECRET = "hvxtFzZiTpjIUD4IKgzn4k2ghsACMzyXY6TtnP39xMJ9OzJZTw";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
    }
}
