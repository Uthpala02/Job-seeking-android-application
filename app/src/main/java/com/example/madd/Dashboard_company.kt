package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madd.databinding.ActivityDashboardCompanyBinding

class Dashboard_company : AppCompatActivity(){
    private lateinit var binding: ActivityDashboardCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Addpayment.setOnClickListener{
            startActivity(Intent(this,FetchingList::class.java))
        }

//        binding.btnTax.setOnClickListener{
//            startActivity(Intent(this,CompanyMain::class.java))
//        }

        binding.Profilebtn.setOnClickListener{
            startActivity(Intent(this,fetchingUserJObs::class.java))
        }

        binding.listjobs.setOnClickListener{
            startActivity(Intent(this,InsertJobList::class.java))
        }

        binding.btnlogout.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
        }

    }

}