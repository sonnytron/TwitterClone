package com.codepath.apps.twitterclone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.codepath.apps.twitterclone.TwitterApplication;
import com.codepath.apps.twitterclone.TwitterClient;
import com.codepath.apps.twitterclone.models.EndlessScrollListener;
import com.codepath.apps.twitterclone.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sonnyrodriguez on 8/14/16.
 */
public class UserTimelineFragment extends TweetListFragment {
    private TwitterClient mClient;
    private String mScreenName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mClient = TwitterApplication.getRestClient();
        populateTimeLine();
        super.onCreate(savedInstanceState);
    }

    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    private void populateTimeLine() {
        mScreenName = getArguments().getString("screen_name");
        mClient.getUserTimeline(mScreenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (response != null) {
                    List<Tweet> tweets = Tweet.fromJsonArray(response);
                    if (tweets.size() > 0) {
                        Tweet lastTweet = tweets.get(tweets.size() - 1);
                        setSinceId(lastTweet.getId() - 1);
                        updateFragmentWithTweets(tweets);
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getContext(), "Failed Tweets!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loadMore(int pageId) {
        if (mClient == null) {
            mClient = TwitterApplication.getRestClient();
        }

        Toast.makeText(getActivity(), String.valueOf(getSinceId()) + " is sinceId", Toast.LENGTH_SHORT).show();
        mClient.getMoreUserTweets(mScreenName ,getSinceId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Tweet> tweets = Tweet.fromJsonArray(response);
                addAll(tweets);
                Tweet lastTweet = tweets.get(tweets.size() - 1);
                setSinceId(lastTweet.getId() - 1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getActivity(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
