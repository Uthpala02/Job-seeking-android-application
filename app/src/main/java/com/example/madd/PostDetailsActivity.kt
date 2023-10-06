package com.example.madd


import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase



class PostDetailsActivity : AppCompatActivity() {

    private lateinit var postId: TextView
    private lateinit var etpostName: TextView
    private lateinit var etpostUserName: TextView
    private lateinit var etpostEmail: TextView
    private lateinit var etpostDescription: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("postId").toString(),
                intent.getStringExtra("postName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("postId").toString()

            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Posts").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Posts data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingPost::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun initView() {
        etpostUserName = findViewById(R.id.postusername)
        etpostName = findViewById(R.id.postname2)
        etpostEmail = findViewById(R.id.postemail)
        etpostDescription = findViewById(R.id.postdescription)

        btnUpdate = findViewById(R.id.update12)
        btnDelete = findViewById(R.id.delete12)
    }

    private fun setValuesToViews() {
        etpostUserName.text  = intent.getStringExtra("postUserName")
        etpostName.text  = intent.getStringExtra("postName")
        etpostEmail.text  = intent.getStringExtra("postEmail")
        etpostDescription.text  = intent.getStringExtra("postDescription")

    }



    private fun openUpdateDialog(
        postId: String,
        postNames: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.post_update, null)

        mDialog.setView(mDialogView)
        val postUserName = mDialogView.findViewById<EditText>(R.id.etpostUserName)
        val postName = mDialogView.findViewById<EditText>(R.id.etpostName)
        val postEmail = mDialogView.findViewById<EditText>(R.id.etpostEmail)
        val postDescription = mDialogView.findViewById<EditText>(R.id.etpostDescription)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        postUserName.setText(intent.getStringExtra("postUserName").toString())
        postName.setText(intent.getStringExtra("postName").toString())
        postEmail.setText(intent.getStringExtra("postEmail").toString())
        postDescription.setText(intent.getStringExtra("postDescription").toString())

        mDialog.setTitle("Updating $postNames Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                postId,
                postUserName.text.toString(),
                postName.text.toString(),
                postEmail.text.toString(),
                postDescription.text.toString(),

                )

            Toast.makeText(applicationContext, "Posts Data Updated", Toast.LENGTH_LONG).show()

            etpostUserName.text = postUserName.text.toString()
            etpostName.text  = postName.text.toString()
            etpostEmail.text  =  postEmail.text.toString()
            etpostDescription.text  = postDescription.text.toString()







            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        username: String,
        name: String,
        email: String,
        description: String,
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Posts").child(id)
        val empInfo = PostModel(id, username, name, email,description)
        dbRef.setValue(empInfo)
    }

}
