package com.codepath.apps.twitterclone;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Request;

import android.content.Context;
import android.util.Log;

import com.codepath.apps.twitterclone.models.TweetPost;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "u4kYPe1aPOpo5FuH6ALa7eUSK";       // Change this
	public static final String REST_CONSUMER_SECRET = "BCQacVoQe31WPKQiGAKBG6IyOIVl55X0mNxPGsb55KDCKlC4Bu"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://twitterclone"; // Change this (here and in manifest)

	public static final String REST_POST_TWEET_URL = "/statuses/update.json";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	// Method -> Endpoint

	// HomeTimeLine - Request

	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);

		// Execute request
		getClient().get(apiUrl, params, handler);
	}

	public void getMoreTweets(long fromId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		params.put("max_id", fromId);

		getClient().get(apiUrl, params, handler);
	}

	public void postNewStatus(TweetPost post, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("status", post.getStatus());

		getClient().post(getApiUrl(REST_POST_TWEET_URL), params, handler);
	}

	public void getMentionsTimeline(long fromId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (fromId != 0) {
			params.put("max_id", fromId);
		}
		params.put("since_id", 1);

		getClient().get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		getClient().get(apiUrl, params, handler);
	}

	public void getMoreUserTweets(String screenName, long sinceId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (screenName != null) {
			params.put("screen_name", screenName);
		}
		if (sinceId != 0 && sinceId != -1) {
			params.put("max_id", sinceId);
		}
		params.put("since_id", 1);
		getClient().get(apiUrl, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);
	}

	public void getOtherUserInfo(String username, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("/users/lookup.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", username);
		getClient().get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}