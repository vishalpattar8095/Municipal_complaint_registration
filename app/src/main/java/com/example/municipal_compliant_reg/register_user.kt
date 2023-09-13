package com.example.municipal_compliant_reg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.municipal_compliant_reg.com.example.municipal_compliant_reg.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class register_user : AppCompatActivity() {
    lateinit var fullname_reg:TextView
    lateinit var address_reg: TextView
    lateinit var adhaar_reg:TextView
    lateinit var mobile_reg:TextView
    lateinit var email_reg: TextView
    lateinit var password_reg: TextView
    lateinit var confirm_pass: TextView
    lateinit var register: Button
    lateinit var login_page: TextView
    private lateinit var firebase:FirebaseAuth
    private lateinit var db:FirebaseDatabase
    lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        fullname_reg=findViewById(R.id.fullname_reg)
        address_reg=findViewById(R.id.address_reg)
        adhaar_reg=findViewById(R.id.adhaar_no_reg)
        mobile_reg=findViewById(R.id.mobile_no_reg)
        email_reg = findViewById(R.id.email_reg)
        password_reg = findViewById(R.id.password_reg)
        confirm_pass = findViewById(R.id.confirm_password_reg)
        register = findViewById(R.id.register_btn)
        login_page = findViewById(R.id.register_page)
        firebase = FirebaseAuth.getInstance()
        db=FirebaseDatabase.getInstance()
        dbRef=db.reference.child("Users")
        login_page.setOnClickListener {
            val intentloginpage = Intent(this, MainActivity::class.java)
            startActivity(intentloginpage)
        }
        register.setOnClickListener {
            if (password_reg.text.toString() == confirm_pass.text.toString()) {
                firebase.createUserWithEmailAndPassword(
                    email_reg.text.toString(),
                    password_reg.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intentloginpage1 = Intent(this, MainActivity::class.java)
                        startActivity(intentloginpage1)
                        val user=user(fullname_reg.text.toString(),address_reg.text.toString(),adhaar_reg.text.toString(),mobile_reg.text.toString())
                        val cur_user = firebase.currentUser
                        val userdb=dbRef.child((cur_user?.uid.toString()))
                        userdb.setValue(user).addOnSuccessListener {
                            Toast.makeText(this, "all details added", Toast.LENGTH_SHORT).show()
                        }
                        Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, "Failed To Add User", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            else
            {
                Toast.makeText(this, "Password Not Matching", Toast.LENGTH_SHORT).show()
            }
        }
    }
}