package com.sourav1.chatapp.Data

data class Message(
    val msgId: String,
    val msg: String,
    val senderId: String,
    val timeStamp:Long
)
