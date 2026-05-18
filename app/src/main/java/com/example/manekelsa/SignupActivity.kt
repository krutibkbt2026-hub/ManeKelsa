package com.example.manekelsa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val editEmail =
            findViewById<EditText>(R.id.editSignupEmail)

        val editPassword =
            findViewById<EditText>(R.id.editSignupPassword)

        val btnSignup =
            findViewById<Button>(R.id.btnSignup)

        val textLogin =
            findViewById<TextView>(R.id.textLogin)

        // Signup Button
        btnSignup.setOnClickListener {

            val email =
                editEmail.text.toString().trim()

            val password =
                editPassword.text.toString().trim()

            if (
                email.isEmpty()
                ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Enter all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(
                email,
                password
            )
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Account Created",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Signup Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        // Login Click
        textLogin.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }
    }
}