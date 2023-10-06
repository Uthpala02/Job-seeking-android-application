package com.example.madd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madd.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class UpdatePostJob : AppCompatActivity() {


    private lateinit var binding: ActivityUpdateBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.updateButton.setOnClickListener {
            val phone = binding.referencePhone.text.toString()
            val Jobname = binding.updateName.text.toString()
            val description = binding.updateOperator.text.toString()
            val Email = binding.updateLocation.text.toString()
            updateData(phone,Jobname,description,Email)
        }
    }
    private fun updateData(phone: String, Jobname: String, description: String, Email: String) {
        database = FirebaseDatabase.getInstance().getReference("User")
        val user = mapOf<String,String>(
            "name" to Jobname,
            "operator" to description,
            "location" to Email
        )
        database.child(phone).updateChildren(user).addOnSuccessListener {
            binding.referencePhone.text.clear()
            binding.updateName.text.clear()
            binding.updateOperator.text.clear()
            binding.updateLocation.text.clear()
            Toast.makeText(this,"Successfully Updated",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to Update", Toast.LENGTH_SHORT).show()
        }}
}


