package com.example.manekelsa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class EditWorkerActivity : AppCompatActivity() {

    private lateinit var imageWorker: ImageView

    private var imageUri: Uri? = null

    private var imageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_edit_worker
        )

        // Views
        imageWorker =
            findViewById(R.id.imageEditWorker)

        val btnChangeImage =
            findViewById<Button>(
                R.id.btnChangeImage
            )

        val editName =
            findViewById<EditText>(
                R.id.editWorkerName
            )

        val editWork =
            findViewById<EditText>(
                R.id.editWorkerWork
            )

        val editExperience =
            findViewById<EditText>(
                R.id.editWorkerExperience
            )

        val editPhone =
            findViewById<EditText>(
                R.id.editWorkerPhone
            )

        val editArea =
            findViewById<EditText>(
                R.id.editWorkerArea
            )

        val switchAvailability =
            findViewById<Switch>(
                R.id.switchEditAvailability
            )

        val btnUpdateWorker =
            findViewById<Button>(
                R.id.btnUpdateWorker
            )

        // Intent Data
        val workerId =
            intent.getStringExtra(
                "workerId"
            ) ?: ""

        val name =
            intent.getStringExtra(
                "name"
            ) ?: ""

        val work =
            intent.getStringExtra(
                "work"
            ) ?: ""

        val experience =
            intent.getStringExtra(
                "experience"
            ) ?: ""

        val phone =
            intent.getStringExtra(
                "phone"
            ) ?: ""

        val area =
            intent.getStringExtra(
                "area"
            ) ?: ""

        val available =
            intent.getBooleanExtra(
                "available",
                true
            )

        imageUrl =
            intent.getStringExtra(
                "imageUrl"
            ) ?: ""

        // Set Existing Data
        editName.setText(name)

        editWork.setText(work)

        editExperience.setText(
            experience
        )

        editPhone.setText(phone)

        editArea.setText(area)

        switchAvailability.isChecked =
            available

        // Load Existing Image
        Glide.with(this)
            .load(imageUrl)
            .placeholder(
                android.R.drawable.ic_menu_camera
            )
            .error(
                android.R.drawable.ic_menu_report_image
            )
            .into(imageWorker)

        // Change Image
        btnChangeImage.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(
                intent,
                100
            )
        }

        // Update Worker
        btnUpdateWorker.setOnClickListener {

            val updatedName =
                editName.text.toString().trim()

            val updatedWork =
                editWork.text.toString().trim()

            val updatedExperience =
                editExperience.text.toString().trim()

            val updatedPhone =
                editPhone.text.toString().trim()

            val updatedArea =
                editArea.text.toString().trim()

            val updatedAvailable =
                switchAvailability.isChecked

            // Validation
            if (
                updatedName.isEmpty() ||
                updatedWork.isEmpty() ||
                updatedExperience.isEmpty() ||
                updatedPhone.isEmpty() ||
                updatedArea.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            val database =
                FirebaseDatabase.getInstance(
                    "https://manekelsa-9d9ee-default-rtdb.asia-southeast1.firebasedatabase.app/"
                )

            val workerRef =
                database.getReference(
                    "workers"
                ).child(workerId)

            // If New Image Selected
            if (imageUri != null) {

                val storageRef =
                    FirebaseStorage.getInstance()
                        .reference
                        .child(
                            "worker_images/$workerId.jpg"
                        )

                storageRef.putFile(
                    imageUri!!
                ).addOnSuccessListener {

                    storageRef.downloadUrl
                        .addOnSuccessListener { uri ->

                            val updatedWorker =
                                Worker(

                                    workerId = workerId,

                                    name = updatedName,

                                    work = updatedWork,

                                    experience =
                                        updatedExperience,

                                    phone = updatedPhone,

                                    area = updatedArea,

                                    available =
                                        updatedAvailable,

                                    imageUrl =
                                        uri.toString()
                                )

                            workerRef.setValue(
                                updatedWorker
                            ).addOnSuccessListener {

                                Toast.makeText(
                                    this,
                                    "Worker Updated Successfully",
                                    Toast.LENGTH_LONG
                                ).show()

                                finish()
                            }
                        }
                }.addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Image Upload Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {

                val updatedWorker =
                    Worker(

                        workerId = workerId,

                        name = updatedName,

                        work = updatedWork,

                        experience =
                            updatedExperience,

                        phone = updatedPhone,

                        area = updatedArea,

                        available =
                            updatedAvailable,

                        imageUrl = imageUrl
                    )

                workerRef.setValue(
                    updatedWorker
                ).addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Worker Updated Successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                }.addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Update Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
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

            imageWorker.setImageURI(
                imageUri
            )
        }
    }
}