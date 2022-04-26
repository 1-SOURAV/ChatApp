package com.sourav1.chatapp.Screens

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.R
import com.sourav1.chatapp.databinding.ActivityDetailedChatBinding
import java.io.File

class DetailedChat : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME").toString()
        val uid = intent.getStringExtra("UID").toString()
        setUpSupportActionBar(username, uid)
    }

    private fun setUpSupportActionBar(username: String, uid: String) {
        setSupportActionBar(binding.toolbar1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar1.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar1UserName.text = username

        val storageRef = FirebaseStorage.getInstance()
        val ref = storageRef.reference.child("UserImage").child(uid)
        val localFile = File.createTempFile(uid, "jpg")

        ref.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.toolbar1userImg.setImageBitmap(bitmap)
        }

        binding.toolbar1.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}