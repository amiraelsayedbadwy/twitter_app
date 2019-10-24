package com.example.twitterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity()
{
    private var email: String? = null
    private var password: String? = null
    private var Email: EditText? = null
    private var Password: EditText? = null
    private var btnLogin: Button? = null
    private var btnCreateAccount: Button? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      intialise()
    }
    fun intialise()
    {
        Email = findViewById<View>(R.id.loginemail) as EditText
        Password = findViewById<View>(R.id.loginpassword) as EditText
        btnLogin = findViewById<View>(R.id.btn_login)as Button
        btnCreateAccount = findViewById<View>(R.id.btn_register) as Button
        mAuth = FirebaseAuth.getInstance()
        btnLogin!!.setOnClickListener { loginUser() }
        btnCreateAccount!!
            .setOnClickListener { startActivity(Intent(this@MainActivity,
                Registeration::class.java)) }
    }
    private fun loginUser() {

        email = Email?.text.toString()
        password = Password?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Log.d("Login", "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d("login", "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@MainActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@MainActivity, HomePage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }



}
