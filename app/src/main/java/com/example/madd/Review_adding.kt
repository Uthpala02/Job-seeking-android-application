package com.example.feeding

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class Review_adding : AppCompatActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var addReview: EditText
    private lateinit var submitButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_adding)

        ratingBar = findViewById(R.id.ratingBar)
        submitButton = findViewById(R.id.submitButton)
        addReview = findViewById(R.id.addReview)
        // Get the RatingBar view from the layout
        ratingBar = findViewById<RatingBar>(R.id.ratingBar)

        // Set the rating bar change listener
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->

            // Set the progress tint to yellow for the selected stars
            ratingBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))

            // Set the progress background tint to green for the unselected stars
            ratingBar.progressBackgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))

            // Set the secondary progress tint to green for the unselected stars
            ratingBar.secondaryProgressTintList = ColorStateList.valueOf(ContextCompat.getColor(this,  R.color.grey))
        }

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            val review = addReview.text.toString()
            val timeStamp: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())


            // Save the data to Firebase
            val database = FirebaseDatabase.getInstance().reference
            val reviewId = database.child("review").push().key

            // Create a new reservation object
            val reservation = Feedback("1", rating, review , timeStamp.toString() , reviewId)

            if (reviewId != null) {
                database.child("review").child(reviewId).setValue(reservation)
            }

            // Show a success message
            Toast.makeText(this, "Review Adding successful!", Toast.LENGTH_SHORT).show()
        }

    }
    data class Feedback(
        val reviewerName: String = "",
        val rating: Float,
        val time_stamp: String = "",
        val review: String = "",
        val reviewId: String?
    )
}