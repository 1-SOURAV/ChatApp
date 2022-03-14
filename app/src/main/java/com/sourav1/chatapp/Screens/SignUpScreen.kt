package com.sourav1.chatapp.Screens

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sourav1.chatapp.databinding.ActivitySignUpScreenBinding


const val TAG = "Check"
class SignUpScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpScreenBinding
    private lateinit var loginBtn: TextView
    private lateinit var signUpBtn: Button

    private lateinit var fullNameTv: EditText
    private lateinit var emailTv: EditText
    private lateinit var passwordTv: EditText

    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        auth = FirebaseAuth.getInstance()

        loginBtn = binding.loginTv1
        signUpBtn = binding.signUpBtn

        fullNameTv = binding.fullNameTv
        passwordTv = binding.fullNameTv
        emailTv = binding.emailTv

        progressDialog = ProgressDialog(this)

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            fullName = fullNameTv.text.toString()
            email = emailTv.text.toString()
            password = passwordTv.text.toString()


            Log.i(TAG, "Full name: $fullName")
            Log.i(TAG, "Email: $email")
            Log.i(TAG, "Password: $password")

            when(true){
                fullName.isEmpty() -> {
                    fullNameTv.error = "Can't be empty!"
                }

                email.isEmpty() -> {
                    emailTv.error = "Can't be empty!"
                }

                password.isEmpty() -> {
                    passwordTv.error = "Can't be empty!"
                }

                else -> {
                    createAccount(fullName, email, password)
                }
            }
        }
    }

    private fun createAccount(fullName: String, email: String, password: String) {
        progressDialog.setTitle("Sign In")
        progressDialog.setMessage("Registering user, please wait a while...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressDialog.dismiss()
                if (task.isSuccessful) {
                    //Sign In Successfully, Update UI
                    Log.d("Email Auth:", "User created with email: Success")
                    val user = auth.currentUser
                    goToDashboard(user)
                } else {
                    Toast.makeText(
                        this,
                        "Authentication Failed: ${task.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun goToDashboard(user: FirebaseUser?) {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}