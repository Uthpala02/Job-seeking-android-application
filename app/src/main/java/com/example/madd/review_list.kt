package com.example.feeding

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class review_list : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReviewAdapter
    private lateinit var reviewAdding: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        reviewAdding = findViewById(R.id.btnAddNewReview)

        reviewAdding.setOnClickListener {
            val intent = Intent(this, Review_adding::class.java)
            startActivity(intent)
        }

        val database = FirebaseDatabase.getInstance().reference
        val reviews = mutableListOf<Feedback>()
        adapter = ReviewAdapter(reviews)
        recyclerView.adapter = adapter

        database.child("review").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                reviews.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val reservation = postSnapshot.getValue(Feedback::class.java)
                    reservation?.let {
                        reviews.add(it)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to read value.", databaseError.toException())
            }
        })
    }


    data class Feedback(
        val reviewerName: String = "",
        val rating: Float,
        val time_stamp: String = "",
        val review: String = "",
        val reviewId: String?
    ){
        constructor() : this("", 0F, "", "", "")
    }

}