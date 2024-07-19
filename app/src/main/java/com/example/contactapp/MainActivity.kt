package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.roomdb.DbBuilder
import com.example.contactapp.roomdb.entity.Contact

class MainActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    var contactList = ArrayList<Contact>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,AddEditActivity::class.java))
        }
        DbBuilder.getdb(this)?.ContactDao()?.let { contactList.addAll(it.readContact()) }

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = ContactAdapter(contactList,this)
        //DbBuilder.getdb(this)?.ContactDao()?.createContact(Contact(name = "sumit", phoneNumber = "8178232170" ))
    }
}