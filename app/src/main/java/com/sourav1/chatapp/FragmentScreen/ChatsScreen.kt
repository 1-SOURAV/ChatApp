package com.sourav1.chatapp.FragmentScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.sourav1.chatapp.Adapter.ChatRvAdapter
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.R
import com.sourav1.chatapp.Screens.DetailedChat
import com.sourav1.chatapp.databinding.FragmentChatsScreenBinding
import java.util.concurrent.RecursiveAction

class ChatsScreen : Fragment(), ChatRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentChatsScreenBinding

    private val mDb: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("ChatApp/Users")

    private val usersList: ArrayList<Users> = fillUserList()
    private lateinit var adapter: RecyclerView.Adapter<ChatRvAdapter.ViewHolder>

    private lateinit var rv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsScreenBinding.inflate(inflater, container, false)
        rv = binding.chatRv
        adapter = ChatRvAdapter(usersList, this)

        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter

        return binding.root
    }


    private fun fillUserList(): ArrayList<Users> {
        val list: ArrayList<Users> = ArrayList()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    if (dataSnapshot.exists()) {
                        val mp = dataSnapshot.getValue<Map<String, String>>()
                        if (mp != null) {
                            list.add(Users(mp["Name"].toString(), "Last Msg", R.drawable.welcome_img))
                        }
                    }
                }
                adapter = ChatRvAdapter(usersList, this@ChatsScreen)

                rv.layoutManager = LinearLayoutManager(activity)
                rv.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Fetching Error", "{error.message}")
            }

        }
        mDb.addValueEventListener(postListener)
        return list
    }

    override fun onItemClick(position: Int, view: View) {
        val intent = Intent(context, DetailedChat::class.java).apply {
            putExtra("POSITION", position.toString())
            putParcelableArrayListExtra("USERS", usersList)
        }
        startActivity(intent)
    }
}