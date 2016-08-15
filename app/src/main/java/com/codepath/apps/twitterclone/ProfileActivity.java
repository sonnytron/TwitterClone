package com.codepath.apps.twitterclone;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclone.fragments.TweetComposeDialogFragment;
import com.codepath.apps.twitterclone.fragments.TweetListFragment;
import com.codepath.apps.twitterclone.fragments.UserTimelineFragment;
import com.codepath.apps.twitterclone.models.Tweet;
import com.codepath.apps.twitterclone.models.TweetPost;
import com.codepath.apps.twitterclone.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity implements TweetListFragment.TweetCallback, TweetComposeDialogFragment.ComposeCallback {
    private TwitterClient mClient;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClient = TwitterApplication.getRestClient();
        setContentView(R.layout.activity_profile);

        // pass in screenName from activity
        String screenName = getIntent().getStringExtra("screen_name");

        if (screenName != null) {
            mClient.getOtherUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    try {
                        JSONObject userObject = response.getJSONObject(0);
                        mUser = User.fromJson(userObject);
                        getSupportActionBar().setTitle(mUser.getScreenName());
                        populateProfileHeader(mUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } else {
            mClient.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    mUser = User.fromJson(response);
                    // My current user account information
                    getSupportActionBar().setTitle(mUser.getScreenName());
                    populateProfileHeader(mUser);
                }
            });
        }

        if (savedInstanceState == null) {
            UserTimelineFragment timelineFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.tweetsFrame, timelineFragment);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");
        Picasso.with(this).load(user.getProfileImage()).into(ivProfile);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {

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

            }
        });
    }
}
