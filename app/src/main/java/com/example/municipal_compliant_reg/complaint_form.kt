package com.example.municipal_compliant_reg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.municipal_compliant_reg.com.example.municipal_compliant_reg.complaints
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class complaint_form : AppCompatActivity() {
    lateinit var submit:Button
    lateinit var upload:Button
    lateinit var address: TextView
    lateinit var subject:TextView
    lateinit var comp_img:ImageView
    lateinit var desc:TextView
    lateinit var img:Uri
    lateinit var firebase:FirebaseAuth
    lateinit var calender:Calendar
    lateinit var date:String
    lateinit var simpdateformat:SimpleDateFormat
    lateinit var storageRef:StorageReference
    private lateinit var db: FirebaseDatabase
    lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_form)
        submit=findViewById( R.id.submit)
        upload=findViewById( R.id.uploadimage)
        address=findViewById(R.id.address)
        subject=findViewById(R.id.subjectCmpl)
        desc=findViewById(R.id.desc)
        comp_img=findViewById(R.id.comp_img)
        calender=Calendar.getInstance()
        simpdateformat=SimpleDateFormat("dd-MM-yyyy")
        date=simpdateformat.format(calender.time)
        firebase=FirebaseAuth.getInstance()
        db=FirebaseDatabase.getInstance()
        dbRef=db.reference
        val cur_user = firebase.currentUser
        val uid=cur_user?.uid
        val pickimg=registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                comp_img.setImageURI(it)
                img=it
            })
        submit.setOnClickListener{
            val userRef=dbRef.child("complaints").child((cur_user?.uid.toString())).child("${subject.text}")
            val cmpls=complaints(uid.toString(),address.text.toString(),subject.text.toString(),date,desc.text.toString())
            userRef.setValue(cmpls).addOnSuccessListener {
                Toast.makeText(this, "Complaints Registered", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }
            if (img != null) {
                storageRef=FirebaseStorage.getInstance().reference.child("Complaints/${cur_user?.uid.toString()}/${subject.text.toString()}_img")
                storageRef.putFile(img).addOnSuccessListener {
                    Toast.makeText(this, "complaint image uploaded", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                }
            }
            val intent=Intent(this,homepage::class.java)
            startActivity(intent)
        }
    upload.setOnClickListener{
        pickimg.launch("image/*")
    }

    }
}
