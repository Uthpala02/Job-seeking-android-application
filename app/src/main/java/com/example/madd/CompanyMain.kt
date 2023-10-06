package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.madd.databinding.ActivityCompanyMainBinding

class CompanyMain : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainUpload.setOnClickListener{
            val intent = Intent(this@CompanyMain, UploadActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.mainSearch.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@CompanyMain, ReadJobDetails::class.java)
            startActivity(intent)
        })
        binding.mainUpdate.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@CompanyMain, UpdatePostJob::class.java)
            startActivity(intent)
        })
        binding.mainDelete.setOnClickListener(View.OnClickListener{
            val intent = Intent(this@CompanyMain, DeleteJobs::class.java)
            startActivity(intent)
        })
    }
}