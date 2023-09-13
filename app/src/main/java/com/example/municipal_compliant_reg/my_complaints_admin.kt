package com.example.municipal_compliant_reg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.municipal_compliant_reg.com.example.municipal_compliant_reg.complaints
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class my_complaints_admin : AppCompatActivity() {
    lateinit var dbref: DatabaseReference
    lateinit var recylerview: RecyclerView
    lateinit var arraylist:ArrayList<complaints>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_complaints_admin)

        recylerview = findViewById(R.id.recyclerview)
        recylerview.layoutManager = LinearLayoutManager(this)
        recylerview.setHasFixedSize(true)
        arraylist= arrayListOf()

        getUserData()
    }

    private fun getUserData() {
        dbref= FirebaseDatabase.getInstance().getReference("complaints/")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for (compsnap in snapshot.children)
                    {
                        for (child in compsnap.children) {
                            val complt = child.getValue(complaints::class.java)
                            arraylist.add(complt!!)
                        }
                    }
                    val adapter=my_complaints_admin_adapter(arraylist)
                    recylerview.adapter=adapter
                    adapter.setOnItemClickListener(object :my_complaints_admin_adapter.OnItemClickListener{
                        override fun OnItemClick(position: Int) {
                            val intent=Intent(this@my_complaints_admin,cmpl_view::class.java)
                            intent.putExtra("uid",arraylist[position].uid)
                            intent.putExtra("subject",arraylist[position].subject)
                            startActivity(intent)
                            Toast.makeText(this@my_complaints_admin,"$position",Toast.LENGTH_SHORT).show()

                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}