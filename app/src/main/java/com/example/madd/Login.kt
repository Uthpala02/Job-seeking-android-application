package com.example.madd

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.madd.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private val users = arrayOf("User", "Company", "Admin")
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userType: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, users)
        spinner2.adapter = arrayAdapter

        userType = spinner2

        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView25.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val email = binding.editTextTextPersonName4.text.toString()
            val pass = binding.editTextTextPersonName5.text.toString()
            val uType = binding.spinner2.selectedItem.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = when (uType) {

                            "User" -> Intent(this,RecyclerViewActivity::class.java)
                           
                            "Company" -> Intent(this, Dashboard_company::class.java)
                            else -> Intent(this, AdminHome::class.java)
                        }
                        startActivity(intent)
                    }
                }


            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }


        }
    }
