package com.codepath.apps.twitterclone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.twitterclone.R;
import com.codepath.apps.twitterclone.models.TweetPost;

/**
 * Created by sonnyrodriguez on 8/7/16.
 */
public class TweetComposeDialogFragment extends DialogFragment {
    private EditText etCompose;
    private TextView tvCharCount;
    private TweetPost mTweetPost;
    private ComposeCallback mCallback;

    public interface ComposeCallback {
        void composeTweet(TweetPost tweetPost);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        return view;
    }

    public TweetComposeDialogFragment() {

    }

    public static TweetComposeDialogFragment newInstance(String title) {
        TweetComposeDialogFragment fragment = new TweetComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCharCount = (TextView) view.findViewById(R.id.tv_tweet_limit);
        etCompose = (EditText) view.findViewById(R.id.et_compose_tweet);
        mTweetPost = new TweetPost("", null);
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer textCount = 140 - charSequence.length();
                String charRem = textCount.toString() + " remaining";
                tvCharCount.setText(charRem);
                if (mTweetPost != null) {
                    mTweetPost.setStatus(charSequence.toString());
                } else {
                    mTweetPost = new TweetPost(charSequence.toString(), null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCompose.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    String tweetText = textView.getText().toString();
                    if (mTweetPost != null) {
                        mTweetPost.setStatus(tweetText);
                        mCallback.composeTweet(mTweetPost);
                    } else {
                        mTweetPost = new TweetPost(tweetText, null);
                        mCallback.composeTweet(mTweetPost);
                    }
                    dismiss();
                }

                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ComposeCallback) context;
    }
}
