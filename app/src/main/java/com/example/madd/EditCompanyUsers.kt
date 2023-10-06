package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class EditCompanyUsers : AppCompatActivity() {

    private lateinit var tvCompId: TextView
    private lateinit var tvCompAge: TextView
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company_users)
        initView()
        setValuesToViews()

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empAge").toString()
            )
        }

    }

    private fun initView() {
        tvCompId = findViewById(R.id.tvCompId)
        tvCompAge = findViewById(R.id.tvCompAge)
        btnDelete = findViewById(R.id.btnCompDelete)
    }

    private fun setValuesToViews() {
        tvCompId.text = intent.getStringExtra("empId")
        tvCompAge.text = intent.getStringExtra("empAge")
    }

    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Company").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener{
            val inflater = layoutInflater
            val layout = inflater.inflate(R.layout.employee_delete_toast, findViewById(R.id.toast_layout))
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()

            val intent = Intent(this, AdminCompany::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


}