package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class fetchingUserJObs : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingDat:TextView
    private lateinit var emplist :ArrayList<JobListModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_user_jobs)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingDat = findViewById(R.id.tvLoadingData)


        emplist = arrayListOf<JobListModel>()


        getJobListModeData()
    }

    private fun getJobListModeData(){

        empRecyclerView.visibility = View.GONE
        tvLoadingDat.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("ListJobs")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                emplist.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(JobListModel::class.java)
                        emplist.add(empData!!)
                    }
                    val mAdapter = cmAdapter(emplist)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : cmAdapter.onItemClickListener{
                        override fun onItemClick(position: Int){

                            val intent = Intent(this@fetchingUserJObs, UserJobDetails::class.java)

                            //put extras
                            intent.putExtra("empId",emplist[position].empId)
                            intent.putExtra("comJobname",emplist[position].comJobname)
                            intent.putExtra("comJobDesc",emplist[position].comJobDesc)
                            intent.putExtra("comJobEmail",emplist[position].comJobEmail)
                            startActivity(intent)
                        }
                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingDat.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}