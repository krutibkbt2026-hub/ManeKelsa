package com.example.manekelsa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val imageWorker =
            findViewById<ImageView>(
                R.id.imageDetailWorker
            )

        val textName =
            findViewById<TextView>(
                R.id.textDetailName
            )

        val textWork =
            findViewById<TextView>(
                R.id.textDetailWork
            )

        val textExperience =
            findViewById<TextView>(
                R.id.textDetailExperience
            )

        val textPhone =
            findViewById<TextView>(
                R.id.textDetailPhone
            )

        val textArea =
            findViewById<TextView>(
                R.id.textDetailArea
            )

        val btnCallWorker =
            findViewById<Button>(
                R.id.btnCallWorker
            )

        val btnEditWorker =
            findViewById<Button>(
                R.id.btnEditWorker
            )

        // Intent Data
        val workerId =
            intent.getStringExtra(
                "workerId"
            ) ?: ""

        val name =
            intent.getStringExtra(
                "name"
            ) ?: "Unknown"

        val work =
            intent.getStringExtra(
                "work"
            ) ?: "Work"

        val experience =
            intent.getStringExtra(
                "experience"
            ) ?: "0"

        val phone =
            intent.getStringExtra(
                "phone"
            ) ?: ""

        val area =
            intent.getStringExtra(
                "area"
            ) ?: "Unknown"

        val available =
            intent.getBooleanExtra(
                "available",
                true
            )

        val imageUrl =
            intent.getStringExtra(
                "imageUrl"
            ) ?: ""

        // Load Image
        Glide.with(this)
            .load(imageUrl)
            .placeholder(
                android.R.drawable.ic_menu_camera
            )
            .into(imageWorker)

        // Set Details
        textName.text = name

        textWork.text =
            "Work: $work"

        textExperience.text =
            "Experience: $experience years"

        textPhone.text =
            "Phone: $phone"

        textArea.text =
            "Area: $area"

        // Call Worker
        btnCallWorker.setOnClickListener {

            if (phone.isNotEmpty()) {

                val dialIntent =
                    Intent(
                        Intent.ACTION_DIAL
                    )

                dialIntent.data =
                    Uri.parse("tel:$phone")

                startActivity(
                    dialIntent
                )

            } else {

                Toast.makeText(
                    this,
                    "Phone number not available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Edit Worker
        btnEditWorker.setOnClickListener {

            val editIntent =
                Intent(
                    this,
                    EditWorkerActivity::class.java
                )

            editIntent.putExtra(
                "workerId",
                workerId
            )

            editIntent.putExtra(
                "name",
                name
            )

            editIntent.putExtra(
                "work",
                work
            )

            editIntent.putExtra(
                "experience",
                experience
            )

            editIntent.putExtra(
                "phone",
                phone
            )

            editIntent.putExtra(
                "area",
                area
            )

            editIntent.putExtra(
                "available",
                available
            )

            editIntent.putExtra(
                "imageUrl",
                imageUrl
            )

            startActivity(
                editIntent
            )
        }
    }
}