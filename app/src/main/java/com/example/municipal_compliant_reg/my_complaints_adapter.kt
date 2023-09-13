package com.example.municipal_compliant_reg

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.municipal_compliant_reg.com.example.municipal_compliant_reg.complaints

class my_complaints_adapter(private val mList: ArrayList<complaints>) : RecyclerView.Adapter<my_complaints_adapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.complaintss, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val complaints = mList[position]
        holder.srno.text=(position+1).toString()
        holder.subject.text = complaints.subject
        holder.date.text = complaints.date
        holder.status.text = complaints.status

    }
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val srno: TextView = itemView.findViewById(R.id.srno)
        val subject: TextView = itemView.findViewById(R.id.subject)
        val date: TextView = itemView.findViewById(R.id.date)
        val status: TextView = itemView.findViewById(R.id.status)
    }
}
