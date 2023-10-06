package com.example.madd

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madd.databinding.ActivityEmployeeDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class EmployeeDetailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEmployeeDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog : Dialog
    private lateinit var user :EmployeModel
    private lateinit var uid: String
    private lateinit var EditProfile: Button
    private lateinit var EditImage: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid  = auth.currentUser?.uid.toString()

        EditProfile = findViewById(R.id.button)


        EditProfile.setOnClickListener {
            val intent = Intent(this,UserProfile::class.java)
            startActivity(intent)
        }





        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()){


            getUserData()
        }
    }

    private fun getUserData(){
        showProgressBar()
        databaseReference.child(uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.getValue(EmployeModel::class.java)!!
                binding.textView11.setText(user.empName)
                binding.textView21.setText(user.empJobTitle)
                binding.textView24.setText(user.empEducation)
                binding.textView16.setText(user.empAddress)
                binding.textView13.setText(user.empPhone)
                binding.textView27.setText(user.empEmail)
                binding.textView22.setText(user.empAbout)
                getUserProfile()

            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(this@EmployeeDetailsActivity,"Failed",Toast.LENGTH_SHORT).show()

            }


        })
    }
    private fun showProgressBar(){
        dialog=Dialog(this@EmployeeDetailsActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }

    private fun getUserProfile(){
        storageReference =FirebaseStorage.getInstance().reference.child("Users/$uid")
        val localFile = File.createTempFile("tempImage","png")
        storageReference.getFile(localFile).addOnCompleteListener{

            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.imageView4.setImageBitmap(bitmap)
            hideProgressBar()

        }.addOnFailureListener {

            hideProgressBar()
            Toast.makeText(this@EmployeeDetailsActivity,"Failed",Toast.LENGTH_SHORT).show()



        }
    }


}




