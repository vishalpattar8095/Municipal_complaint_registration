package com.example.municipal_compliant_reg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class homepage : AppCompatActivity() {
    lateinit var mycomplaints: TextView
    lateinit var registercomplaint: TextView
    lateinit var myprofile: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        mycomplaints=findViewById(R.id.m_complaints)
        registercomplaint=findViewById(R.id.register_complaint)
        myprofile=findViewById(R.id.my_profile)
        val logout=findViewById<TextView>(R.id.logout)
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intentlogin= Intent(this,MainActivity::class.java)
            startActivity(intentlogin)
        }
        mycomplaints.setOnClickListener{
            val intentmycmp=Intent(this,my_complaints::class.java)
            startActivity(intentmycmp)
        }
        registercomplaint.setOnClickListener{
            val intentcmpform=Intent(this,complaint_form::class.java)
            startActivity(intentcmpform)
        }
        myprofile.setOnClickListener{
            val intentprofile=Intent(this,my_profile::class.java)
            startActivity(intentprofile)
        }
    }
}