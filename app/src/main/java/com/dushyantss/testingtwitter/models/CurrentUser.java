package com.dushyantss.testingtwitter.models;

import com.twitter.sdk.android.core.models.User;

import java.util.Calendar;

/**
 * Created by Dushyant on 17-03-2017.
 */

public class CurrentUser {

    private String mId;
    private String mName;
    private String mProfileImage;
    private String mDescription;
    private long mLastUpdated;

    public CurrentUser() {

    }

    public CurrentUser(String id, String name, String profileImage, String description, long lastUpdated) {
        mId = id;
        mName = name;

        // this needs to be done because by default we get either 48x48(normal) or 73x73(bigger)
        mProfileImage = profileImage;
        if (mProfileImage.contains("_bigger")) {
            mProfileImage = mProfileImage.replace("_bigger", "_400x400");
        } else if(mProfileImage.contains("_normal")){
            mProfileImage = mProfileImage.replace("_normal", "_400x400");
        }

        mDescription = description;
        mLastUpdated = lastUpdated;
    }

    public CurrentUser(User user){
        this(user.idStr, user.name, user.profileImageUrlHttps,
                user.description, Calendar.getInstance().getTimeInMillis());
    }

    public long getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        mProfileImage = profileImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
