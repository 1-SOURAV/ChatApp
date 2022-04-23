package com.sourav1.chatapp.Data

import android.os.Parcel
import android.os.Parcelable
import com.sourav1.chatapp.R

data class Users(
    val name: String,
    var lastMsg: String = "Say Hii..",
    val userImg: Int = R.drawable.person,
    var id: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(lastMsg)
        parcel.writeInt(userImg)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}