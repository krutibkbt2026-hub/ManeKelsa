package com.example.manekelsa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        val startButton =
            findViewById<Button>(R.id.btnStart)

        startButton.setOnClickListener {

            // Check if user already logged in
            if (auth.currentUser != null) {

                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )

            } else {

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }

            finish()
        }
    }
}