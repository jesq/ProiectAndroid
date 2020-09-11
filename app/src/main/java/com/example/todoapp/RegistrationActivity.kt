package com.example.todoapp

import DBHelper
import UserListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_registration.*



class RegistrationActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var userList : List<User> = ArrayList<User>()
    private var db = DBHelper(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

       // db = DBHelper(this)



        val actionBar = supportActionBar
        actionBar!!.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()
        //refreshData()

        FinishBtn.setOnClickListener {
            signUpUser()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        return true
    }

    private fun signUpUser()
    {
        if(UsernameTextBx.text.toString().isEmpty())
        {
            UsernameTextBx.error = "Please enter a username"
            UsernameTextBx.requestFocus()
            return
        }

        if(!checkUsername(UsernameTextBx.text.toString()))
        {
            UsernameTextBx.error = "Username already taken!"
            UsernameTextBx.requestFocus()
            return
        }

        if(EmailTextBx.text.toString().isEmpty())
        {
            EmailTextBx.error = "Please enter an Email"
            EmailTextBx.requestFocus()
            return
        }

        if(!checkEmail(EmailTextBx.text.toString()))
        {
            EmailTextBx.error = "Email already in use!"
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


        val user = User()


        //db = DBHelper(this)

        if(db.allUser.isEmpty())
        {
            user.userID = "1";
        }
        else
        {
            val size = db.allUser.size
            val auxUserId = db.allUser[size - 1].userID.toInt()
            user.userID = (auxUserId + 1).toString()
        }

        user.UserName = UsernameTextBx.text.toString()
        user.Email = EmailTextBx.text.toString()
        user.Password = PasswordTextBx.text.toString()

        db.addUser(user)

        auth.createUserWithEmailAndPassword(EmailTextBx.text.toString(), PasswordTextBx.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sign Up failed. Please try again after some time.", Toast.LENGTH_SHORT).show()

                }
            }

    }
    
    private fun checkUsername(username : String) : Boolean
    {
        if(db.allUser.isEmpty()) {
            return true
        }
            for (userName in db.allUser) {
                if (userName.UserName == UsernameTextBx.text.toString()) {
                    return false
                }
            }
        return true
    }

    private fun checkEmail(email : String) : Boolean
    {
        if(db.allUser.isEmpty()) {
            return true
        }
        for (email in db.allUser) {
            if (email.Email == EmailTextBx.text.toString()) {
                return false
            }
        }
        return true
    }

    private fun refreshData()
    {
        val user = User()
        var auxUserId : Long
        auxUserId = 1
        if(db.allUser.isEmpty())
        {
            user.userID = "1";

        }
        else
        {
            auxUserId = db.allUser[db.allUser.size].userID.toLong()
            user.userID = (auxUserId + 1).toString()
        }

        userList = db.allUser
        val adapter = UserListAdapter(this@RegistrationActivity, userList, auxUserId, UsernameTextBx, EmailTextBx, PasswordTextBx)
        //list_users.adapter

    }

}
