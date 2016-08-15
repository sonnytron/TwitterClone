# TwitterClone
This is a CodePath Week 3 Project to Implement Twitter OAuth and REST API. The application makes use of Twitter's Rest API. 

![Twitter Clone Gif](/TweetClone.gif "An animated image showing use.")

![Twitter Clone Update Gif](/TwitterClonePartTwo.gif "Animated GIF showing update.")

## User Stories

* [x] User can sign in to Twitter using OAuth login
* [x] User can view tweets from their home timeline
* [x] Tweets display username, name and body text for each tweet
* [x] Tweets display timestamp in relative time ("4m ago, 2h ago")
* [x] As user scrolls, more tweets are loaded using Twitter's API Get Request
* [x] User can compose a new tweet  
* [x] User can initiate a composition Tweet dialog by tapping action button
* [x] User can enter the post into Tweet dialog and hit send button
* [x] Upon successful post, the list of tweets is updated with the new tweet

## Updated Stories  
  
* [x] User can tap on Profile Icon to view their profile  
* [x] User can tap on a Tweet item to view the Profile of a user
* [x] User can tap on a tab menu to switch between Home and Mentions Timeline  
* [x] As user is scrolling on any Tweet timeline, the results will continue to load

## Optional Stories  
* [x] While composing a tweet, user can see how many characters are remaining out of 140
* [x] The compose activity is replaced with a DialogFragment modal overlay
* [x] Tweet objects are built using Parcelable library to allow them to be passed between activities
* [x] Implemented recycler view instead of standard ListView for better use of resources/memory
* [x] Moved compose button into floating action button instead of menu button
* [x] Floating action button has ic_circle_add from Android materials

## Notes

The biggest challenge with this application was the use of the max_id to ensure that the newest tweets were acquired from Twitter API rest client. I was facing an issue where the first 25 tweets were repeatedly being sent. I discovered that I wasn't updating my lastTweetId when I retrieved "more" Tweets and only when I was receiving the initial payload.  

## Part Two Notes  
  
The biggest challenge for the update was that there was a mysterious issue occurring with infinite pagination in the home timeline and user profile timeline. Some times, the results would paginate and load infinitely. Other times, even with the max_id set in the TweetClient methods, the results weren't returning.  

## Time to complete

This project required 16 hours of development time with the bulk of the time being on Saturday and Sunday.  

## Part Two Time to complete  
  
Part two of this project required approximately 14 hours of development because a lot of additional stories were already completed during part one: including RecyclerView and Fragments.  

## Open Source Libraries

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android