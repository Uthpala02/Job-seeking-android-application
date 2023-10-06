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

class EditAppUsers : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_app_users)
        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
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
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpAge.text = intent.getStringExtra("empAge")
    }

    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener{
            val inflater = layoutInflater
            val layout = inflater.inflate(R.layout.employee_delete_toast, findViewById(R.id.toast_layout))
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()

            val intent = Intent(this, AdminUsers::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(empId: String, empName: String){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.admin_update_dialog, null)

        mDialog.setView(mDialogView)
        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpEmail)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpPassword)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())

        mDialog.setTitle("Updating $empName")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString()
            )

            val inflater = layoutInflater
            val layout = inflater.inflate(R.layout.employee_update_toast, findViewById(R.id.toast_update_layout))
            val toast = Toast(applicationContext)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()

            tvEmpName.text = etEmpName.text.toString()
            tvEmpAge.text = etEmpAge.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(id: String, name: String, age: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeModel(id, name, age)
        dbRef.setValue(empInfo)
    }



}