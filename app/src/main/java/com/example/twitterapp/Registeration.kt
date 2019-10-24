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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registeration : AppCompatActivity() {
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        initialise()
    }
    private fun initialise() {

        etFirstName = findViewById<View>(R.id.firstname_txt) as EditText
        etLastName = findViewById<View>(R.id.lastname_txt) as EditText
        etEmail = findViewById<View>(R.id.signupemail_txt) as EditText
        etPassword = findViewById<View>(R.id.registerpassword_txt) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_signup) as Button


        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }
    private fun createNewAccount()
    {
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->


                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("registeration", "createUserWithEmail:success")

                        val userId = mAuth!!.currentUser!!.uid


                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("firstName").setValue(firstName)
                        currentUserDb.child("lastName").setValue(lastName)

                        updateUserInfoAndUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("error in register", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@Registeration, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUserInfoAndUI() {

        //start next activity this flag to prevent when  back go to registeration again
        val intent = Intent(this@Registeration, HomePage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
