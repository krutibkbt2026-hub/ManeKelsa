package com.example.manekelsa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class UserTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_type)

        val btnWorker = findViewById<Button>(R.id.btnWorker)

        val btnUser = findViewById<Button>(R.id.btnUser)

        // I am a Worker
        btnWorker.setOnClickListener {

            startActivity(
                Intent(this, RegisterActivity::class.java)
            )

        }

        // I am looking for Workers
        btnUser.setOnClickListener {

            startActivity(
                Intent(this, HomeActivity::class.java)
            )

        }
    }
}