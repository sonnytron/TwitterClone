package com.codepath.apps.twitterclone;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitterclone.models.Tweet;
import com.codepath.apps.twitterclone.models.TweetPost;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends SingleFragmentActivity implements TweetListFragment.TweetCallback, TweetComposeDialogFragment.ComposeCallback {

    private TwitterClient mClient;

    @Override
    protected Fragment createFragment() {
        return new TweetListFragment();
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Intent intent = TweetDetailActivity.newIntent(this, tweet);
        startActivity(intent);
    }

    @Override
    public void onTweetCompose() {
        FragmentManager manager = getSupportFragmentManager();
        TweetComposeDialogFragment dialogFragment = TweetComposeDialogFragment.newInstance("Compose Tweet");
        dialogFragment.show(manager, "fragment_compose_tweet");
    }

    @Override
    public void composeTweet(TweetPost tweetPost) {
        mClient = TwitterApplication.getRestClient();
        mClient.postNewStatus(tweetPost, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                FragmentManager manager = getSupportFragmentManager();
                TweetListFragment listFrag =(TweetListFragment) manager.findFragmentById(R.id.fragment_container);
                listFrag.onResume();
            }
        });
    }


}
