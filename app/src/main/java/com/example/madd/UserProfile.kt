package com.example.madd;

import android.app.Dialog;
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.madd.databinding.ActivityUserProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference:DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener{

            showProgressBar()


            val empName = binding.etEmpName.text.toString()
            val empJobTitle =  binding.etEmpJobTitle.text.toString()
            val empEducation =  binding.etEmpEducation.text.toString()
            val empAddress =  binding.etEmpAddress.text.toString()
            val empPhone  =  binding.etEmpPhone.text.toString()
            val empEmail =  binding.etEmpEmail123.text.toString()
            val empAbout =  binding.etEmpAbout.text.toString()



            val user = EmployeModel(empName,empJobTitle,empEducation,empAddress,empPhone,empEmail,empAbout)
            if(uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener{

                    if(it.isSuccessful){


                        uploadProfile()
                    }


                    else{
                        hideProgressBar()
                        Toast.makeText(this@UserProfile,"Failed",Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }

    }

    private fun uploadProfile(){
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {


            hideProgressBar()
            Toast.makeText(this@UserProfile,"Success",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {

            hideProgressBar()
            Toast.makeText(this@UserProfile,"Failure",Toast.LENGTH_SHORT).show()
        }

    }
    private fun showProgressBar(){
        dialog=Dialog(this@UserProfile)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar(){
        dialog.dismiss()
    }


}