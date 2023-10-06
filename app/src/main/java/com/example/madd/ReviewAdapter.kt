package com.example.feeding

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val reviews: List<review_list.Feedback>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_one_review_item, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val reservation = reviews[position]
        val reservationViewHolder = holder as ReservationViewHolder
        reservationViewHolder.bind(reservation)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val reviewer_name: TextView = itemView.findViewById(R.id.lbl_name)
        private val rating: TextView = itemView.findViewById(R.id.lbl_rating)
        private val review_: TextView = itemView.findViewById(R.id.lbl_review)


        private val context = itemView.context
        var updateButton: Button? = null

        fun bind(onereview: review_list.Feedback) {

            reviewer_name.text = onereview.reviewerName
            rating.text = onereview.rating.toString()+" Rating Review"
            review_.text = onereview.review


            updateButton = itemView.findViewById<Button>(R.id.btnViewReview)
            updateButton?.setOnClickListener {
                val intent = Intent(context, update_one_review::class.java)

                Global_variable.reviewerName = onereview.reviewerName.toString();
                Global_variable.rating = onereview.rating.toString();
                Global_variable.review = onereview.review.toString();
                Global_variable.time_stamp = onereview.time_stamp.toString();
                context.startActivity(intent)
            }


        }
    }
}
