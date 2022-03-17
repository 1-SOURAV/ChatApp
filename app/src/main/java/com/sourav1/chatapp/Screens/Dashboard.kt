package com.sourav1.chatapp.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBar.DISPLAY_SHOW_TITLE
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sourav1.chatapp.R
import com.sourav1.chatapp.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mDb: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("ChatApp/Users")

    private val uid = auth.currentUser?.uid
    private val mp: MutableMap<String, String> = mutableMapOf()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDb.child(uid!!).child("Name").get().addOnSuccessListener {
            val key = it.key.toString()
            val value = it.value.toString()
            Log.d("check: ", "$key : $value")
            mp[key] = value
        }.addOnCompleteListener {
            val fullName = mp["Name"].toString()
            binding.userName.text = "Hello $fullName"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logoutUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        auth.signOut()
        val intent = Intent(this, LoginScreen::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}