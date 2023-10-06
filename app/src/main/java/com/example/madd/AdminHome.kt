package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AdminHome : AppCompatActivity() {

    private lateinit var btnFetchData: Button
    private lateinit var btnCompanies: Button
    private lateinit var btnPerformance: Button
    private lateinit var imageButton: ImageButton
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        btnFetchData = findViewById(R.id.button3)
        btnCompanies = findViewById(R.id.button4)
        btnPerformance = findViewById(R.id.button6)

        mAuth = FirebaseAuth.getInstance()
        imageButton = findViewById(R.id.imageButton)

        btnFetchData.setOnClickListener{
            val intent = Intent(this, AdminUsers::class.java)
            startActivity(intent)
        }

        btnCompanies.setOnClickListener{
            val intent = Intent(this, AdminCompany::class.java)
            startActivity(intent)
        }

        btnPerformance.setOnClickListener{
            val intent = Intent(this, AdminPerformance::class.java)
            startActivity(intent)
        }


        imageButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this@AdminHome, Login::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this@AdminHome, "Logout Successful !", Toast.LENGTH_SHORT).show()
        }
    }
}