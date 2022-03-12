package com.sourav1.chatapp.Screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sourav1.chatapp.databinding.ActivityLoginScreenBinding

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var signUpTv: TextView
    private lateinit var loginBtn: Button
    private lateinit var loginWithGoogleBtn: Button
    private lateinit var forgotPasswordTv: TextView

    private lateinit var mobile: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        signUpTv = binding.signUpTv1
        loginBtn = binding.loginBtn
        loginWithGoogleBtn = binding.loginWithGoogleBtn
        forgotPasswordTv = binding.forgotPasswordTv

        mobile = binding.mobileTv1.text.toString()
        password = binding.passwordTv1.text.toString()

        signUpTv.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            loginUser(mobile, password)
        }

        loginWithGoogleBtn.setOnClickListener {
            loginWithGoogle()
        }

        forgotPasswordTv.setOnClickListener {

        }

    }

    private fun loginWithGoogle() {

    }

    private fun loginUser(mobile: String, password: String) {

    }
}