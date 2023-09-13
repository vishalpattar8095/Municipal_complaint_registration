package com.example.municipal_compliant_reg

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class my_profile : AppCompatActivity() {
    lateinit var profile_pic:ImageView
    lateinit var my_name:TextView
    lateinit var address:TextView
    lateinit var mobile_no:TextView
    lateinit var adhaar_no:TextView
    lateinit var email:TextView
    lateinit var upload_image:TextView
    lateinit var firebase:FirebaseAuth
    lateinit var storageRef:StorageReference
    private lateinit var db: FirebaseDatabase
    lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        profile_pic=findViewById(R.id.profile_pic)
        my_name=findViewById(R.id.my_name)
        address=findViewById(R.id.address)
        mobile_no=findViewById(R.id.mobile_no)
        adhaar_no=findViewById(R.id.adhaar_no)
        email=findViewById(R.id.email)
        upload_image=findViewById(R.id.upload_profile)
        firebase=FirebaseAuth.getInstance()
        db=FirebaseDatabase.getInstance()
        dbRef=db.reference
        val cur_user = firebase.currentUser
        storageRef=FirebaseStorage.getInstance().reference.child("userProfile/${cur_user?.uid.toString()}.jpg")
        dbRef.child("Users").child((cur_user?.uid.toString())).get().addOnSuccessListener {
            val fullname_usr1=it.child("fullname_usr").value
            val address_usr1=it.child("address_usr").value
            val adhaar_usr1=it.child("adhaar_usr").value
            val mobile_usr1=it.child("mobile_usr").value
            val imgfile=File.createTempFile("temp","jpg")
            storageRef.getFile(imgfile).addOnSuccessListener {
                val bitmap= BitmapFactory.decodeFile(imgfile.absolutePath)
                profile_pic.setImageBitmap(bitmap)
            }.addOnFailureListener{
                profile_pic.setImageURI(null)
            }

            my_name.text=fullname_usr1.toString()
            address.text="Address : ${address_usr1.toString()}"
            adhaar_no.text="Adhaar No : ${adhaar_usr1.toString()}"
            mobile_no.text="Mobile No : ${mobile_usr1.toString()}"
            email.text="Email : ${cur_user?.email.toString()}"
        }
        val pickimg=registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                profile_pic.setImageURI(it)
                val imgfile=File.createTempFile("temp","jpg")
                val imgred= storageRef.getFile(imgfile)
                if(imgred!=null)
                {
                    storageRef.delete().addOnSuccessListener {
                        Toast.makeText(this, "old deleted", Toast.LENGTH_SHORT).show()
                    }
                    if (it != null) {
                        storageRef.putFile(it).addOnSuccessListener {
                            Toast.makeText(this, "profile pic updated", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    if (it != null) {
                        storageRef.putFile(it).addOnSuccessListener {
                            Toast.makeText(this,"profile pic updated",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        upload_image.setOnClickListener{
            pickimg.launch("image/*")
        }
    }
}