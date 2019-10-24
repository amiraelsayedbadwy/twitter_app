package com.example.twitterapp

import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.TwitterApiClient
import retrofit2.Retrofit

class TwitterApiClient (val session: TwitterSession) : TwitterApiClient(session) {



    val kTwitterApi: TwitterApi by lazy {
        val retrofit = Retrofit.Builder()


            .baseUrl("https://api.twitter.com/")
            .build()
        return@lazy retrofit.create(TwitterApi::class.java) }

}