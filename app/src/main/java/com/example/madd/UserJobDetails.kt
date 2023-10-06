package com.example.madd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView


class UserJobDetails : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_job_details)

        initView()
        setValuesToViews()


    }

    private fun initView() {


        tvEmpName = findViewById(R.id.textView48)
        tvEmpAge = findViewById(R.id.textView46)
        tvEmpSalary = findViewById(R.id.textView49)



    }

    private fun setValuesToViews() {


        tvEmpName.text = intent.getStringExtra("comJobname")
        tvEmpAge.text = intent.getStringExtra("comJobDesc")
        tvEmpSalary.text = intent.getStringExtra("comJobEmail")

    }



}