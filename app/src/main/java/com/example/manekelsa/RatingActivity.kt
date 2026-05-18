package com.example.manekelsa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rating)

        val ratingBar =
            findViewById<RatingBar>(
                R.id.ratingBar
            )

        val editFeedback =
            findViewById<EditText>(
                R.id.editFeedback
            )

        val btnSubmit =
            findViewById<Button>(
                R.id.btnSubmit
            )

        btnSubmit.setOnClickListener {

            val rating =
                ratingBar.rating

            val feedback =
                editFeedback.text.toString()

            // Validation
            if (rating == 0f) {

                Toast.makeText(
                    this,
                    "Please give rating",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Firebase Database
            val database =
                FirebaseDatabase.getInstance(
                    "https://manekelsa-9d9ee-default-rtdb.asia-southeast1.firebasedatabase.app/"
                )

            val ratingsRef =
                database.getReference("ratings")

            val ratingId =
                ratingsRef.push().key!!

            val ratingData =
                HashMap<String, Any>()

            ratingData["rating"] =
                rating

            ratingData["feedback"] =
                feedback

            ratingsRef.child(ratingId)
                .setValue(ratingData)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Feedback Submitted Successfully!",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Failed to submit feedback",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}