package com.example.municipal_compliant_reg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.municipal_compliant_reg.com.example.municipal_compliant_reg.complaints
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class my_complaints : AppCompatActivity() {

    lateinit var dbref:DatabaseReference
    lateinit var recylerview:RecyclerView
    lateinit var arraylist:ArrayList<complaints>
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_complaints)

        recylerview = findViewById(R.id.recyclerview)
        recylerview.layoutManager = LinearLayoutManager(this)
        recylerview.setHasFixedSize(true)
        arraylist= arrayListOf()

        val data = ArrayList<complaints>()
        recylerview.adapter

        getUserData()
    }

    private fun getUserData() {
        auth=FirebaseAuth.getInstance()
        val cur_user=auth.currentUser?.uid
        dbref=FirebaseDatabase.getInstance().getReference("complaints/${cur_user.toString()}")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists())
               {
                   for (compsnap in snapshot.children)
                   {
                       val complt=compsnap.getValue(complaints::class.java)
                       arraylist.add(complt!!)
                   }
                   recylerview.adapter=my_complaints_adapter(arraylist)
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}