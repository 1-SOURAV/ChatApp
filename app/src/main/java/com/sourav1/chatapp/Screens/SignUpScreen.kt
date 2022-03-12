package com.sourav1.chatapp.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.sourav1.chatapp.databinding.ActivitySignUpScreenBinding

class SignUpScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpScreenBinding
    private lateinit var loginBtn: TextView
    private lateinit var signUpBtn: Button

    private lateinit var fullName: String
    private lateinit var mobile: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        loginBtn = binding.loginTv1
        signUpBtn = binding.signUpBtn

        fullName = binding.fullNameTv.text.toString()
        mobile = binding.mobileTv.text.toString()


        loginBtn.setOnClickListener {
            val intent = Intent(this,LoginScreen::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            createAccount(fullName, mobile, password)
        }
    }

    private fun createAccount(fullName: String, mobile: String, password: String) {

    }
}