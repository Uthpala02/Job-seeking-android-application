package com.example.madd

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompanyAdapter (private val compList: ArrayList<User>): RecyclerView.Adapter<CompanyAdapter.ViewHolder>(){

    private lateinit var cListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        cListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.company_list_item, parent, false)
        return ViewHolder(itemView, cListener)
    }

    override fun getItemCount(): Int {
        return compList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = compList[position]
        holder.textView47.text = currentEmp.description
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val textView47 : TextView = itemView.findViewById(R.id.textView47)
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}
