package com.example.madd

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.*
import java.util.*
import kotlin.concurrent.timerTask

class AdminPerformance : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRef2: DatabaseReference
    private lateinit var dbRef3: DatabaseReference
    private var totalUsers: Long = 0
    private var totalCompanies: Long = 0
    private var totalPosts: Long = 0
    private lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_performance)

        var newTotalUsers: Long = 0
        var prevTotal: Long = 0
        var newTotalCompanies: Long = 0
        var prevCompanyTotal: Long = 0

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef2 = FirebaseDatabase.getInstance().getReference("Company")
        dbRef2 = FirebaseDatabase.getInstance().getReference("Posts")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                totalUsers = dataSnapshot.childrenCount
                val textView11 = findViewById<TextView>(R.id.textView11)
                textView11.text = "$totalUsers"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })

        dbRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                totalCompanies = dataSnapshot.childrenCount
                val textView13 = findViewById<TextView>(R.id.textView13)
                textView13.text = "$totalCompanies"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })

        dbRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                totalPosts = dataSnapshot.childrenCount
                val textView9 = findViewById<TextView>(R.id.textView9)
                textView9.text = "$totalPosts"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read value.", error.toException())
            }
        })

        // Start the timer to get the updated total users count every 5 minutes
        timer = Timer()
        timer.scheduleAtFixedRate(timerTask {
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    newTotalUsers = dataSnapshot.childrenCount
                    val textView21 = findViewById<TextView>(R.id.textView21)
                    var difference = newTotalUsers - prevTotal
                    prevTotal = newTotalUsers
                    textView21.text = "$difference"
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Failed to read value.", error.toException())
                }
            })

            dbRef2.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    newTotalCompanies = dataSnapshot.childrenCount
                    val textView23 = findViewById<TextView>(R.id.textView23)
                    var compDifference = newTotalCompanies - prevCompanyTotal
                    prevCompanyTotal = newTotalCompanies
                    textView23.text = "$compDifference"
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Failed to read value.", error.toException())
                }
            })

            // Update the total users count

        }, 0 * 30 * 1000, 1 * 10 * 1000) // 5 minutes in milliseconds

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}