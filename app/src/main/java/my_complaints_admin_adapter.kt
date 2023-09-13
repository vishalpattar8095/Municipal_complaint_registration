package com.example.municipal_compliant_reg

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.municipal_compliant_reg.com.example.municipal_compliant_reg.complaints


class my_complaints_admin_adapter (private val mList: List<complaints>) : RecyclerView.Adapter<my_complaints_admin_adapter.ViewHolder>(){

    private lateinit var mlistener:OnItemClickListener
    lateinit var subject_ref:String

    interface OnItemClickListener{
        fun OnItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener)
    {
        mlistener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.complaintss, parent, false)

            return ViewHolder(view,mlistener)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val ItemsViewModel = mList[position]
            holder.srno.text=(position+1).toString()
            holder.subject.text = ItemsViewModel.subject
            subject_ref=ItemsViewModel.subject.toString()
            holder.date.text = ItemsViewModel.date
            holder.status.text = ItemsViewModel.status

        }
        override fun getItemCount(): Int {
            return mList.size
        }
        class ViewHolder(ItemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(ItemView) {
            val srno: TextView = itemView.findViewById(R.id.srno)
            val subject: TextView = itemView.findViewById(R.id.subject)
            val date: TextView = itemView.findViewById(R.id.date)
            val status: TextView = itemView.findViewById(R.id.status)
            init {
                itemView.setOnClickListener{
                    listener.OnItemClick(absoluteAdapterPosition)
                }
            }
        }
    }