package com.codepath.apps.twitterclone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class TimelineFragment extends TweetListFragment {
    private TwitterClient mClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClient = TwitterApplication.getRestClient();
        populateTimeLine();
    }

    private void populateTimeLine() {
        mClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if (response != null) {
                    List<Tweet> tweets = Tweet.fromJsonArray(response);
                    Tweet lastTweet = tweets.get(tweets.size() - 1);
                    setSinceId(lastTweet.getId() - 1);
                    updateFragmentWithTweets(tweets);
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

        mClient.getMoreTweets(getSinceId(), new JsonHttpResponseHandler() {
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
