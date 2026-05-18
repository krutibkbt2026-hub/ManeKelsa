package com.example.manekelsa

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        // Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Views
        val profileName =
            findViewById<TextView>(
                R.id.textProfileName
            )

        val profileEmail =
            findViewById<TextView>(
                R.id.textProfileEmail
            )

        val availabilitySwitch =
            findViewById<Switch>(
                R.id.switchAvailability
            )

        val statusText =
            findViewById<TextView>(
                R.id.txtStatus
            )

        val ratingButton =
            findViewById<Button>(
                R.id.btnRating
            )

        val btnEnglish =
            findViewById<Button>(
                R.id.btnEnglish
            )

        val btnKannada =
            findViewById<Button>(
                R.id.btnKannada
            )

        // Current User
        val currentUser =
            auth.currentUser

        if (currentUser != null) {

            val email =
                currentUser.email

            profileEmail.text = email

            val userName =
                email?.substringBefore("@")

            profileName.text =
                userName
        }

        // Availability Switch
        availabilitySwitch
            .setOnCheckedChangeListener {

                    _,
                    isChecked ->

                if (isChecked) {

                    statusText.text =
                        getString(R.string.online)

                    statusText.setTextColor(
                        Color.GREEN
                    )

                } else {

                    statusText.text =
                        getString(R.string.offline)

                    statusText.setTextColor(
                        Color.RED
                    )
                }
            }

        // English Language
        btnEnglish.setOnClickListener {

            setLocale("en")
        }

        // Kannada Language
        btnKannada.setOnClickListener {

            setLocale("kn")
        }

        // Open Rating Screen
        ratingButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RatingActivity::class.java
                )
            )
        }
    }

    // Language Switch
    private fun setLocale(
        languageCode: String
    ) {

        val locale =
            Locale(languageCode)

        Locale.setDefault(locale)

        val config =
            Configuration()

        config.setLocale(locale)

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )

        val intent =
            Intent(
                this,
                SplashActivity::class.java
            )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        finish()
    }
}