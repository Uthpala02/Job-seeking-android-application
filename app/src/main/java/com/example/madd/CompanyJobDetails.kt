package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class CompanyJobDetails : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_job_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdatejoblist(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("comJobname").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()

            )
        }
    }

    private fun initView() {

        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge = findViewById(R.id.tvEmpAge)
        tvEmpSalary = findViewById(R.id.tvEmpSalary)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("comJobname")
        tvEmpAge.text = intent.getStringExtra("comJobDesc")
        tvEmpSalary.text = intent.getStringExtra("comJobEmail")

    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("ListJobs").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Posts data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingList::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdatejoblist(

        empId: String,
        empName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_job_list,null)

        mDialog.setView(mDialogView)

        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)
        val btnDelete = mDialogView.findViewById<Button>(R.id.btnDelete)

        etEmpName.setText(intent.getStringExtra("comJobname").toString())
        etEmpAge.setText(intent.getStringExtra("comJobDesc").toString())
        etEmpSalary.setText(intent.getStringExtra("comJobEmail").toString())


        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpName.text = etEmpName.text.toString()
            tvEmpAge.text = etEmpAge.text.toString()
            tvEmpSalary.text = etEmpSalary.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        age: String,
        salary: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("ListJobs").child(id)
        val empInfo = JobListModel(id, name, age, salary)
        dbRef.setValue(empInfo)
    }
}