package com.example.contactapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.ContactItemBinding
import com.example.contactapp.roomdb.entity.Contact

class ContactAdapter(private var contactList: List<Contact>, private var context: Context): RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: ContactItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.MyViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
   }

    override fun onBindViewHolder(holder: ContactAdapter.MyViewHolder, position: Int) {
        val contact = contactList[position]

        if(contact.profile==null){
            val splitName = contact.name?.split(" ")
            var sign = ""
            splitName?.forEach {
                if (it.isNotEmpty()) {
                    sign += it[0]
                }
            }
            holder.binding.profile.visibility = View.GONE
            holder.binding.sign.visibility = View.VISIBLE
            holder.binding.sign.text = sign
        }
        else{
            var imageByte = contact.profile
            if(imageByte!=null){
                var image = BitmapFactory.decodeByteArray(imageByte,0,imageByte.size)
                holder.binding.profile.setImageBitmap(image)
                holder.binding.profile.visibility = View.VISIBLE
                holder.binding.sign.visibility = View.GONE

            }
            else{
                val splitName = contact.name?.split(" ")
                var sign = ""
                splitName?.forEach {
                    if (it.isNotEmpty()) {
                        sign += it[0]
                    }
                }
                holder.binding.profile.visibility = View.GONE
                holder.binding.sign.visibility = View.VISIBLE
                holder.binding.sign.text = sign
            }

        }


        holder.binding.name.text = contact.name
        holder.binding.phone.text = contact.phoneNumber
        holder.binding.email.text = contact.email

    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}