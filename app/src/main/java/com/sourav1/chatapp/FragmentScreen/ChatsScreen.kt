package com.sourav1.chatapp.FragmentScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sourav1.chatapp.Adapter.ChatRvAdapter
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.Screens.DetailedChat
import com.sourav1.chatapp.databinding.FragmentChatsScreenBinding

class ChatsScreen : Fragment(), ChatRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentChatsScreenBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val uid = auth.currentUser?.uid
    private val mDb: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val userList = fillUserList()
    private val adapter: ChatRvAdapter = ChatRvAdapter(userList, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsScreenBinding.inflate(inflater, container, false)

        binding.chatRv.layoutManager = LinearLayoutManager(context)
        binding.chatRv.adapter = adapter

        return binding.root
    }


    private fun fillUserList(): ArrayList<Users> {
        val ref = mDb.collection("Users").document("Data").collection("PersonalInfo")
        val userList: ArrayList<Users> = ArrayList()

        ref.addSnapshotListener { value, e ->
            if (e != null) {
                Log.d("Check", e.message.toString())
                return@addSnapshotListener
            }

            binding.progressBar1.visibility = View.VISIBLE
            for (doc in value!!) {
                val user = doc.data as MutableMap<*, *>
                Log.d("Checking: ", user["UserImg"].toString())

                val uid = user["Uid"].toString()
                val name = user["Name"].toString()

                if (uid == auth.currentUser?.uid)
                    continue
                userList.add(Users(name, uid))
            }
            binding.progressBar1.visibility = View.INVISIBLE

            binding.chatRv.layoutManager = LinearLayoutManager(context)
            binding.chatRv.adapter = adapter
        }
        return userList
    }

    override fun onItemClick(position: Int, view: View) {
        val intent = Intent(context, DetailedChat::class.java)
        val currUser = userList[position]
        intent.putExtra("USERNAME", currUser.name)
        intent.putExtra("UID", currUser.id)
        startActivity(intent)
    }
}