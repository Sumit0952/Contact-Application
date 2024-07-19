package com.example.contactapp.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contactapp.roomdb.entity.Contact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)

    fun createContact(contact: Contact): Long
    @Update

    fun updateContact(contact: Contact)
    @Query("SELECT*FROM CONTACT ")
    fun readContact(): List<Contact>
    @Query("SELECT * FROM CONTACT WHERE id =:id1")
    fun readContact(id1 : Int):Contact
    @Delete
    fun deleteContact(contact: Contact)
}