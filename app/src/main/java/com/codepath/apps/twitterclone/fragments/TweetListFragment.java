package com.codepath.apps.twitterclone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.TwitterApplication;
import com.codepath.apps.twitterclone.TwitterClient;
import com.codepath.apps.twitterclone.models.EndlessScrollListener;
import com.codepath.apps.twitterclone.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sonnyrodriguez on 8/6/16.
 */
public class TweetListFragment extends Fragment {
    private FloatingActionButton mComposeBtn;
    private RecyclerView mRecyclerView;
    private TweetCallback mCallback;
    private TweetAdapter mAdapter;
    private ArrayList<Tweet> mTweets;
    private long lastTweetId = 0;

    public interface TweetCallback {
        void onTweetSelected(Tweet tweet);
        void onTweetCompose();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tweet_recycler, container, false);
        mComposeBtn = (FloatingActionButton) view.findViewById(R.id.compose_tweet_float);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.tweet_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMore(page);
            }
        });

        mComposeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onTweetCompose();
                onPause();
            }
        });

        mTweets = new ArrayList<>();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (TweetCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateFragmentWithTweets(List<Tweet> tweets) {
        if (mAdapter == null) {
            mAdapter = new TweetAdapter(tweets);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTweets(tweets);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void loadMore(int pageId) {

    }

    public void addAll(List<Tweet> tweets) {
        if (mAdapter == null) {
            mAdapter = new TweetAdapter(tweets);
            mRecyclerView.setAdapter(mAdapter);
        }
        mRecyclerView.setAdapter(mAdapter);
        mTweets.addAll(tweets);
        mAdapter.notifyDataSetChanged();
    }

    public void setSinceId(Long id) {
        lastTweetId = id;
    }

    public long getSinceId() {
        return lastTweetId;
    }

    private class TweetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private com.codepath.apps.twitterclone.models.Tweet mTweet;

        private TextView tvTitle;
        private TextView tvBody;
        private ImageView ivProfile;
        private TextView tvTimeStamp;

        public TweetHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView)itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView)itemView.findViewById(R.id.tvBody);
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
        }

        public void bindTweet(Tweet tweet) {
            mTweet = tweet;
            tvTitle.setText(mTweet.getUser().getScreenName());
            tvBody.setText(mTweet.getBody());
            tvTimeStamp.setText(getRelativeTimeAgo(mTweet.getCreatedAt()));
            updateImageView();
        }

        @Override
        public void onClick(View view) {
            mCallback.onTweetSelected(mTweet);
        }

        private void updateImageView() {
            ivProfile.setImageResource(android.R.color.transparent);
            if (mTweet.getUser().getProfileImage().length() > 0) {
                Picasso.with(getContext()).load(mTweet.getUser().getProfileImage()).into(ivProfile);
            }
        }

        public String getRelativeTimeAgo(String rawJsonDate) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            sf.setLenient(true);

            String relativeDate = "";
            try {
                long dateMillis = sf.parse(rawJsonDate).getTime();
                relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return relativeDate;
        }
    }

    private class TweetAdapter extends RecyclerView.Adapter<TweetHolder> {
        private List<Tweet> mTweets;
        private LayoutInflater mInflater;

        public TweetAdapter(List<Tweet> tweets) {
            mTweets = tweets;
        }

        @Override
        public TweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mInflater = LayoutInflater.from(parent.getContext());
            View view = mInflater.inflate(R.layout.item_tweet, parent, false);
            return new TweetHolder(view);
        }

        @Override
        public void onBindViewHolder(TweetHolder holder, int position) {
            Tweet tweet = mTweets.get(position);
            holder.bindTweet(tweet);
        }

        @Override
        public int getItemCount() {
            return (mTweets != null) ? mTweets.size() : 0;
        }

        public void setTweets(List<Tweet> tweets) {
            mTweets = tweets;
        }


    }
}
