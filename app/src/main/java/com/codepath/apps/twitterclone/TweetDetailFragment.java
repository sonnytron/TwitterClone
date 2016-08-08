package com.codepath.apps.twitterclone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclone.models.Tweet;
import com.squareup.picasso.Picasso;

/**
 * Created by sonnyrodriguez on 8/6/16.
 */
public class TweetDetailFragment extends Fragment {
    private static final String ARG_TWEET = "com.codepath.apps.twitterclone.Tweet";

    private Tweet mTweet;

    private ImageView ivProfile;
    private TextView tvUsername;
    private TextView tvBody;
    private Button btReply;

    public static Intent newIntent(Context packageContext, Tweet tweet) {
        Intent intent = new Intent(packageContext, TweetDetailFragment.class);
        intent.putExtra(ARG_TWEET, tweet);
        return intent;
    }

    public static TweetDetailFragment newInstance(Tweet tweet) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TWEET, tweet);

        TweetDetailFragment fragment = new TweetDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTweet = getArguments().getParcelable(ARG_TWEET);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweet_detail, container, false);
        tvUsername = (TextView) v.findViewById(R.id.tvUsernameDetail);
        tvBody = (TextView) v.findViewById(R.id.tvBodyDetail);
        ivProfile = (ImageView) v.findViewById(R.id.ivProfileDetail);
        loadImageView();
        return v;
    }

    public void loadImageView() {
        if (mTweet.getUser().getProfileImage().length() > 0) {
            Picasso.with(getContext()).load(mTweet.getUser().getProfileImage()).into(ivProfile);
        }
    }
}
