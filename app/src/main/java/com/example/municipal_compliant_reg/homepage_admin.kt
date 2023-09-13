package com.example.municipal_compliant_reg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class homepage_admin : AppCompatActivity() {
    lateinit var complaints:TextView
    lateinit var my_profile_admin:TextView
    lateinit var logout:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage_admin)
        complaints=findViewById(R.id.complaints_list)
        my_profile_admin=findViewById(R.id.profile_admin)
        logout=findViewById(R.id.logout_admin)
        complaints.setOnClickListener{
            val intent= Intent(this,my_complaints_admin::class.java)
            startActivity(intent)
        }
        my_profile_admin.setOnClickListener{
            val intent=Intent(this,my_profile::class.java)
            startActivity(intent)
        }
        logout.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}