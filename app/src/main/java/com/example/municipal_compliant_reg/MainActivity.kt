package com.example.municipal_compliant_reg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var username:TextView
    lateinit var password:TextView
    lateinit var loginbtn:Button
    lateinit var register:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        register=findViewById(R.id.register_page)
        loginbtn=findViewById<Button>(R.id.login1)
        val firebaseAuth = FirebaseAuth.getInstance()
        loginbtn.setOnClickListener{

            if(username.text.toString().isEmpty())
            {
                username.setError("enter email")
            }
            if(password.text.toString().isEmpty())
            {
                password.setError("enter password")
            }
            else
            {
                firebaseAuth.signInWithEmailAndPassword(username.text.toString(),password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        val dbRef=FirebaseDatabase.getInstance().reference
                        val cur_user = FirebaseAuth.getInstance().currentUser
                        dbRef.child("Users").child((cur_user?.uid.toString())).get().addOnSuccessListener {
                            val isOfficer= it.child("officer").value
                            if(isOfficer.toString()=="true")
                            {
                                val intent=Intent(this,homepage_admin::class.java)
                                startActivity(intent)
                                Toast.makeText(this,"Welcome Officer",Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                val intent=Intent(this,homepage::class.java)
                                startActivity(intent)
                                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(this,"Wrong Credentials",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        register.setOnClickListener{
            val intent=Intent(this,register_user::class.java)
            startActivity(intent)
        }
    }
}