package com.example.twitterapp

import com.twitter.sdk.android.core.models.Tweet
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

public  interface TwitterApi
 {

     @GET("/1.1/statuses/user_timeline.json")

     fun userTimeline(@Query("user_id") user: Long,

                      @Query("count") count: Int? = null): Observable<List<Tweet>>
}