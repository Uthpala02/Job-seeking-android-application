package com.example.feeding

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class update_one_review : AppCompatActivity() {

    private lateinit var newReview: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_one_review)

        val updateResevaation = findViewById<Button>(R.id.btnReviewUpdate)
        val close_button = findViewById<TextView>(R.id.btnCloseReservation)
        val review_delete_button = findViewById<TextView>(R.id.btnReviewDelete)
        val update_Review_ID = findViewById<TextView>(R.id.updateReviewID)
        val update_Review_ratingbar = findViewById<RatingBar>(R.id.ratingBarUpdate)

        newReview = findViewById<EditText>(R.id.editReview)
        update_Review_ID.text = Global_variable.reviewId
        update_Review_ratingbar.rating = (Global_variable.rating).toFloat()

        close_button.setOnClickListener {
            val intent = Intent(this, review_list::class.java)
            startActivity(intent)
        }

        // set an OnClickListener on the Add to Cart button
        updateResevaation.setOnClickListener {
            val reviewId = Global_variable.reviewId

            // Get the new date and time values
            val update_review = newReview.text.toString()


            // Update the reservation in Firebase
            val database = FirebaseDatabase.getInstance().reference
            if (reviewId != null) {
                database.child("review").child(reviewId).child("review").setValue(update_review)

                // Show a success message
                Toast.makeText(this, "Review updated successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, review_list::class.java)
                startActivity(intent)
            }
        }

        review_delete_button.setOnClickListener{

            val reviewId = Global_variable.reviewId
            // Assume that you have a reference to your Firebase Database root
            val databaseRef = FirebaseDatabase.getInstance().reference

            // Assume that you have a reference to the reservation that you want to delete
            val reservationRef = databaseRef.child("review").child(reviewId.toString())

            // Call the removeValue() method on the reservation reference to delete it
            reservationRef.removeValue().addOnSuccessListener {
                Toast.makeText(this, "Review deleted successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Error deleting reservation", e)
                Toast.makeText(this, "Failed to delete reservation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun back(view: View) {}

}