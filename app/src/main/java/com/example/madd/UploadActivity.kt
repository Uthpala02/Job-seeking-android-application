package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madd.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveButton.setOnClickListener {
            val Jobname = binding.uploadName.text.toString()
            val description = binding.uploadOperator.text.toString()
            val Email = binding.uploadLocation.text.toString()
            val phone = binding.uploadPhone.text.toString()
            database = FirebaseDatabase.getInstance().getReference("Company")
            val users = User(Jobname,description,Email,phone)
            database.child(phone).setValue(users).addOnSuccessListener {
                binding.uploadName.text.clear()
                binding.uploadOperator.text.clear()
                binding.uploadLocation.text.clear()
                binding.uploadPhone.text.clear()
                Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, CompanyMain::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}