package com.example.contactapp.roomdb


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactapp.roomdb.dao.ContactDao
import com.example.contactapp.roomdb.entity.Contact

@Database(entities = [Contact::class], version = 3)
abstract class Database: RoomDatabase(){

    abstract fun ContactDao(): ContactDao
}