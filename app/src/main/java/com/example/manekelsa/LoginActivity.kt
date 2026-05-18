package com.example.manekelsa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val editEmail =
            findViewById<EditText>(R.id.editEmail)

        val editPassword =
            findViewById<EditText>(R.id.editPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val textSignup =
            findViewById<TextView>(R.id.textSignup)

        // Login Button
        btnLogin.setOnClickListener {

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

            auth.signInWithEmailAndPassword(
                email,
                password
            )
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Login Successful",
                        Toast.LENGTH_SHORT
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
                        "Login Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }

        // Signup Click
        textSignup.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SignupActivity::class.java
                )
            )
        }
    }
}