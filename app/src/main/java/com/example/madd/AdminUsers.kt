package com.example.madd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class AdminUsers : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var empList: ArrayList<EmployeModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_users)

        userRecyclerView = findViewById(R.id.recyclerViewUsers)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        empList = arrayListOf<EmployeModel>()
        getEmployeesData()
    }

    private fun getEmployeesData(){
        userRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(EmployeModel::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = UserAdapter(empList)
                    userRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AdminUsers, EditAppUsers::class.java)

                            //put extras

                            intent.putExtra("empName", empList[position].empName)
                            intent.putExtra("empAge", empList[position].empEmail)
                            startActivity(intent)
                        }

                    })



                    userRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}