package com.example.contactapp.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Contact(
    @PrimaryKey var id : Int? = null,
    var profile : ByteArray? = null,
    var name : String? = null,
    var phoneNumber : String? = null,
    var email: String? = null
)