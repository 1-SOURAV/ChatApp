package com.sourav1.chatapp.FragmentScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sourav1.chatapp.Adapter.ChatRvAdapter
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.R
import com.sourav1.chatapp.Screens.DetailedChat
import com.sourav1.chatapp.databinding.FragmentChatsScreenBinding

class ChatsScreen : Fragment(), ChatRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentChatsScreenBinding
    private val usersList: ArrayList<Users> = fillUserList()
    private val adapter = ChatRvAdapter(usersList, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsScreenBinding.inflate(inflater, container, false)

        val rv = binding.chatRv
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter

        return binding.root
    }


    private fun fillUserList(): ArrayList<Users> {
        val list: ArrayList<Users> = ArrayList()

        list.add(Users("Sourav Sharma", "This is last message", R.drawable.welcome_img))
        list.add(Users("Manish Sharma", "This is last message", R.drawable.hand_shake))
        list.add(Users("Shubham Sharma", "This is last message", R.drawable.friends))
        list.add(Users("Ram"))
        list.add(Users("Shyam"))
        list.add(Users("Manoj"))
        list.add(Users("Ram Lal"))
        list.add(Users("Mohit"))

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