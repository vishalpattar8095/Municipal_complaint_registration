package com.example.municipal_compliant_reg

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class cmpl_view : AppCompatActivity() {
    lateinit var storageRef: StorageReference
    private lateinit var db: FirebaseDatabase
    lateinit var dbRef: DatabaseReference
    lateinit var img_ad_view:ImageView
    lateinit var subject1_ref:String
    lateinit var address_ad_view:TextView
    lateinit var subject_ad_view:TextView
    lateinit var date_ad_view:TextView
    lateinit var desc_ad_view:TextView
    lateinit var resolving:Button
    lateinit var resolved:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cmpl_view)

        img_ad_view=findViewById(R.id.img_ad_view)
        address_ad_view=findViewById(R.id.address_ad_view)
        subject_ad_view=findViewById(R.id.subject_ad_view)
        date_ad_view=findViewById(R.id.date_ad_view)
        desc_ad_view=findViewById(R.id.desc_ad_view)
        resolving=findViewById(R.id.resolving)
        resolved=findViewById(R.id.resolved)

        val uid=intent.getStringExtra("uid")
        subject1_ref= intent.getStringExtra("subject").toString()

        db=FirebaseDatabase.getInstance()
        dbRef=db.reference

        storageRef= FirebaseStorage.getInstance().reference.child("Complaints/${uid.toString()}/${subject1_ref}_img")
        dbRef.child("complaints").child((uid.toString())).child("${subject1_ref}").get().addOnSuccessListener {
            val imgfile= File.createTempFile("temp","jpg")
            storageRef.getFile(imgfile).addOnSuccessListener {
                val bitmap= BitmapFactory.decodeFile(imgfile.absolutePath)
                img_ad_view.setImageBitmap(bitmap)
            }.addOnFailureListener{
                Toast.makeText(this, "failed to load img", Toast.LENGTH_SHORT).show()
                img_ad_view.setImageURI(null)
            }
        }

        dbRef.child("complaints").child((uid.toString())).child("${subject1_ref}").get().addOnSuccessListener{
                subject_ad_view.text="Subject : "+it.child("subject").value.toString()
                address_ad_view.text="Addess : "+it.child("fulladdress").value.toString()
                date_ad_view.text="Date : "+it.child("date").value.toString()
                desc_ad_view.text="Description : "+it.child("desc").value.toString()
        }
        resolving.setOnClickListener{
          val dbref= dbRef.child("complaints").child((uid.toString())).child("${subject1_ref}").child("status")
            dbref.setValue("Resolving").addOnSuccessListener {
                Toast.makeText(this, "status updated as Resolving", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@cmpl_view,my_complaints_admin::class.java))
            }
        }
        resolved.setOnClickListener{
            val dbref= dbRef.child("complaints").child((uid.toString())).child("${subject1_ref}").child("status")
            dbref.setValue("Resolved").addOnSuccessListener {
                Toast.makeText(this, "status updated as Resolved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@cmpl_view,my_complaints_admin::class.java))
            }
        }

    }
}