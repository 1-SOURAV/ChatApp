package com.sourav1.chatapp.Screens

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sourav1.chatapp.Adapter.DetailedChatRvAdapter
import com.sourav1.chatapp.Data.Message
import com.sourav1.chatapp.R
import com.sourav1.chatapp.databinding.ActivityDetailedChatBinding
import java.io.File
import java.util.*

class DetailedChat : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedChatBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var msgList: ArrayList<Message>
    private lateinit var adapter: DetailedChatRvAdapter

    private lateinit var senderUid: String
    private lateinit var receiverUid: String

    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receiverUsername = intent.getStringExtra("USERNAME").toString()
        receiverUid = intent.getStringExtra("UID").toString()
        setUpSupportActionBar(receiverUsername, receiverUid)

        senderUid = auth.currentUser?.uid.toString()

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        msgList = ArrayList()
        fillMsgList(msgList)

        adapter = DetailedChatRvAdapter(this, msgList)

        val rv = binding.detailedChatRv
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        binding.sendBtn.setOnClickListener {
            sendMsg(msgList)
        }
    }

    private fun sendMsg(msgList: java.util.ArrayList<Message>) {
        val msgTxt = binding.msgEt.text.toString()
        binding.msgEt.text.clear()

        val date = Date()
        val msg = Message("", msgTxt, senderUid, date.time)

        mDb.collection("chats")
            .document(senderRoom)
            .collection("messages")
            .add(msg)
            .addOnSuccessListener {
                mDb.collection("chats")
                    .document(receiverRoom)
                    .collection("messages")
                    .add(msg)
            }
        adapter.notifyItemInserted(msgList.size)
        binding.detailedChatRv.scrollToPosition(msgList.size - 1)
    }

    private fun fillMsgList(msgList: ArrayList<Message>) {
        val ref =
            mDb.collection("chats").document(senderRoom).collection("messages").orderBy("timeStamp")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.d("Check::==>", "Listen Failed, $e")
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        binding.progressBar.visibility = View.VISIBLE
                        msgList.clear()
                        for (doc in snapshot.documents) {
                            val msg = Message(
                                doc["msgId"].toString(),
                                doc["msg"].toString(),
                                doc["senderId"].toString(),
                                doc["timeStamp"].toString().toLong()
                            )

                            msgList.add(msg)
                        }
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    binding.detailedChatRv.layoutManager = LinearLayoutManager(this)
                    binding.detailedChatRv.adapter = DetailedChatRvAdapter(this, msgList)
                    binding.detailedChatRv.scrollToPosition(msgList.size - 1)
                }
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