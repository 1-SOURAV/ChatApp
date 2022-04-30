package com.sourav1.chatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.sourav1.chatapp.Data.Message
import com.sourav1.chatapp.R

const val ITEM_SENT = 1
const val ITEM_RECEIVED = 2

class DetailedChatRvAdapter(val context: Context, val msgList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class senderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msgSend = itemView.findViewById<TextView>(R.id.textOutTv)
    }

    inner class receiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msgReceived = itemView.findViewById<TextView>(R.id.textInTv)
    }

    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        if (FirebaseAuth.getInstance().currentUser!!.uid == msg.senderId) {
            return ITEM_SENT
        }
        return ITEM_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_SENT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_text_out, parent, false)
            return senderViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_text_in, parent, false)
            return receiverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == senderViewHolder::class.java){
            val viewHolder = holder as senderViewHolder
            viewHolder.msgSend.text = msgList[position].msg
        }
        else{
            val viewHolder = holder as receiverViewHolder
            viewHolder.msgReceived.text = msgList[position].msg
        }
    }

    override fun getItemCount(): Int {
        return msgList.size
    }
}