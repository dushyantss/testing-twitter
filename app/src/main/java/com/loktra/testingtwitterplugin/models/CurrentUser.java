package com.loktra.testingtwitterplugin.models;

/**
 * Created by Dushyant on 17-03-2017.
 */

public class CurrentUser {

    private String mId;
    private String mName;
    private String mBackgroundColor;
    private String mProfileImage;
    private String mDescription;
    private long mLastUpdated;

    public CurrentUser() {

    }

    public CurrentUser(String id, String name, String backgroundColor, String profileImage, String description, long lastUpdated) {
        mId = id;
        mName = name;
        mBackgroundColor = backgroundColor;
        mProfileImage = profileImage;
        mDescription = description;
        mLastUpdated = lastUpdated;
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

    public String getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        mBackgroundColor = backgroundColor;
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
