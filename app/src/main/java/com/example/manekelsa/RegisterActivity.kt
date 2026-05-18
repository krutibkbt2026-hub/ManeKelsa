package com.example.manekelsa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterActivity : AppCompatActivity() {

    private lateinit var imageWorker: ImageView

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        // Views
        imageWorker =
            findViewById(R.id.imageWorker)

        val btnChooseImage =
            findViewById<Button>(
                R.id.btnChooseImage
            )

        val editName =
            findViewById<EditText>(
                R.id.editName
            )

        val spinnerWork =
            findViewById<Spinner>(
                R.id.spinnerWork
            )

        val editExperience =
            findViewById<EditText>(
                R.id.editExperience
            )

        val editPhone =
            findViewById<EditText>(
                R.id.editPhone
            )

        val editArea =
            findViewById<EditText>(
                R.id.editArea
            )

        val switchAvailability =
            findViewById<Switch>(
                R.id.switchAvailability
            )

        val btnRegister =
            findViewById<Button>(
                R.id.btnRegister
            )

        // Work Categories
        val workList = arrayOf(
            "Cooking",
            "Cleaning",
            "Babysitting",
            "Gardening"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            workList
        )

        spinnerWork.adapter = adapter

        // Choose Image
        btnChooseImage.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(intent, 100)
        }

        // Register Button
        btnRegister.setOnClickListener {

            val name =
                editName.text.toString().trim()

            val work =
                spinnerWork.selectedItem.toString()

            val experience =
                editExperience.text.toString().trim()

            val phone =
                editPhone.text.toString().trim()

            val area =
                editArea.text.toString().trim()

            val available =
                switchAvailability.isChecked

            // Validation
            if (
                name.isEmpty() ||
                experience.isEmpty() ||
                phone.isEmpty() ||
                area.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            // Firebase Database
            val database =
                FirebaseDatabase.getInstance(
                    "https://manekelsa-9d9ee-default-rtdb.asia-southeast1.firebasedatabase.app/"
                )

            val workersRef =
                database.getReference("workers")

            val workerId =
                workersRef.push().key!!

            // Firebase Storage
            val storageRef =
                FirebaseStorage.getInstance()
                    .reference
                    .child("worker_images/$workerId.jpg")

            // If image selected
            if (imageUri != null) {

                Toast.makeText(
                    this,
                    "Uploading Image...",
                    Toast.LENGTH_SHORT
                ).show()

                storageRef.putFile(imageUri!!)
                    .addOnSuccessListener {

                        storageRef.downloadUrl
                            .addOnSuccessListener { downloadUri ->

                                val worker = Worker(

                                    workerId,

                                    name,

                                    work,

                                    experience,

                                    phone,

                                    area,

                                    available,

                                    downloadUri.toString()
                                )

                                saveWorker(
                                    workersRef,
                                    workerId,
                                    worker
                                )
                            }
                    }
                    .addOnFailureListener {

                        Toast.makeText(
                            this,
                            "Image Upload Failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }

            } else {

                val worker = Worker(

                    workerId,

                    name,

                    work,

                    experience,

                    phone,

                    area,

                    available,

                    ""
                )

                saveWorker(
                    workersRef,
                    workerId,
                    worker
                )
            }
        }
    }

    // Save Worker Function
    private fun saveWorker(
        workersRef: DatabaseReference,
        workerId: String,
        worker: Worker
    ) {

        workersRef.child(workerId)
            .setValue(worker)
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Worker Registered Successfully",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )

                finish()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Registration Failed",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    // Image Picker Result
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == 100 &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {

            imageUri = data.data

            imageWorker.setImageURI(imageUri)
        }
    }
}