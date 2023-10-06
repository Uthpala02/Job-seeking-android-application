package com.example.madd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Post_Adapter(private val userList:ArrayList<PostModel>):RecyclerView.Adapter<Post_Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val PostName: TextView = itemView.findViewById(R.id.postName)
        val UserName: TextView = itemView.findViewById(R.id.userName)
        val UserEmail: TextView = itemView.findViewById(R.id.email)
        val PostDescription: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {


        return userList.size
    }

    override fun onBindViewHolder(holder: Post_Adapter.MyViewHolder, position: Int) {
        holder.PostName.text = userList[position].postUserName
        holder.UserName.text = userList[position].postName
        holder.UserEmail.text = userList[position].postEmail
        holder.PostDescription.text = userList[position].postDescription
    }
}