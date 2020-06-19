package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        auth = FirebaseAuth.getInstance()

        FinishBtn.setOnClickListener {
            signUpUser()
        }

    }


    private fun signUpUser()
    {
        if(UsernameTextBx.text.toString().isEmpty())
        {
            UsernameTextBx.error = "Please enter a username"
            UsernameTextBx.requestFocus()
            return
        }

        if(EmailTextBx.text.toString().isEmpty())
        {
            EmailTextBx.error = "Please enter an Email"
            EmailTextBx.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(EmailTextBx.text.toString()).matches()){
            EmailTextBx.error = "Please enter a valid Email"
            EmailTextBx.requestFocus()
            return
        }

        if(PasswordTextBx.text.toString().isEmpty())
        {
            PasswordTextBx.error = "Please enter a Password"
            PasswordTextBx.requestFocus()
            return
        }

        if(ConPassTextBx.text.toString().isEmpty())
        {
            ConPassTextBx.error = "Please reenter the Password"
            ConPassTextBx.requestFocus()
            return
        }

        if(!PasswordTextBx.text.toString().equals(ConPassTextBx.text.toString()))
        {
            ConPassTextBx.error = "The Passwords don't match please reenter the Password"
            ConPassTextBx.requestFocus()
            return
        }


        auth.createUserWithEmailAndPassword(EmailTextBx.text.toString(), PasswordTextBx.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sign Up failed. Please try again after some time.", Toast.LENGTH_SHORT).show()

                }

                // ...
            }

    }




}
