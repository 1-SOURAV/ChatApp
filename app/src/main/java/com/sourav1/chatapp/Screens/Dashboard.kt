package com.sourav1.chatapp.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sourav1.chatapp.Adapter.ViewPagerAdapter
import com.sourav1.chatapp.FragmentScreen.ChatsScreen
import com.sourav1.chatapp.FragmentScreen.ProfileScreen
import com.sourav1.chatapp.FragmentScreen.UsersScreen
import com.sourav1.chatapp.R
import com.sourav1.chatapp.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDb: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val uid = auth.currentUser?.uid
    private val mp: MutableMap<String, String> = mutableMapOf()

    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var bar: Toolbar

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUser()

        pager = binding.viewPager
        tab = binding.tabs
        bar = binding.toolbar

        setSupportActionBar(bar)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ChatsScreen(), "Chats")
        adapter.addFragment(UsersScreen(), "Users")
        adapter.addFragment(ProfileScreen(), "Profile")

        pager.adapter = adapter
        tab.setupWithViewPager(pager)
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

    private fun loadUser() {
        val ref = mDb.collection("Users").document("Data").collection("PersonalInfo")
            .document(uid!!)

        ref.get().addOnSuccessListener { document ->
            if (document != null) {
                val userMap = document.data as MutableMap<*, *>
                val username = userMap["Name"]
                bar.title = username.toString()
            }
        }
            .addOnFailureListener {
                Log.d("Check: ", it.message.toString())
            }
    }
}