package com.codepath.apps.twitterclone.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by sonnyrodriguez on 8/6/16.
 */
public class TweetPost implements Parcelable {
    private String mStatus;
    private String mResponseId;

    public TweetPost (String status, @Nullable String responseId) {
        mStatus = status;
        if (responseId != null) {
            mResponseId = responseId;
        }
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getResponseId() {
        return mResponseId;
    }

    public void setResponseId(String responseId) {
        mResponseId = responseId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mResponseId);
        dest.writeString(this.mStatus);
    }

    protected TweetPost(Parcel in) {
        this.mResponseId = in.readString();
        this.mStatus = in.readString();
    }

    public static final Creator<TweetPost> CREATOR = new Creator<TweetPost>() {
        @Override
        public TweetPost createFromParcel(Parcel source) {
            return new TweetPost(source);
        }

        @Override
        public TweetPost[] newArray(int size) {
            return new TweetPost[size];
        }
    };
}
