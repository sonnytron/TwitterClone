package com.codepath.apps.twitterclone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.codepath.apps.twitterclone.fragments.SingleFragmentActivity;
import com.codepath.apps.twitterclone.models.Tweet;

public class TweetDetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_TWEET_ID = "com.codepath.apps.twitterclone.Tweet";

    private Tweet mTweet;

    @Override
    protected Fragment createFragment() {
        String tweetExists = mTweet.toString();
        return new TweetDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tweet tweet = (Tweet) getIntent().getParcelableExtra(EXTRA_TWEET_ID);
        mTweet = tweet;

    }

    public static Intent newIntent(Context packageContext, Tweet tweet) {
        Intent intent = new Intent(packageContext, TweetDetailActivity.class);
        intent.putExtra(EXTRA_TWEET_ID, tweet);
        return intent;
    }
}
