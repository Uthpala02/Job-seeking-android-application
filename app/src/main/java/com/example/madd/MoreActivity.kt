package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button

class MoreActivity : AppCompatActivity() {

    private lateinit var btnViewProfile: Button
    private lateinit var btnManagePost: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        btnViewProfile = findViewById(R.id.ViewUser)
        btnManagePost = findViewById(R.id.ManagePost)
        btnLogout = findViewById(R.id.Logout)

        btnViewProfile.setOnClickListener {
            val intent = Intent(this,EmployeeDetailsActivity::class.java)
            startActivity(intent)
        }

        btnManagePost.setOnClickListener {
            val intent = Intent(this, Post_Management::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {
            val intent = Intent(this, Login::class.java)


            startActivity(intent)

        }


    }

}