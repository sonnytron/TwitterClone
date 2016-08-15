package com.codepath.apps.twitterclone.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sonnyrodriguez on 8/2/16.
 */
public class User implements Parcelable {
    private String name;
    private long uId;
    private String screenName;
    private String profileImage;
    private String tagline;
    private int followersCount;
    private int followingCount;

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.uId = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImage = jsonObject.getString("profile_image_url");
            user.tagline = jsonObject.getString("description");
            user.followersCount = jsonObject.getInt("followers_count");
            user.followingCount = jsonObject.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public long getuId() {
        return uId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profileImage);
        dest.writeLong(this.uId);
        dest.writeString(this.screenName);
        dest.writeString(this.name);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.profileImage = in.readString();
        this.uId = in.readLong();
        this.screenName = in.readString();
        this.name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
