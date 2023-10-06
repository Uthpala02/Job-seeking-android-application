package com.example.madd



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_Post : AppCompatActivity() {

    private lateinit var etPostUserName: EditText
    private lateinit var etPostName: EditText
    private lateinit var etPostEmail: EditText
    private lateinit var etPostDescription: EditText
    private lateinit var btnSaveData: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        etPostUserName = findViewById(R.id.editTextTextPersonName18)
        etPostName = findViewById(R.id.editTextTextPersonName5)
        etPostEmail = findViewById(R.id.editTextTextPersonName6)
        etPostDescription = findViewById(R.id.editTextTextPersonName7)

        btnSaveData = findViewById(R.id.button4)

        dbRef = FirebaseDatabase.getInstance().getReference("Posts")

        btnSaveData.setOnClickListener {
            savePostData()
        }
    }

    private fun savePostData() {
        //getting values
        val postUserName = etPostUserName.text.toString()
        val postName = etPostName.text.toString()
        val postEmail = etPostEmail.text.toString()
        val postDescription = etPostDescription.text.toString()

        if (postUserName.isEmpty()) {
            etPostUserName.error = "Please enter User name"
        }
        if (postName.isEmpty()) {
            etPostName.error = "Please enter name"
        }
        if (postEmail.isEmpty()) {
            etPostEmail.error = "Please enter Email"
        }
        if (postDescription.isEmpty()) {
            etPostDescription.error = "Please enter Description"
        }

        val postId = dbRef.push().key!!

        val postModel = PostModel(postId, postUserName, postName, postEmail, postDescription)

        dbRef.child(postId).setValue(postModel)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etPostUserName.text.clear()
                etPostName.text.clear()
                etPostEmail.text.clear()
                etPostDescription.text.clear()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
