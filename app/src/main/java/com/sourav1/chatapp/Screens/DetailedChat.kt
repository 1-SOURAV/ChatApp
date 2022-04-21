package com.sourav1.chatapp.Screens

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.FragmentScreen.ChatsScreen
import com.sourav1.chatapp.R
import com.sourav1.chatapp.databinding.ActivityDetailedChatBinding

class DetailedChat : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedChatBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersList = intent.getParcelableArrayListExtra<Users>("USERS")
        val pos = intent.getStringExtra("POSITION").toString().toInt()
        val currUser = usersList?.get(pos)

        setSupportActionBar(binding.toolbar1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar1.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        if (currUser != null) {
            binding.toolbar1UserName.text = currUser.name.toString()
            binding.toolbar1userImg.setImageResource(currUser.userImg)
        }

        binding.toolbar1.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}