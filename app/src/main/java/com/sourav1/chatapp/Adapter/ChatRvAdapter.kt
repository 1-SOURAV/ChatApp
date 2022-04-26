package com.sourav1.chatapp.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.sourav1.chatapp.Data.Users
import com.sourav1.chatapp.R
import com.squareup.picasso.Picasso
import java.io.File

class ChatRvAdapter(
    private val userList: ArrayList<Users>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ChatRvAdapter.ViewHolder>() {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val userImgView: ImageView = itemView.findViewById<ImageView>(R.id.userImg)
        val userName: TextView = itemView.findViewById(R.id.userName)

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

        val ref = storage.reference.child("UserImage").child(currUser.id)
        val localFile = File.createTempFile(currUser.id, "jpg")

        ref.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.userImgView.setImageBitmap(bitmap)
        }
        holder.userName.text = currUser.name
    }

    override fun getItemCount(): Int {
        Log.d("Checking: ", "${userList.size}")
        return userList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }
}