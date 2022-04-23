package com.sourav1.chatapp.FragmentScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sourav1.chatapp.Adapter.ChatRvAdapter
import com.sourav1.chatapp.databinding.FragmentChatsScreenBinding

class ChatsScreen : Fragment(), ChatRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentChatsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onItemClick(position: Int, view: View) {
        
    }
}