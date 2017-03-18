package com.dushyantss.testingtwitter.helpers;

import android.content.SharedPreferences;

import com.dushyantss.testingtwitter.models.CurrentUser;

/**
 * Created by Dushyant on 17-03-2017.
 *
 * Helper class for SharedPreferences related things. We get a CurrentUser object to work with
 * which is much easier
 */

public class PreferenceHelper {

    public static CurrentUser getCurrentUser(SharedPreferences preferences) {
        String id = preferences.getString(Constants.USER_ID, null);
        String name = preferences.getString(Constants.USER_NAME, null);
        String profileImage = preferences.getString(Constants.USER_PROFILE_IMAGE, null);
        String description = preferences.getString(Constants.USER_DESCRIPTION, null);
        long lastUpdated = preferences.getLong(Constants.USER_LAST_UPDATED, 0);

        return new CurrentUser(id, name, profileImage, description, lastUpdated);
    }

    public static void setCurrentUser(SharedPreferences preferences, CurrentUser user) {
        preferences.edit()
                .putString(Constants.USER_ID, user.getId())
                .putString(Constants.USER_NAME, user.getName())
                .putString(Constants.USER_PROFILE_IMAGE, user.getProfileImage())
                .putString(Constants.USER_DESCRIPTION, user.getDescription())
                .putLong(Constants.USER_LAST_UPDATED, user.getLastUpdated())
                .apply();
    }
}
