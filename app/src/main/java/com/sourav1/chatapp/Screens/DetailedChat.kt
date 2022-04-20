package com.sourav1.chatapp.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.databinding.ActivityDetailedChatBinding

class DetailedChat : AppCompatActivity() {

    private lateinit var binding : ActivityDetailedChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersList = intent.getParcelableArrayListExtra<Users>("USERS")
        val pos = intent.getStringExtra("POSITION").toString().toInt()
        val currUser = usersList?.get(pos)

        val actionBar = binding.toolbar1
        actionBar.setTitle(currUser?.name)
    }
}