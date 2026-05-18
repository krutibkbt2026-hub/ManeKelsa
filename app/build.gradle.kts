plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {

    namespace = "com.example.manekelsa"

    compileSdk = 34

    defaultConfig {

        applicationId = "com.example.manekelsa"

        minSdk = 24
        targetSdk = 34

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_17

        targetCompatibility =
            JavaVersion.VERSION_17
    }

    kotlinOptions {

        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Firebase BOM
    implementation(
        platform(
            "com.google.firebase:firebase-bom:33.1.2"
        )
    )

    // Firebase
    implementation(
        "com.google.firebase:firebase-database"
    )

    implementation(
        "com.google.firebase:firebase-storage"
    )

    implementation(
        "com.google.firebase:firebase-auth"
    )

    // Glide
    implementation(
        "com.github.bumptech.glide:glide:4.16.0"
    )

    // Stable AndroidX Versions
    implementation(
        "androidx.core:core-ktx:1.12.0"
    )

    implementation(
        "androidx.appcompat:appcompat:1.6.1"
    )

    implementation(
        "com.google.android.material:material:1.11.0"
    )

    implementation(
        "androidx.constraintlayout:constraintlayout:2.1.4"
    )

    implementation(
        "androidx.recyclerview:recyclerview:1.3.2"
    )

    implementation(
        "androidx.cardview:cardview:1.0.0"
    )

    implementation(
        "androidx.activity:activity-ktx:1.8.2"
    )
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Testing
    testImplementation(
        "junit:junit:4.13.2"
    )

    androidTestImplementation(
        "androidx.test.ext:junit:1.1.5"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.5.1"
    )
}