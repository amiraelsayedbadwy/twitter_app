package com.example.twitterapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.TwitterAuthProvider

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class HomePage : AppCompatActivity() {
    private var btn_TwitterLogin: TwitterLoginButton? = null
    private var userName:TextView?=null
    private var PhoneNumber:TextView?=null
    private var email:TextView?=null
    private var tweetListView: ListView? = null
    private var id:Long?=null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntialiseTwitter()
        setContentView(R.layout.activity_home_page)

          Intialize()
    }

    fun IntialiseTwitter() {

        val authConfig = TwitterAuthConfig(
            getString(R.string.twitter_consumer_key),
            getString(R.string.twitter_consumer_secret))
        val twitterConfig = TwitterConfig.Builder(this)
            .twitterAuthConfig(authConfig)
            .build()
        Twitter.initialize(twitterConfig)

    }
 fun Intialize()
 {
     mAuth = FirebaseAuth.getInstance()
     btn_TwitterLogin = findViewById<View>(R.id.twitterLoginButton) as TwitterLoginButton
     userName=findViewById<View>(R.id.username_tv)as TextView
     PhoneNumber=findViewById<View>(R.id.PhoneNumber_tv)as TextView
     email=findViewById<View>(R.id.email_tv)as TextView
     tweetListView=findViewById<View>(R.id.tweetlist)as ListView
     btn_TwitterLogin!!.callback = object : Callback<TwitterSession>() {

         override fun success(result: Result<TwitterSession>) {

             Log.d("twitter", "twitterLogin:success$result")

             handleTwitterSession(result.data)

         }

         override fun failure(exception: TwitterException) {

             Log.w("twitter", "twitterLogin:failure", exception)



         }

     }
 }
    public override fun onStart() {

        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.

        val currentUser = mAuth!!.currentUser


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        btn_TwitterLogin!!.onActivityResult(requestCode, resultCode, data)

    }
    private fun handleTwitterSession(session: TwitterSession) {

        Log.d("twitter", "handleTwitterSession:$session")

        val credential = TwitterAuthProvider.getCredential(
            session.authToken.token,
            session.authToken.secret)
        mAuth!!.signInWithCredential(credential)

            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information

                    Log.i("twitterlogin", "signInWithCredential:success")

                    val user = mAuth!!.currentUser
                    updateUI(user)
                } else {

                    // If sign in fails, display a message to the user.

                    Log.w("twitter", "signInWithCredential:failure", task.exception)

                    Toast.makeText(baseContext, "Authentication failed.",

                        Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun updateUI(user: FirebaseUser?) {



        if (user != null) {

           userName!!.text=  user.displayName

            PhoneNumber!!.text =  user.phoneNumber
            email!!.text=user.email
           id= user.uid.toLongOrNull()
           btn_TwitterLogin!!.visibility=View.GONE
            GetTweets()





        }

    }
    private  fun GetTweets()
    {
       // val apiClient = TwitterApiClient(TwitterCore.getInstance().sessionManager.activeSession)
     // var listItems=  apiClient.kTwitterApi.userTimeline(id!!.toLong(),100 )

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitter.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(TwitterApi::class.java)
        //app hydrb hena
      // var call= service.userTimeline(id!!,100)
    }

}


