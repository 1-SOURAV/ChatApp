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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.sourav1.chatapp.R
import com.sourav1.chatapp.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding

    private lateinit var signUpTv: TextView
    private lateinit var loginBtn: Button
    private lateinit var loginWithGoogleBtn: Button
    private lateinit var forgotPasswordTv: TextView
    private lateinit var emailTv: EditText
    private lateinit var passwordTv: EditText

    private lateinit var email: String
    private lateinit var password: String

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dialog = ProgressDialog(this)

        signUpTv = binding.signUpTv1
        loginBtn = binding.loginBtn
        loginWithGoogleBtn = binding.loginWithGoogleBtn
        forgotPasswordTv = binding.forgotPasswordTv
        emailTv = binding.emailTv1
        passwordTv = binding.passwordTv1

        signUpTv.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            loginUser()
        }

        loginWithGoogleBtn.setOnClickListener {
            loginWithGoogle()
        }

        forgotPasswordTv.setOnClickListener {

        }

    }

    private fun loginWithGoogle() {

    }

    private fun loginUser() {
        email = emailTv.text.toString()
        password = passwordTv.text.toString()

        Log.d("Check", "Email: $email")
        Log.d("Check", "Password: $password")

        when {
            email.isEmpty() -> {
                emailTv.error = "Can't be empty!.."
            }
            password.isEmpty() -> {
                passwordTv.error = "Can't be empty!.."
            }
            else -> {
                dialog.setTitle("Logging In")
                dialog.setMessage("Please wait a while, you will be logged in shortly...")
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        dialog.dismiss()
                        if (task.isSuccessful) {
                            gotToDashBoard()
                        } else {
                            Toast.makeText(
                                this,
                                "Some Error Occurred: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun gotToDashBoard() {
        val intent = Intent(this, Dashboard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}