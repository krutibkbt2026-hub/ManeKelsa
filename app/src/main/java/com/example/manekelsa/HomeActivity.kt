package com.example.manekelsa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerWorkers: RecyclerView

    private lateinit var workerList: ArrayList<Worker>

    private lateinit var adapter: WorkerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        recyclerWorkers =
            findViewById(R.id.recyclerWorkers)

        val btnLogout =
            findViewById<Button>(
                R.id.btnLogout
            )

        val bottomNavigation =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        workerList = ArrayList()

        adapter =
            WorkerAdapter(workerList)

        recyclerWorkers.layoutManager =
            LinearLayoutManager(this)

        recyclerWorkers.adapter =
            adapter

        // Load Firebase Workers
        loadWorkers()

        // Logout
        btnLogout.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {

                    true
                }

                R.id.nav_register -> {

                    startActivity(
                        Intent(
                            this,
                            RegisterActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_profile -> {

                    startActivity(
                        Intent(
                            this,
                            ProfileActivity::class.java
                        )
                    )

                    true
                }

                else -> false
            }
        }
    }

    // Load Workers from Firebase
    private fun loadWorkers() {

        val database =
            FirebaseDatabase.getInstance(
                "https://manekelsa-9d9ee-default-rtdb.asia-southeast1.firebasedatabase.app/"
            )

        val workersRef =
            database.getReference("workers")

        workersRef.addValueEventListener(
            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    workerList.clear()

                    for (workerSnapshot in snapshot.children) {

                        try {

                            val worker =
                                workerSnapshot.getValue(
                                    Worker::class.java
                                )

                            if (worker != null) {

                                worker.workerId =
                                    workerSnapshot.key

                                workerList.add(worker)
                            }

                        } catch (e: Exception) {

                            // Ignore invalid Firebase data
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {
                }
            }
        )
    }
}