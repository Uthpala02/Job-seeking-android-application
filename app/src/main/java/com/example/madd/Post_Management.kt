package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class Post_Management :  AppCompatActivity() {


    private lateinit var btnViewProfile: Button
    private lateinit var btnManagePost: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_management)


        btnManagePost = findViewById(R.id.manage_post)
        btnViewProfile= findViewById(R.id.add_post)


        btnViewProfile.setOnClickListener {
            val intent = Intent(this, Add_Post::class.java)
            startActivity(intent)
        }
        btnManagePost.setOnClickListener {
            val intent = Intent(this, FetchingPost::class.java)


            startActivity(intent)




        }
    }
}