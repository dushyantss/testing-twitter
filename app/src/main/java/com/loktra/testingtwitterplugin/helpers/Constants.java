package com.loktra.testingtwitterplugin.helpers;

import com.loktra.testingtwitterplugin.BuildConfig;

/**
 * Created by Dushyant on 17-03-2017.
 *
 * Helper class to hold all the constants
 */

public class Constants {

    public static final String USER_ID = BuildConfig.APPLICATION_ID + "user_id";
    public static final String USER_NAME = BuildConfig.APPLICATION_ID + "user_name";
    public static final String USER_BACKGROUND_COLOR = BuildConfig.APPLICATION_ID + "user_background_color";
    public static final String USER_PROFILE_IMAGE = BuildConfig.APPLICATION_ID + "user_profile_image";
    public static final String USER_DESCRIPTION = BuildConfig.APPLICATION_ID + "user_description";
    public static final String USER_LAST_UPDATED = BuildConfig.APPLICATION_ID + "user_last_updated";
    public static final long ONE_DAY = 1000 * 60 * 60 * 24;
}
