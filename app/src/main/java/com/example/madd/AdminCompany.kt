package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminCompany : AppCompatActivity() {

    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var compList: ArrayList<User>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_company)

        companyRecyclerView = findViewById(R.id.recyclerViewCompany)
        companyRecyclerView.layoutManager = LinearLayoutManager(this)
        companyRecyclerView.setHasFixedSize(true)

        compList = arrayListOf<User>()
        getCompanyData()
    }

    private fun getCompanyData(){
        companyRecyclerView.visibility = View.GONE
        dbRef = FirebaseDatabase.getInstance().getReference("Company")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                compList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val compData = empSnap.getValue(User::class.java)
                        compList.add(compData!!)
                    }
                    val cAdapter = CompanyAdapter(compList)
                    companyRecyclerView.adapter = cAdapter

                    cAdapter.setOnItemClickListener(object : CompanyAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AdminCompany, EditCompanyUsers::class.java)

                            //put extras
                            intent.putExtra("empId", compList[position].description)
                            intent.putExtra("empName", compList[position].Jobname)
                            intent.putExtra("empAge", compList[position].phone)
                            startActivity(intent)
                        }

                    })
                    companyRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}