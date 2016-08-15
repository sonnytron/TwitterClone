package com.codepath.apps.twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterclone.fragments.MentionsFragment;
import com.codepath.apps.twitterclone.fragments.SingleFragmentActivity;
import com.codepath.apps.twitterclone.fragments.TimelineFragment;
import com.codepath.apps.twitterclone.fragments.TweetComposeDialogFragment;
import com.codepath.apps.twitterclone.fragments.TweetListFragment;
import com.codepath.apps.twitterclone.models.Tweet;
import com.codepath.apps.twitterclone.models.TweetPost;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends AppCompatActivity implements TweetListFragment.TweetCallback, TweetComposeDialogFragment.ComposeCallback {

    private TwitterClient mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ViewPager viewPager = (ViewPager) findViewById(R.id.tweet_pager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tweet_strip);
        tabStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.myProfile) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        // Return order of Fragments in viewPager
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimelineFragment();
            } else if (position == 1) {
                return new MentionsFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("screen_name", tweet.getUser().getScreenName());
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
