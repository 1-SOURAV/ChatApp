package com.sourav1.chatapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.R

class ChatRvAdapter(
    private val userList: ArrayList<Users>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ChatRvAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val userImgView: ImageView = itemView.findViewById<ImageView>(R.id.userImg)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val lastMsg: TextView = itemView.findViewById(R.id.lastMsg)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if(view != null){
                listener.onItemClick(adapterPosition, view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currUser = userList[position]

        holder.userImgView.setImageResource(currUser.userImg)
        holder.userName.text = currUser.name
        holder.lastMsg.text = currUser.lastMsg

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }
}