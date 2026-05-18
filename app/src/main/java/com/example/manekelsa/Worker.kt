package com.example.manekelsa

data class Worker(

    // Firebase Worker ID
    var workerId: String? = null,

    // Basic Details
    var name: String? = null,

    var work: String? = null,

    var experience: String? = null,

    var phone: String? = null,

    var area: String? = null,

    // Availability
    var available: Boolean? = true,

    // Profile Image
    var imageUrl: String? = null
)