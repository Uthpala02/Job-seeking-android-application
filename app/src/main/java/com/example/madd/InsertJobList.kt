package com.example.madd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertJobList : AppCompatActivity() {

    private lateinit var comJobname: EditText
    private lateinit var comJobDesc: EditText
    private lateinit var comJobEmail: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_job_list)

        comJobname = findViewById(R.id.comJobname)
        comJobDesc = findViewById(R.id.comJobDesc)
        comJobEmail = findViewById(R.id.comJobEmail)
        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("ListJobs")

        btnSaveData.setOnClickListener{
            saveListJobs()
        }
    }

    private fun saveListJobs(){

        val comJobname = comJobname.text.toString()
        val comJobDesc = comJobDesc.text.toString()
        val comJobEmail = comJobEmail.text.toString()

        val empId = dbRef.push().key!!

        val jobListModel = JobListModel(empId, comJobname, comJobDesc, comJobEmail)

        dbRef.child(empId).setValue(jobListModel)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}
